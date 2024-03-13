package com.example.interfaceonduleurv0;

import com.example.interfaceonduleurv0.onduleur.Wks;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import jssc.SerialPort;
import jssc.SerialPortException;
import java.io.IOException;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Ihm extends Application {
    //test normal c'est good !!!
    static Wks wks = new Wks();
    static Scanner sc = new Scanner(System.in);
    static SerialPort serialPort;
    static Timer bdt = new Timer();
    static TimerTask timerTaskQPIGS = new TimerTask() {
        @Override
        public void run() {
            wks.demandeQPIGS();
        }
    };
    static TimerTask timerTaskQPIRI = new TimerTask() {
        @Override
        public void run() {
            wks.demandeQPIRI();
        }
    };
    static TimerTask timerTaskQPIWS = new TimerTask() {
        @Override
        public void run() {
            wks.demandeQPIWS();
        }
    };
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
        try {
            wks.initCom("COM3");
            wks.configurerParametres(2400, 8, 0, 1);
            bdt.scheduleAtFixedRate(timerTaskQPIGS, 0, 10000);
            bdt.scheduleAtFixedRate(timerTaskQPIRI,2000,10000);
            bdt.scheduleAtFixedRate(timerTaskQPIWS, 4000, 10000);

        } catch (SerialPortException e) {
            System.out.println(e.getExceptionType());

        }
        launch();
    }
}