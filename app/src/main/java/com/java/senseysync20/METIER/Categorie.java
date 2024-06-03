package com.java.senseysync20.METIER;

import java.util.ArrayList;

public class Categorie {
    private int idCategorie;
    private String Libelle;

    public Categorie(int idCategorie, String libelle) {
        this.idCategorie = idCategorie;
        Libelle = libelle;
    }

    public int getIdCategorie() {
        return idCategorie;
    }

    public String getLibelle() {
        return Libelle;
    }

    public int getNumberJudoka() {
        return 0;
    }

    public void setLibelle(String libelle) {
        Libelle = libelle;
    }

    @Override
    public String toString() {
        return "Categorie{" +
                "idCategorie=" + idCategorie +
                ", Libelle='" + Libelle + '\'' +
                '}';
    }
}
