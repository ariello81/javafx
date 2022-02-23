package pl.ryzykowski.javafx.controller;

import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import pl.ryzykowski.javafx.config.ConfigOdsluchane;
import pl.ryzykowski.javafx.dto.StationArtistSummary;
import pl.ryzykowski.javafx.parser.HtmlParserOdsluchane;
import pl.ryzykowski.javafx.service.HistoryService;
import pl.ryzykowski.javafx.service.impl.HistoryServiceOdsluchane;
import pl.ryzykowski.javafx.util.DatesUtilOdsluchane;

public class SceneController {

    @FXML
    TextField tfArtist;

    @FXML
    ComboBox comboStations;

    @FXML
    Button btnSearch;

    HistoryService service;

    public SceneController() {
        ConfigOdsluchane config = new ConfigOdsluchane();
        DatesUtilOdsluchane datesUtil = new DatesUtilOdsluchane();
        HtmlParserOdsluchane parser = new HtmlParserOdsluchane(config, datesUtil);
        service = new HistoryServiceOdsluchane(parser, datesUtil, config);
        System.out.println("stations: " + config.getStations().size());
        System.out.println(config.getStations().size());
        comboStations.getItems().addAll(FXCollections.observableArrayList(config.getStations()));
    }

    @FXML
    public void btnSearchClicked(Event e){
      /*  StationArtistSummary summary = service.songsStationForArtistYearAndMonth("1", "coldplay", "2022", "2");
        label1.setText(summary.getArtist());
        label2.setText(String.valueOf(summary.getDistinctTitles().keySet().size()));
*/
    }

}
