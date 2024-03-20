package com.example.interfaceonduleurv0;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jssc.SerialPort;

import java.io.IOException;
import java.util.Scanner;

public class Ihm extends Application {
    //test normal c'est good !!!

    static Scanner sc = new Scanner(System.in);
    static SerialPort serialPort;


    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(Ihm.class.getResource("view.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setTitle("V2");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {

        launch();
    }
}