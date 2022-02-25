package pl.ryzykowski.javafx.controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import pl.ryzykowski.javafx.config.ConfigOdsluchane;
import pl.ryzykowski.javafx.dto.DistinctTitle;
import pl.ryzykowski.javafx.dto.Song;
import pl.ryzykowski.javafx.dto.Station;
import pl.ryzykowski.javafx.dto.StationArtistSummary;
import pl.ryzykowski.javafx.parser.HtmlParserOdsluchane;
import pl.ryzykowski.javafx.service.HistoryService;
import pl.ryzykowski.javafx.service.impl.HistoryServiceOdsluchane;
import pl.ryzykowski.javafx.util.DatesUtilOdsluchane;

import java.util.List;


public class SceneController {

    @FXML
    TextField tfArtist;

    @FXML
    ComboBox<Station> comboStations;

    ConfigOdsluchane config;

    @FXML
    private void initialize() {
        comboStations.getItems().addAll(config.getStations());
    }

    @FXML
    Button btnSearch;

    @FXML
    ListView<DistinctTitle> songList;

    HistoryService service;

    public SceneController() {
        config = new ConfigOdsluchane();
        DatesUtilOdsluchane datesUtil = new DatesUtilOdsluchane();
        HtmlParserOdsluchane parser = new HtmlParserOdsluchane(config, datesUtil);
        service = new HistoryServiceOdsluchane(parser, datesUtil, config);
    }

    @FXML
    public void btnSearchClicked(Event e){
        StationArtistSummary summary = service.songsStationForArtistYearAndMonth(comboStations.getValue().getId(), tfArtist.getText(), "2022", "2");
        List<DistinctTitle> distinctTitles = summary.getDistinctTitles();
        System.out.println(distinctTitles.size());
        songList.getItems().addAll(distinctTitles);
        System.out.println();
    }

}
