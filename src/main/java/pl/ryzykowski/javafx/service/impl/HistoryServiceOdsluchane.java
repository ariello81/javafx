package pl.ryzykowski.javafx.service.impl;

import pl.ryzykowski.javafx.dto.Song;
import pl.ryzykowski.javafx.dto.Station;
import pl.ryzykowski.javafx.dto.StationArtistSummary;
import pl.ryzykowski.javafx.parser.SongsReader;
import pl.ryzykowski.javafx.parser.StationsReader;
import pl.ryzykowski.javafx.service.HistoryService;
import pl.ryzykowski.javafx.util.DatesUtil;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class HistoryServiceOdsluchane implements HistoryService {

    private SongsReader songsReader;
    private StationsReader stationsReader;

    public HistoryServiceOdsluchane(SongsReader songsReader, StationsReader stationsReader) {
        this.songsReader = songsReader;
        this.stationsReader = stationsReader;
    }

    @Override
    public List<Song> songsStationForDateRange(String stationId, String dateFrom, String dateTo) {
        List<Song> songs = new ArrayList<>();
        Station station = stationsReader.getStation(stationId);
        if (station != null && DatesUtil.validateDates(dateFrom, dateTo)) {
            List<LocalDate> dates = DatesUtil.getAllDatesBetweenTwoDates(dateFrom, dateTo);
            final List<String> hours = Arrays.asList("0", "10", "20", "0");
            List<Thread> threads = new ArrayList<>();
            for (LocalDate date : dates) {
                for (int i = 0; i < hours.size() - 1; i++) {
                    String startHour = hours.get(i);
                    String stopHour = hours.get(i + 1);
                    Thread thread = new Thread(() -> {
                        songs.addAll(songsReader.getSongsForStationAndDate(station, date, startHour, stopHour));
                    });
                    threads.add(thread);
                    thread.start();
                }
            }
            joinThreads(threads);
        }
        return songs
                .stream()
                .sorted(Comparator.comparing(Song::getDateTime))
                .collect(Collectors.toList());
    }

    @Override
    public StationArtistSummary songsStationForDateRangeAndArtist(String stationId, String dateFrom, String dateTo, String artist) {
        List<Song> artistSongs = songsStationForDateRange(stationId, dateFrom, dateTo)
                .stream()
                .filter(song -> song.getArtist().toLowerCase().contains(artist.toLowerCase()))
                .collect(Collectors.toList());
        StationArtistSummary stationArtistSummary = new StationArtistSummary();
        stationArtistSummary.setSongs(artistSongs);
        stationArtistSummary.setArtist(artist);
        stationArtistSummary.setDateFrom(dateFrom);
        stationArtistSummary.setDateTo(dateTo);
        stationArtistSummary.setStation(stationsReader.getStation(stationId));
        LinkedHashMap<String, Long> sortedMap = new LinkedHashMap<>();
        artistSongs
                .stream()
                .collect(Collectors.groupingBy(song -> song.getTitle(), Collectors.counting()))
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));
        stationArtistSummary.setDistinctTitles(sortedMap);
        return stationArtistSummary;
    }

    @Override
    public StationArtistSummary songsStationForArtistYearAndMonth(String stationId, String artist, String year, String month) {
        LocalDate localDateFrom = LocalDate.of (Integer.parseInt(year), Integer.parseInt(month), 1);
        LocalDate localDateTo = localDateFrom.withDayOfMonth(localDateFrom.lengthOfMonth());
        return songsStationForDateRangeAndArtist(stationId, localDateFrom.toString(), localDateTo.toString(), artist);
    }

    @Override
    public void songsStationForArtistAndYears(String stationId, String artist, String yearStart, String yearStop) {
        int yearStartInt = Integer.parseInt(yearStart);
        int yearStopInt = Integer.parseInt(yearStop);
        for (int y=yearStartInt; y<=yearStopInt; y++){
            for (int m=1; m<=12; m++) {
                LocalDate localDateFrom = LocalDate.of (y, m, 1);
                if (localDateFrom.isAfter(LocalDate.now())) {
                    return;
                }
                LocalDate localDateTo = localDateFrom.withDayOfMonth(localDateFrom.lengthOfMonth());
                StationArtistSummary summary = songsStationForDateRangeAndArtist(stationId, localDateFrom.toString(), localDateTo.toString(), artist);
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }


    private void joinThreads(List<Thread> threads){
        for (int i = 0; i < threads.size(); i++) {
            try {
                threads.get(i).join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
