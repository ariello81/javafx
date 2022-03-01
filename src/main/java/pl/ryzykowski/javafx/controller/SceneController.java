package pl.ryzykowski.javafx.controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import pl.ryzykowski.javafx.dto.DistinctTitle;
import pl.ryzykowski.javafx.dto.Song;
import pl.ryzykowski.javafx.dto.Station;
import pl.ryzykowski.javafx.dto.StationArtistSummary;
import pl.ryzykowski.javafx.parser.SongsReader;
import pl.ryzykowski.javafx.parser.StationsReader;
import pl.ryzykowski.javafx.parser.impl.HtmlSongsReaderOdsluchane;
import pl.ryzykowski.javafx.parser.impl.StationsReaderOdsluchane;
import pl.ryzykowski.javafx.service.HistoryService;
import pl.ryzykowski.javafx.service.impl.HistoryServiceOdsluchane;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;


public class SceneController {

    private StationsReader stationsReader;
    private SongsReader songsReader;

    @FXML
    TextField tfArtist;

    @FXML
    DatePicker dateStart;

    @FXML
    DatePicker dateStop;

    @FXML
    ComboBox<Station> comboStations;

    @FXML
    private void initialize() {
        comboStations.getItems().addAll(stationsReader.getStations());
    }

    @FXML
    Button btnSearch;

    @FXML
    ListView<DistinctTitle> distinctTitlesList;

    @FXML
    ListView<Song> songsList;

    public SceneController() {
        stationsReader = new StationsReaderOdsluchane();
        songsReader = new HtmlSongsReaderOdsluchane();
    }

    @FXML
    public void btnSearchClicked(Event e){
        distinctTitlesList.getItems().clear();
        songsList.getItems().clear();
        HistoryService service = new HistoryServiceOdsluchane(songsReader, stationsReader);
        StationArtistSummary summary
                = service.songsStationForDateRangeAndArtist(comboStations.getValue().getId(), dateStart.getValue().toString(), dateStop.getValue().toString(), tfArtist.getText());
        LinkedHashMap<String, Long> distinctTitles = summary.getDistinctTitles();
        distinctTitlesList.getItems().addAll(distinctTitles.entrySet()
                .stream()
                .map(entry -> new DistinctTitle(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList())
        );
        songsList.getItems().addAll(summary.getSongs()
                .stream()
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList()));
    }

}
