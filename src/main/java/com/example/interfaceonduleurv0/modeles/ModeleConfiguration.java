package com.example.interfaceonduleurv0.modeles;

import java.io.Serializable;

public class ModeleConfiguration implements Serializable {
    private String mac;
    private String latitude;
    private String longitude;
    private String ipServeur;

    public String getMac() {
        return mac;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getIpServeur() {
        return ipServeur;
    }

    public ModeleConfiguration(String mac, String latitude, String longitude, String serveur) {
        this.mac = mac;
        this.latitude = latitude;
        this.longitude = longitude;
        this.ipServeur = serveur;
    }
}
