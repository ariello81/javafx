package pl.ryzykowski.javafx.parser.impl;

import pl.ryzykowski.javafx.dto.Station;
import pl.ryzykowski.javafx.parser.StationsReader;

import java.util.ArrayList;
import java.util.List;

public class StationsReaderOdsluchane implements StationsReader {

    private List<Station> stations;

    public StationsReaderOdsluchane() {
        this.stations = new ArrayList<>();
        this.stations.add(new Station("1", "Radio Zet"));
        this.stations.add(new Station("2", "RMF FM"));
    }

    public List<Station> getStations() {
        return stations;
    }

    public Station getStation(String stationId){
        return stations
                .stream()
                .filter(station -> station.getId().equals(stationId))
                .findFirst()
                .orElse(null);
    }
}
