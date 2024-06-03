package com.java.senseysync20.METIER;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;

public class Judoka {

    private int idjudoka;
    private String Nom;
    private String prenom;
    private String Tel;
    private LocalDate DateNaissance;
    private Categorie Cat;

    public Judoka(int idjudoka, String nom, String prenom, String tel,  LocalDate dateNaissance, Categorie Cat) {
        this.idjudoka = idjudoka;
        Nom = nom;
        this.prenom = prenom;
        Tel = tel;
        DateNaissance = dateNaissance;
        this.Cat = Cat;
    }
    public Judoka() {
    }

    public int getIdjudoka() {
        return idjudoka;
    }

    public String getNom() {
        return Nom;
    }

    public void setNom(String nom) {
        Nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTel() {
        return Tel;
    }

    public void setTel(String tel) {
        Tel = tel;
    }

    public  LocalDate getDateNaissance() {
        return DateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        DateNaissance = dateNaissance;
    }

    public Categorie getCat() {
        return Cat;
    }

    public void setCat(Categorie cat) {
        Cat = cat;
    }


    @Override
    public String toString() {
        return "Judoka{" +
                "idjudoka=" + idjudoka +
                ", Nom='" + Nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", Tel=" + Tel +
                ", DateNaissance=" + DateNaissance +
                ", Catégorie=" + Cat.toString() +
                '}';
    }

}
