package com.example.interfaceonduleurv0.modeles;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Cette classe représente les données récupérées.
 */
public class ModeleData {

    /** Adresse MAC associée aux données. */
    private String macAddress;

    /** Date des données. */
    private String date;

    /** Montant en euro. */
    private double euro;

    /** Quantité d'énergie en kilowatter. */
    private double kilowatter;

    /**
     * Renvoie l'adresse MAC associée aux données.
     *
     * @return l'adresse MAC
     */
    public String getMacAddress() {
        return macAddress;
    }

    /**
     * Définit l'adresse MAC associée aux données.
     *
     * @param macAddress l'adresse MAC à définir
     */
    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    /**
     * Renvoie la date des données.
     *
     * @return la date des données
     */
    public String getDate() {
        return date;
    }

    /**
     * Définit la date des données.
     *
     * @param date la date à définir
     */
    public void setDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        this.date = sdf.format(date);
    }

    /**
     * Renvoie le montant en euro.
     *
     * @return le montant en euro
     */
    public double getEuro() {
        return euro;
    }

    /**
     * Définit le montant en euro.
     *
     * @param euro le montant en euro à définir
     */
    public void setEuro(double euro) {
        this.euro = euro;
    }

    /**
     * Renvoie la quantité d'énergie en kilowatt.
     *
     * @return la quantité d'énergie en kilowatter
     */
    public double getKilowatter() {
        return kilowatter;
    }

    /**
     * Définit la quantité d'énergie en kilowatt.
     *
     * @param kilowatter la quantité d'énergie en kilowatt à définir
     */
    public void setKilowatter(double kilowatter) {
        this.kilowatter = kilowatter;
    }
}