package pl.ryzykowski.javafx.parser;

import pl.ryzykowski.javafx.dto.Station;

import java.util.List;

public interface StationsReader {

    List<Station> readStations();
    Station getStation(String id);

}
