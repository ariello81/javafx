package pl.ryzykowski.javafx.parser.impl;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import pl.ryzykowski.javafx.dto.Song;
import pl.ryzykowski.javafx.dto.Station;
import pl.ryzykowski.javafx.helper.SSLHelper;
import pl.ryzykowski.javafx.parser.SongsReader;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class HtmlSongsReaderOdsluchane implements SongsReader {

    private static final String URL = "http://www.odsluchane.eu/szukaj.php?";
    private static final String ARTIST_TITLE_SEPARATOR = " - ";

    @Override
    public List<Song> getSongsForStationAndDate(Station station, LocalDate date, String timeFrom, String timeTo) {
        List<Song> songs = new ArrayList<>();
        try {
            songs = tryToGetSongsForStationAndDate(station, date, timeFrom, timeTo);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return songs;
    }

    private List<Song> tryToGetSongsForStationAndDate(Station station, LocalDate date, String timeFrom, String timeTo) throws IOException {
        List<Song> songs = new ArrayList<>();
        String odsluchaneDate = date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy" , Locale.UK ));
        Document doc = SSLHelper.getConnection(URL+"r="+station.getId()+"&date="+odsluchaneDate+"&time_from="+timeFrom+"&time_to="+timeTo).get();
        Iterator<Element> iterator = doc.getElementsByClass("title-link").iterator();
        while (iterator.hasNext()) {
            Element element = iterator.next();
            if (element.text().contains(ARTIST_TITLE_SEPARATOR)) {
                String artist = element.text().substring(0, element.text().indexOf(ARTIST_TITLE_SEPARATOR));
                String title = element.text().substring(element.text().indexOf(ARTIST_TITLE_SEPARATOR) + ARTIST_TITLE_SEPARATOR.length());
                String time = element.parent().firstElementSibling().text();
                songs.add(new Song(title, artist, station, date + " " + time));
            }
        }
        return songs;
    }

}