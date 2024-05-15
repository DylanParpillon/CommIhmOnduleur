package com.example.interfaceonduleurv0.modeles;

public class ModeleInsert {
    private String name;
    private String macAddress;
    private String position;
    private boolean isOnline;
    private int batteryPercentage;
    private double outputActivePower;
    private double outputVoltage;
    private int outputSourcePriority;
    private boolean inverterFault;
    private boolean lineFail;
    private boolean voltageTooLow;
    private boolean voltageTooHigh;
    private boolean overTemperature;
    private boolean fanLocked;
    private boolean batteryLowAlarm;
    private boolean softFail;
    private boolean batteryTooLowToCharge;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String lat , String longi) {
        this.position = lat + ";" + longi;
    }

    public boolean getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(boolean online) {
        isOnline = online;
    }

    public int getBatteryPercentage() {
        return batteryPercentage;
    }

    public void setBatteryPercentage(int batteryPercentage) {
        this.batteryPercentage = batteryPercentage;
    }

    public double getOutputActivePower() {
        return outputActivePower;
    }

    public void setOutputActivePower(int outputActivePower) {
        this.outputActivePower = outputActivePower;
    }

    public double getOutputVoltage() {
        return outputVoltage;
    }

    public void setOutputVoltage(int outputVoltage) {
        this.outputVoltage = outputVoltage;
    }

    public int getOutputSourcePriority() {
        return outputSourcePriority;
    }

    public void setOutputSourcePriority(int outputSourcePriority) {
        this.outputSourcePriority = outputSourcePriority;
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
