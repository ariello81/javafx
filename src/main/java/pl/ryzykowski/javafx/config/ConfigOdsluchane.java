package pl.ryzykowski.javafx.config;

import pl.ryzykowski.javafx.dto.Station;

import java.util.List;

public class ConfigOdsluchane {

    private List<Station> stations;

    public List<Station> getStations() {
        return stations;
    }

    public void setStations(List<Station> stations) {
        this.stations = stations;
    }

    public Station getStation(String stationId){
        return stations
                .stream()
                .filter(station -> station.getId().equals(stationId))
                .findFirst()
                .orElse(null);
    }
}
