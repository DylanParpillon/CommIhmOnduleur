package com.example.interfaceonduleurv0;

import com.example.interfaceonduleurv0.controller.ConfigControleur;
import com.example.interfaceonduleurv0.controller.RootController;
import com.example.interfaceonduleurv0.controller.WifiControleur;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class Ihm extends Application {
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Info Systéme Onduleur");
        this.primaryStage.setResizable(false);
        this.primaryStage.setIconified(false);
        //this.primaryStage.initStyle(StageStyle.UNDECORATED); //pour rpi full screen
        this.primaryStage.setFullScreen(false);
        //this.primaryStage.setFullScreenExitHint(""); // disable fullscreen toggle hint
        //this.primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH); // prevent ESC toggling fullscreen
        rootView();
    }

    public void rootView() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(Ihm.class.getResource("root-view.fxml"));
            Parent rootLayout = fxmlLoader.load();
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            System.setProperty("com.sun.javafx.touch", "true");
            System.setProperty("com.sun.javafx.isEmbedded", "true");
            System.setProperty("com.sun.javafx.virtualKeyboard", "javafx");
            primaryStage.show();
            RootController rootController = fxmlLoader.getController();
            rootController.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void wifiView() {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Ihm.class.getResource("dialogues/wifi-root-view.fxml"));
            Parent wifiLayout = loader.load();
            Stage wifiStage = new Stage();
            wifiStage.setTitle("Configuration accés WIFI");
            wifiStage.initModality(Modality.WINDOW_MODAL);
            wifiStage.initOwner(primaryStage);
            wifiStage.setResizable(false);
            Scene scene = new Scene(wifiLayout);
            wifiStage.setScene(scene);
            // passe la ref du stage a l'objet scanController
            WifiControleur wificontroleur = loader.getController();
            wificontroleur.setWifiStage(wifiStage);
            // passe la ref de l'objet modele_simpleTextField a l'objet scanController
            //wificontroleur.setModele_simpleTextField(modeleWifi);
            wifiStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void configView() {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Ihm.class.getResource("dialogues/config-root-view.fxml"));
            Parent wifiLayout = loader.load();
            Stage configStage = new Stage();
            configStage.setTitle("Configuration Générale");
            configStage.initModality(Modality.WINDOW_MODAL);
            configStage.initOwner(primaryStage);
            configStage.setResizable(false);
            Scene scene = new Scene(wifiLayout);
            configStage.setScene(scene);
            // passe la ref du stage a l'objet scanController
            ConfigControleur configControleur = loader.getController();
            configControleur.setConfigStage(configStage);
            // passe la ref de l'objet modele_simpleTextField a l'objet scanController
            //wificontroleur.setModele_simpleTextField(modeleWifi);
            configStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}