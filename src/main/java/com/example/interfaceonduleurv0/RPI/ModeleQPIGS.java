package com.example.interfaceonduleurv0.RPI;

public class ModeleQPIGS {
    private String tensionDuReseau, frequenceDuReseau;
    private String tensionDeSortie_AC, frequenceDeSortie_AC, puissanceApparenteDeSortie_AC, puissanceActiveDeSortie_AC, pourcentageChargeSortie;
    private String tensionBUS, tensionBatterie, courantRechargeBatterie, pourcentageCapaciteBatterie, temperatureDuRadiateurOnduleur, courantEntreePv, tensionEntreePv;
    private String tentionBatterie, courantDechargeBatterie;
    private String statusMateriel1;
    private String tensionVentilateurs_10mv, versionEEPROM, puissanceChargePv;
    private String statusMateriel2;
    private String[] valeursPrioriteSourceSortie = {"Allumer", "charger en mode flottant"};

    public ModeleQPIGS() {
    }

    public String getTensionDuReseau() {
        return tensionDuReseau;
    }

    public void setTensionDuReseau(String tensionDuReseau) {
        this.tensionDuReseau = tensionDuReseau;
    }

    public String getFrequenceDuReseau() {
        return frequenceDuReseau;
    }

    public void setFrequenceDuReseau(String frequenceDuReseau) {
        this.frequenceDuReseau = frequenceDuReseau;
    }

    public String getTensionDeSortie_AC() {
        return tensionDeSortie_AC;
    }

    public void setTensionDeSortie_AC(String tensionDeSortie_AC) {
        this.tensionDeSortie_AC = tensionDeSortie_AC;
    }

    public String getFrequenceDeSortie_AC() {
        return frequenceDeSortie_AC;
    }

    public void setFrequenceDeSortie_AC(String frequenceDeSortie_AC) {
        this.frequenceDeSortie_AC = frequenceDeSortie_AC;
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

    public String getPourcentageChargeSortie() {
        return pourcentageChargeSortie;
    }

    public void setPourcentageChargeSortie(String pourcentageChargeSortie) {
        this.pourcentageChargeSortie = pourcentageChargeSortie;
    }

    public String getTensionBUS() {
        return tensionBUS;
    }

    public void setTensionBUS(String tensionBUS) {
        this.tensionBUS = tensionBUS;
    }

    public String getTensionBatterie() {
        return tensionBatterie;
    }

    public void setTensionBatterie(String tensionBatterie) {
        this.tensionBatterie = tensionBatterie;
    }

    public String getCourantRechargeBatterie() {
        return courantRechargeBatterie;
    }

    public void setCourantRechargeBatterie(String courantRechargeBatterie) {
        this.courantRechargeBatterie = courantRechargeBatterie;
    }

    public String getPourcentageCapaciteBatterie() {
        return pourcentageCapaciteBatterie;
    }

    public void setPourcentageCapaciteBatterie(String pourcentageCapaciteBatterie) {
        this.pourcentageCapaciteBatterie = pourcentageCapaciteBatterie;
    }

    public String getTemperatureDuRadiateurOnduleur() {
        return temperatureDuRadiateurOnduleur;
    }

    public void setTemperatureDuRadiateurOnduleur(String temperatureDuRadiateurOnduleur) {
        this.temperatureDuRadiateurOnduleur = temperatureDuRadiateurOnduleur;
    }

    public String getCourantEntreePv() {
        return courantEntreePv;
    }

    public void setCourantEntreePv(String courantEntreePv) {
        this.courantEntreePv = courantEntreePv;
    }

    public String getTensionEntreePv() {
        return tensionEntreePv;
    }

    public void setTensionEntreePv(String tensionEntreePv) {
        this.tensionEntreePv = tensionEntreePv;
    }

    public String getTentionBatterie() {
        return tentionBatterie;
    }

    public void setTentionBatterie(String tentionBatterie) {
        this.tentionBatterie = tentionBatterie;
    }

    public String getCourantDechargeBatterie() {
        return courantDechargeBatterie;
    }

    public void setCourantDechargeBatterie(String courantDechargeBatterie) {
        this.courantDechargeBatterie = courantDechargeBatterie;
    }

    public String getStatusMateriel1() {
        return statusMateriel1;
    }

    public void setStatusMateriel1(String statusMateriel1) {
        this.statusMateriel1 = statusMateriel1;
    }

    public String getTensionVentilateurs_10mv() {
        return tensionVentilateurs_10mv;
    }

    public void setTensionVentilateurs_10mv(String tensionVentilateurs_10mv) {
        this.tensionVentilateurs_10mv = tensionVentilateurs_10mv;
    }

    public String getVersionEEPROM() {
        return versionEEPROM;
    }

    public void setVersionEEPROM(String versionEEPROM) {
        this.versionEEPROM = versionEEPROM;
    }

    public String getPuissanceChargePv() {
        return puissanceChargePv;
    }

    public void setPuissanceChargePv(String puissanceChargePv) {
        this.puissanceChargePv = puissanceChargePv;
    }

    public String getStatusMateriel2() {
        return statusMateriel2;
    }

    public void setStatusMateriel2(String statusMateriel2) {
        this.statusMateriel2 = statusMateriel2;
    }

    public String[] getValeursPrioriteSourceSortie() {
        return valeursPrioriteSourceSortie;
    }

    public void setValeursPrioriteSourceSortie(String[] valeursPrioriteSourceSortie) {
        this.valeursPrioriteSourceSortie = valeursPrioriteSourceSortie;
    }
}
