package com.example.interfaceonduleurv0.RPI;

public class ModeleQPIRI {
    private String tensionNominaleDuReseau, courantNominalDuReseau;
    private String tensionNominaleDeSortie_AC, frequenceNominaleDeSortie_AC, courantNominalDeSortie_AC,puissanceApparenteDeSortie_AC, puissanceActiveDeSortie_AC;
    private String tensionNominaleBatterie, tensionRechargeBatterie,tensionBasseBatterie,tensionMasseBatterie,tensionEntretienBatterie,typeBatterie,courantDeCharge_AC_max,courantDeCharge_max ;
    private String plageDeTensionEntree,prioriteSourceDeSortie,prioriteSourceDuChargeur;
    private String nombreMaximumParallele,modeDeFonctionnement,topology,modeDeSortie;
    private String tensionDeRechargeBatterie,conditionDeMiseEnParallele,billanDePuissance_PV;

    public ModeleQPIRI() {
    }

    public String getTensionNominaleDuReseau() {
        return tensionNominaleDuReseau;
    }

    public void setTensionNominaleDuReseau(String tensionNominaleDuReseau) {
        this.tensionNominaleDuReseau = tensionNominaleDuReseau;
    }

    public String getCourantNominalDuReseau() {
        return courantNominalDuReseau;
    }

    public void setCourantNominalDuReseau(String courantNominalDuReseau) {
        this.courantNominalDuReseau = courantNominalDuReseau;
    }

    public String getTensionNominaleDeSortie_AC() {
        return tensionNominaleDeSortie_AC;
    }

    public void setTensionNominaleDeSortie_AC(String tensionNominaleDeSortie_AC) {
        this.tensionNominaleDeSortie_AC = tensionNominaleDeSortie_AC;
    }

    public String getFrequenceNominaleDeSortie_AC() {
        return frequenceNominaleDeSortie_AC;
    }

    public void setFrequenceNominaleDeSortie_AC(String frequenceNominaleDeSortie_AC) {
        this.frequenceNominaleDeSortie_AC = frequenceNominaleDeSortie_AC;
    }

    public String getCourantNominalDeSortie_AC() {
        return courantNominalDeSortie_AC;
    }

    public void setCourantNominalDeSortie_AC(String courantNominalDeSortie_AC) {
        this.courantNominalDeSortie_AC = courantNominalDeSortie_AC;
    }

    public String getPuissanceApparenteDeSortie_AC() {
        return puissanceApparenteDeSortie_AC;
    }

    public void setPuissanceApparenteDeSortie_AC(String puissanceApparenteDeSortie_AC) {
        this.puissanceApparenteDeSortie_AC = puissanceApparenteDeSortie_AC;
    }

    public String getPuissanceActiveDeSortie_AC() {
        return puissanceActiveDeSortie_AC;
    }

    public void setPuissanceActiveDeSortie_AC(String puissanceActiveDeSortie_AC) {
        this.puissanceActiveDeSortie_AC = puissanceActiveDeSortie_AC;
    }

    public String getTensionNominaleBatterie() {
        return tensionNominaleBatterie;
    }

    public void setTensionNominaleBatterie(String tensionNominaleBatterie) {
        this.tensionNominaleBatterie = tensionNominaleBatterie;
    }

    public String getTensionRechargeBatterie() {
        return tensionRechargeBatterie;
    }

    public void setTensionRechargeBatterie(String tensionRechargeBatterie) {
        this.tensionRechargeBatterie = tensionRechargeBatterie;
    }

    public String getTensionBasseBatterie() {
        return tensionBasseBatterie;
    }

    public void setTensionBasseBatterie(String tensionBasseBatterie) {
        this.tensionBasseBatterie = tensionBasseBatterie;
    }

    public String getTensionMasseBatterie() {
        return tensionMasseBatterie;
    }

    public void setTensionMasseBatterie(String tensionMasseBatterie) {
        this.tensionMasseBatterie = tensionMasseBatterie;
    }

    public String getTensionEntretienBatterie() {
        return tensionEntretienBatterie;
    }

    public void setTensionEntretienBatterie(String tensionEntretienBatterie) {
        this.tensionEntretienBatterie = tensionEntretienBatterie;
    }

    public String getTypeBatterie() {
        return typeBatterie;
    }

    public void setTypeBatterie(String typeBatterie) {
        this.typeBatterie = typeBatterie;
    }

    public String getCourantDeCharge_AC_max() {
        return courantDeCharge_AC_max;
    }

    public void setCourantDeCharge_AC_max(String courantDeCharge_AC_max) {
        this.courantDeCharge_AC_max = courantDeCharge_AC_max;
    }

    public String getCourantDeCharge_max() {
        return courantDeCharge_max;
    }

    public void setCourantDeCharge_max(String courantDeCharge_max) {
        this.courantDeCharge_max = courantDeCharge_max;
    }

    public String getPlageDeTensionEntree() {
        return plageDeTensionEntree;
    }

    public void setPlageDeTensionEntree(String plageDeTensionEntree) {
        this.plageDeTensionEntree = plageDeTensionEntree;
    }

    public String getPrioriteSourceDeSortie() {
        return prioriteSourceDeSortie;
    }

    public void setPrioriteSourceDeSortie(String prioriteSourceDeSortie) {
        this.prioriteSourceDeSortie = prioriteSourceDeSortie;
    }

    public String getPrioriteSourceDuChargeur() {
        return prioriteSourceDuChargeur;
    }

    public void setPrioriteSourceDuChargeur(String prioriteSourceDuChargeur) {
        this.prioriteSourceDuChargeur = prioriteSourceDuChargeur;
    }

    public String getNombreMaximumParallele() {
        return nombreMaximumParallele;
    }

    public void setNombreMaximumParallele(String nombreMaximumParallele) {
        this.nombreMaximumParallele = nombreMaximumParallele;
    }

    public String getModeDeFonctionnement() {
        return modeDeFonctionnement;
    }

    public void setModeDeFonctionnement(String modeDeFonctionnement) {
        this.modeDeFonctionnement = modeDeFonctionnement;
    }

    public String getTopology() {
        return topology;
    }

    public void setTopology(String topology) {
        this.topology = topology;
    }

    public String getModeDeSortie() {
        return modeDeSortie;
    }

    public void setModeDeSortie(String modeDeSortie) {
        this.modeDeSortie = modeDeSortie;
    }

    public String getTensionDeRechargeBatterie() {
        return tensionDeRechargeBatterie;
    }

    public void setTensionDeRechargeBatterie(String tensionDeRechargeBatterie) {
        this.tensionDeRechargeBatterie = tensionDeRechargeBatterie;
    }

    public String getConditionDeMiseEnParallele() {
        return conditionDeMiseEnParallele;
    }

    public void setConditionDeMiseEnParallele(String conditionDeMiseEnParallele) {
        this.conditionDeMiseEnParallele = conditionDeMiseEnParallele;
    }

    public String getBillanDePuissance_PV() {
        return billanDePuissance_PV;
    }

    public void setBillanDePuissance_PV(String billanDePuissance_PV) {
        this.billanDePuissance_PV = billanDePuissance_PV;
    }
}
