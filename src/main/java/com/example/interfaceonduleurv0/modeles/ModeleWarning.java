package com.example.interfaceonduleurv0.modeles;

public class ModeleWarning {
    private String macAddress;
    private boolean inverterFault;
    private boolean lineFail;
    private boolean voltageTooLow;
    private boolean voltageTooHigh;
    private boolean overTemperature;
    private boolean fanLocked;
    private boolean batteryLowAlarm;
    private boolean softFail;
    private boolean batteryTooLowToCharge;

    // Getters et Setters
    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public boolean isInverterFault() {
        return inverterFault;
    }

    public void setInverterFault(boolean inverterFault) {
        this.inverterFault = inverterFault;
    }

    public boolean isLineFail() {
        return lineFail;
    }

    public void setLineFail(boolean lineFail) {
        this.lineFail = lineFail;
    }

    public boolean isVoltageTooLow() {
        return voltageTooLow;
    }

    public void setVoltageTooLow(boolean voltageTooLow) {
        this.voltageTooLow = voltageTooLow;
    }

    public boolean isVoltageTooHigh() {
        return voltageTooHigh;
    }

    public void setVoltageTooHigh(boolean voltageTooHigh) {
        this.voltageTooHigh = voltageTooHigh;
    }

    public boolean isOverTemperature() {
        return overTemperature;
    }

    public void setOverTemperature(boolean overTemperature) {
        this.overTemperature = overTemperature;
    }

    public boolean isFanLocked() {
        return fanLocked;
    }

    public void setFanLocked(boolean fanLocked) {
        this.fanLocked = fanLocked;
    }

    public boolean isBatteryLowAlarm() {
        return batteryLowAlarm;
    }

    public void setBatteryLowAlarm(boolean batteryLowAlarm) {
        this.batteryLowAlarm = batteryLowAlarm;
    }

    public boolean isSoftFail() {
        return softFail;
    }

    public void setSoftFail(boolean softFail) {
        this.softFail = softFail;
    }

    public boolean isBatteryTooLowToCharge() {
        return batteryTooLowToCharge;
    }

    public void setBatteryTooLowToCharge(boolean batteryTooLowToCharge) {
        this.batteryTooLowToCharge = batteryTooLowToCharge;
    }
}
