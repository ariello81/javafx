package pl.ryzykowski.javafx.parser;

import pl.ryzykowski.javafx.dto.Song;
import pl.ryzykowski.javafx.dto.Station;

import java.time.LocalDate;
import java.util.List;

public interface SongsReader {

    List<Song> getSongsForStationAndDate(Station station, LocalDate date, String timeFrom, String timeTo);

}
