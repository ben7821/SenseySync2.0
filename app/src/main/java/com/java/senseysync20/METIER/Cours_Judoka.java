package com.java.senseysync20.METIER;

import androidx.annotation.NonNull;

public class Cours_Judoka {
    private int idCours;
    private int idJudoka;
    private String presence;

    public Cours_Judoka(int idCours, int idJudoka, String presence) {
        this.idCours = idCours;
        this.idJudoka = idJudoka;
        this.presence = presence;
    }

    public int getIdCours() {
        return idCours;
    }

    public void setIdCours(int idCours) {
        this.idCours = idCours;
    }

    public int getIdJudoka() {
        return idJudoka;
    }

    public void setIdJudoka(int idJudoka) {
        this.idJudoka = idJudoka;
    }

    public String isPresence() {
        return presence;
    }

    public void setPresence(String presence) {
        this.presence = presence;
    }

    @Override
    public String toString() {
        return "Cours_Judoka{" +
                "idCours=" + idCours +
                ", idJudoka=" + idJudoka +
                ", presence=" + presence +
                '}';
    }
}
