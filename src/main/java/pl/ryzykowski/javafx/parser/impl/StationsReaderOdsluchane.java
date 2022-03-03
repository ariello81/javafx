package pl.ryzykowski.javafx.parser.impl;

import pl.ryzykowski.javafx.dto.Station;
import pl.ryzykowski.javafx.parser.StationsReader;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class StationsReaderOdsluchane implements StationsReader {

    private static final String FILE_PATH = "stations.csv";
    private static final String FILE_CHARSET = "UTF-8";
    private static final String FILE_SEPARATOR = ";";

    private List<Station> stations;

    public StationsReaderOdsluchane() {
        this.stations = new ArrayList<>();
    }

    @Override
    public List<Station> readStations(){
        try {
            tryToReadStations();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stations;
    }

    private List<Station> tryToReadStations() throws IOException {
        stations.clear();
        FileInputStream fis = new FileInputStream(FILE_PATH);
        InputStreamReader input = new InputStreamReader(fis, FILE_CHARSET);
        String line;
        BufferedReader in = new BufferedReader(input);
        while( (line=in.readLine())!=null ) {
            String[] record = line.split(FILE_SEPARATOR);
            stations.add(new Station(record[0], record[1]));
        }
        return stations;
    }

    public Station getStation(String stationId) {
        return stations
                .stream()
                .filter(station -> station.getId().equals(stationId))
                .findFirst()
                .orElse(null);
    }
}
