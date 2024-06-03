package com.java.senseysync20.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.util.LocaleData;
import android.os.Build;
import android.os.LocaleList;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.java.senseysync20.IHM.SQLiteSenseisync;
import com.java.senseysync20.METIER.Cours;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class CoursDAO extends MainDAO<Cours> {
    private SQLiteSenseisync dbs;
    //déclaration des outils nécessaires à la base
    private static final String TABLE_CATEGORIE = "COURS";
    private static final String COL_ID_COURS = "IDCOURS";
    private static final String COL_DATE = "DATE";
    private static final String COL_NOM = "NOM";

    private SQLiteDatabase db;
    public CoursDAO(Context context){
        dbs = new SQLiteSenseisync(context);
    }
    public void open(){
        db = dbs.getReadableDatabase();
    }
    public void close(){
        dbs.close();
    }
    public void insert(Cours ma) {
        ContentValues ctv = new ContentValues();
        ctv.put(COL_DATE, String.valueOf(ma.getDate()));
        ctv.put(COL_NOM, ma.getNom());
        open();
        db.insert(TABLE_CATEGORIE,null,ctv);
        close();
    }
    //insertion de la matière dans la base
    public void update(Cours obj) {
        ContentValues ctv = new ContentValues();
        ctv.put(COL_DATE, String.valueOf(obj.getDate()));
        ctv.put(COL_NOM, obj.getNom());
        open();
        db.update(TABLE_CATEGORIE,ctv,COL_ID_COURS + " = " + obj.getIdCour(),null);
        close();
    }
    //modification du nom et coefficient de la matière en fonction du numéro
    public void delete(Cours obj) {
        open();
        db.delete(TABLE_CATEGORIE,COL_ID_COURS + " = " + obj.getIdCour(),null);
        close();
    }
    //suppression de la matière en fonction de son numéro
    @RequiresApi(api = Build.VERSION_CODES.O)
    public Cours read(long id){
        Cursor curseurCours;
        Cours unCours;
        LocalDate date;
        String nom;
        //Ouverture de la base en lecture
        open();
        //Execution de la requête de selection avec tri par nom de matière
        curseurCours = db.query(TABLE_CATEGORIE,null,COL_ID_COURS + " = " + id,null,null,null,null);
        //Initialisation de la liste des matières
        curseurCours.moveToFirst();
        //Récupération des données de l'enregistrement
        date = LocalDate.parse(curseurCours.getString(1));
        nom = curseurCours.getString(2);
        //Ajout de la matière dans la liste
        unCours = new Cours((int)id,date,nom);
        curseurCours.close();
        close();
        return unCours;
    }
    //Recherche le numéro de matière dans la base et la retourne

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<Cours> read(LocalDate date) {
        Cursor curseurCours;
        ArrayList<Cours> listeDesCours;
        Cours unCours;
        String date1;
        int idcours;
        String nom;
        //Ouverture de la base en lecture
        open();
        //Execution de la requête de selection avec tri par nom de matière
        curseurCours = db.query(TABLE_CATEGORIE,null,COL_DATE + " = " + "'" + date + "'" ,null,null,null,
                COL_ID_COURS);

        //Initialisation de la liste des matières
        listeDesCours = new ArrayList<Cours>();
        //Parcours du curseur
        curseurCours.moveToFirst();
        while (!curseurCours.isAfterLast()){

            idcours = curseurCours.getInt(0);
            date1 = curseurCours.getString(1);
            LocalDate localDate = LocalDate.parse(date1);
            nom = curseurCours.getString(2);

            unCours = new Cours(idcours,localDate,nom);
            listeDesCours.add(unCours);
            curseurCours.moveToNext();
        }
        curseurCours.close();
        close();
        Log.d("COURS", String.valueOf(listeDesCours));
        return listeDesCours;

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<Cours> read() {
        Cursor curseurCours;
        ArrayList<Cours> listeDesCours;
        Cours unCours;
        int idcours;
        LocalDate date;
        String nom;
        //Ouverture de la base en lecture
        open();
        //Execution de la requête de selection avec tri par nom de matière
        curseurCours = db.query(TABLE_CATEGORIE,null,null,null,null,null,
                COL_ID_COURS);
        //Initialisation de la liste des matières
        listeDesCours = new ArrayList<Cours>();
        //Parcours du curseur
        curseurCours.moveToFirst();
        while (!curseurCours.isAfterLast()){

            //Récupération des données de l'enregistrement
            idcours = curseurCours.getInt(0);
            date = LocalDate.parse(curseurCours.getString(1));
            nom = curseurCours.getString(2);
            //Ajout de la matière dans la liste
            unCours = new Cours(idcours,date,nom);
            listeDesCours.add(unCours);
            curseurCours.moveToNext();
        }
        curseurCours.close();
        close();
        return listeDesCours;
    }
    //Retourne la liste de toutes les matières enregistrées dans la base.
}
