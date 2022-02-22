package pl.ryzykowski.javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pl.ryzykowski.javafx.config.ConfigOdsluchane;
import pl.ryzykowski.javafx.dto.StationArtistSummary;
import pl.ryzykowski.javafx.parser.HtmlParserOdsluchane;
import pl.ryzykowski.javafx.service.HistoryService;
import pl.ryzykowski.javafx.service.impl.HistoryServiceOdsluchane;
import pl.ryzykowski.javafx.util.DatesUtilOdsluchane;

import java.net.URL;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(new URL("file:///C:/Users/Ariel/IdeaProjects/JavaFx/src/main/resources/scene1.fxml"));
        VBox vbox = loader.<VBox>load();

        Scene scene = new Scene(vbox, 600,400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
