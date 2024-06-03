package com.java.senseysync20.METIER;

import java.time.LocalDate;
import java.util.Date;

public class Cours {
    private int idCour;
    private LocalDate date;
    private String nom;


    public Cours(int idCour, LocalDate date, String nom) {
        this.idCour = idCour;
        this.date = date;
        this.nom = nom;

    }

    public int getIdCour() {
        return idCour;
    }
    public LocalDate getDate() {
        return date;
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public void setDate( LocalDate date) {
        this.date = date;
    }


    @Override
    public String toString() {
        return "Cour{" +
                "idCour=" + idCour +
                ", nom='" + nom +
                ", date=" + date +
                '}';
    }

}
