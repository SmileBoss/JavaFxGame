package ru.itis;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;


public class Main extends Application {
    Parent root;

    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL urlFxml = getClass().getResource("/view/startView.fxml");
        String stylesheet = getClass().getResource("/style.css").toExternalForm();
        loader.setLocation(urlFxml);
        root = loader.load();
        Scene mainScene = new Scene(root);
        mainScene.getStylesheets().add(stylesheet);
        stage.initStyle(StageStyle.TRANSPARENT);
        mainScene.setFill(Color.TRANSPARENT);
        stage.setScene(mainScene);
        stage.show();
    }
}
