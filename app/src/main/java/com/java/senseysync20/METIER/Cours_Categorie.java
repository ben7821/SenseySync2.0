package com.java.senseysync20.METIER;

public class Cours_Categorie {
    private int idCours;
    private int idCategories;
    public Cours_Categorie(int cours, int categories) {
        idCours = cours;
        idCategories = categories;
    }

    public int getCours() {
        return idCours;
    }

    public int getCategories() {
        return idCategories;
    }

    public void setCours(int cours) {
        idCours = cours;
    }

    public void setCategories(int categories) {
        idCategories = categories;
    }

    @Override
    public String toString() {
        return "Cour_Judoka{" +
                "Judoka=" + idCategories +
                ", Cour=" + idCours +
                '}';
    }
}
