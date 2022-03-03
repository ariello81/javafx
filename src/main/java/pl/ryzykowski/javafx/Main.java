package pl.ryzykowski.javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.stage.Stage;

import java.net.URL;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        URL fxmlLocation = getClass().getClassLoader().getResource("scene1.fxml");
        loader.setLocation(fxmlLocation);
        SplitPane splitPane = loader.<SplitPane>load();
        Scene scene = new Scene(splitPane, 600,400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
