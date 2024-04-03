package com.example.interfaceonduleurv0;
import javafx.application.Platform;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Ihm extends Application {
    //test normal c'est good !!!
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(Ihm.class.getResource("view.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setTitle("V2");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        stage.setOnCloseRequest(event -> Platform.exit());
    }
    public static void main(String[] args) {

        launch();
    }
}