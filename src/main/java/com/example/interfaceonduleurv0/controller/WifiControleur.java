package com.example.interfaceonduleurv0.controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
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
    bt_valider.setOnAction(((event -> {
        try {
            ev_button_valider(event);
        } catch (IOException | InterruptedException e) {
            System.err.println(e.getMessage());
        }
    })));
    bt_annuler.setOnAction(event -> {
        wifiStage.close();
    });

        }
        public void ev_button_valider(ActionEvent actionEvent) throws IOException, InterruptedException {
        if(!tf_ssid.getText().isEmpty()){
           String[] cmd = {"./determineMacEth.sh"};
           ProcessBuilder pb = new ProcessBuilder( cmd[0] , tf_ssid.getText() , tf_mdp.getText()  );
           Process p = pb.start();
           p.waitFor();
           wifiStage.close();
        }else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Erreur de saisie");
            if (tf_ssid.getText().isEmpty()) {
                alert.setContentText("Ssid vide !");
            }else {
                alert.setContentText("mot de pass vide");
            }
            alert.showAndWait();
        }

        }
}
