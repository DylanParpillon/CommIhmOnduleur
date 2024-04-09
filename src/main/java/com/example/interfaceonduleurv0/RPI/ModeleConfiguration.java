package com.example.interfaceonduleurv0.RPI;

public class ModeleConfiguration {
    private String mac;
    private String latitude;
    private String longitude;
    private String ipServeur;

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getIpServeur() {
        return ipServeur;
    }

    public void setIpServeur(String serveur) {
        this.ipServeur = serveur;
    }

    public ModeleConfiguration(String mac, String latitude, String longitude, String serveur) {
        this.mac = mac;
        this.latitude = latitude;
        this.longitude = longitude;
        this.ipServeur = serveur;
    }
}
