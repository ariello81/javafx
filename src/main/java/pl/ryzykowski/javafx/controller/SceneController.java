package pl.ryzykowski.javafx.controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import pl.ryzykowski.javafx.config.ConfigOdsluchane;
import pl.ryzykowski.javafx.dto.StationArtistSummary;
import pl.ryzykowski.javafx.parser.HtmlParserOdsluchane;
import pl.ryzykowski.javafx.service.HistoryService;
import pl.ryzykowski.javafx.service.impl.HistoryServiceOdsluchane;
import pl.ryzykowski.javafx.util.DatesUtilOdsluchane;

public class SceneController {

    @FXML
    Label label1, label2;

    @FXML
    Button button1;

    HistoryService service;

    public SceneController() {
        ConfigOdsluchane config = new ConfigOdsluchane();
        DatesUtilOdsluchane datesUtil = new DatesUtilOdsluchane();
        HtmlParserOdsluchane parser = new HtmlParserOdsluchane(config, datesUtil);
        service = new HistoryServiceOdsluchane(parser, datesUtil, config);
    }

    @FXML
    public void buttonClicked(Event e){
        StationArtistSummary summary = service.songsStationForArtistYearAndMonth("1", "coldplay", "2022", "2");
        label1.setText(summary.getArtist());
        label2.setText(String.valueOf(summary.getDistinctTitles().keySet().size()));

    }

}
