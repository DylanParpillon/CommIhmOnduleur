package com.example.interfaceonduleurv0.controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class WifiControleur implements Initializable {
    public TextField tf_ssid;
    public TextField tf_mdp;
    public Button bt_valider;
    public Button bt_annuler;
    Stage wifiStage;

    public void setWifiStage(Stage wifiStage) {
        this.wifiStage = wifiStage;
    }

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  {@code null} if the location is not known.
     * @param resources The resources used to localize the root object, or {@code null} if
     *                  the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    wifiStage.close();
        }
        public void ev_button_valider(ActionEvent actionEvent){


        }
}
