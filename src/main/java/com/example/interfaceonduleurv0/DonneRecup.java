package com.example.interfaceonduleurv0;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DonneRecup {
    private String macAddress;
    private String date;
    private double euro;
    private double kilowatter;

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getDate() {
        return date;
    }

    public void setDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        this.date = sdf.format(date);

    }
    public double getEuro() {
        return euro;
    }

    public void setEuro(double euro) {
        this.euro = euro;
    }

    public double getKilowatter() {
        return kilowatter;
    }

    public void setKilowatter(double kilowatter) {
        this.kilowatter = kilowatter;
    }
}
