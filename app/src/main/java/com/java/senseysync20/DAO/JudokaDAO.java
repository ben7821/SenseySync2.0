package com.java.senseysync20.DAO;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.java.senseysync20.IHM.SQLiteSenseisync;
import com.java.senseysync20.METIER.Categorie;
import com.java.senseysync20.METIER.Judoka;

import java.time.LocalDate;
import java.util.ArrayList;

public class JudokaDAO extends MainDAO<Judoka> {
    private SQLiteSenseisync dbs;
    //déclaration des outils nécessaires à la base
    private static final String TABLE_JUDOKA = "JUDOKA";
    private static final String COL_ID_JUDOKA = "IDJUDOKA";
    private static final String COL_NOM = "NOM";
    private static final String COL_PRENOM = "PRENOM";
    private static final String COL_TEL = "TEL";
    private static final String COL_DATE_NAISSANCE = "DateNaissance";
    private static final String COL_CATEGORIE = "CATEGORIE";
    private SQLiteDatabase db;
    public JudokaDAO(Context context){
        dbs = new SQLiteSenseisync(context);
    }
    public void open(){
        db = dbs.getReadableDatabase();
    }
    public void close(){
        dbs.close();
    }
    public void insert(Judoka ma) {
        ContentValues ctv = new ContentValues();
        ctv.put(COL_NOM, ma.getNom());
        ctv.put(COL_PRENOM, ma.getPrenom());
        ctv.put(COL_TEL, ma.getTel());
        ctv.put(COL_DATE_NAISSANCE, String.valueOf(ma.getDateNaissance()));
        ctv.put(COL_CATEGORIE, ma.getCat().getIdCategorie());
        open();
        db.insert(TABLE_JUDOKA, null, ctv);
        close();
    }
    //insertion de la matière dans la base
    public void update(Judoka obj) {
        ContentValues ctv = new ContentValues();
        ctv.put(COL_NOM, obj.getNom());
        ctv.put(COL_PRENOM, obj.getPrenom());
        ctv.put(COL_TEL, obj.getTel());
        ctv.put(COL_DATE_NAISSANCE, String.valueOf(obj.getDateNaissance()));
        ctv.put(COL_CATEGORIE, obj.getCat().getIdCategorie());
        open();
        db.update(TABLE_JUDOKA, ctv, COL_ID_JUDOKA + " = " + obj.getIdjudoka(), null);
        close();
    }

    //modification du nom et coefficient de la matière en fonction du numéro
    public void delete(Judoka obj) {
        open();
        db.delete(TABLE_JUDOKA,COL_ID_JUDOKA + " = " + obj.getIdjudoka(),null);
        close();
    }
    //suppression de la matière en fonction de son numéro

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<Judoka> read() {
        Cursor curseurJudoka;
        ArrayList<Judoka> listeDesJudoka;
        Judoka unJudoka;
        int idJudoka;
        String Nom;
        String Prenom;
        String Tel;
        LocalDate DateNaissance;
        Categorie Categorie;
        //Ouverture de la base en lecture
        open();
        //Execution de la requête de selection avec tri par nom de matière
        curseurJudoka = db.query(TABLE_JUDOKA, null,null,null,null,null,
                COL_ID_JUDOKA);
        //Initialisation de la liste des matières
        listeDesJudoka = new ArrayList<Judoka>();
        //Parcours du curseur
        curseurJudoka.moveToFirst();
        while (!curseurJudoka.isAfterLast()){

            //Récupération des données de l'enregistrement
            idJudoka = curseurJudoka.getInt(0);
            Nom = curseurJudoka.getString(1);
            Prenom = curseurJudoka.getString(2);
            Tel = curseurJudoka.getString(3);
            DateNaissance = LocalDate.parse(curseurJudoka.getString(4));
            // ...
            int categoryId = curseurJudoka.getInt(5);
            Cursor categoryCursor = db.query("CATEGORIE", new String[]{"Libelle"}, "idCategorie = ?", new String[]{String.valueOf(categoryId)}, null, null, null);
            String categoryLabel = null;
            if (categoryCursor.moveToFirst()) {
                categoryLabel = categoryCursor.getString(0);
            }
            categoryCursor.close();
            Categorie = new Categorie(categoryId, categoryLabel);
            //Ajout de la matière dans la liste
            unJudoka = new Judoka(idJudoka,Nom,Prenom,Tel,DateNaissance,Categorie);
            listeDesJudoka.add(unJudoka);
            curseurJudoka.moveToNext();
        }
        curseurJudoka.close();
        close();
        return listeDesJudoka;
    }
    //Retourne la liste de toutes les matières enregistrées dans la base.

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Judoka read(long id){
        Cursor curseurJudoka;
        Judoka unJudoka;
        String Nom;
        String Prenom;
        String Tel;
        LocalDate DateNaissance;
        Categorie Categorie;
        //Ouverture de la base en lecture
        open();
        //Execution de la requête de selection avec tri par nom de matière
        curseurJudoka = db.query(TABLE_JUDOKA,null,COL_ID_JUDOKA + " = " + id,null,null,null,null);
        //Initialisation de la liste des matières
        curseurJudoka.moveToFirst();
        //Récupération des données de l'enregistrement
        Nom = curseurJudoka.getString(1);
        Prenom = curseurJudoka.getString(2);
        Tel = curseurJudoka.getString(3);
        DateNaissance = LocalDate.parse(curseurJudoka.getString(4));
        // ...
        int categoryId = curseurJudoka.getInt(5);
        Cursor categoryCursor = db.query("CATEGORIE", new String[]{"Libelle"}, "idCategorie = ?", new String[]{String.valueOf(categoryId)}, null, null, null);
        String categoryLabel = null;
        if (categoryCursor.moveToFirst()) {
            categoryLabel = categoryCursor.getString(0);
        }
        categoryCursor.close();
        Categorie = new Categorie(categoryId, categoryLabel);
        //Ajout de la matière dans la liste
        unJudoka = new Judoka((int)id,Nom,Prenom,Tel,DateNaissance,Categorie);
        curseurJudoka.close();
        close();
        return unJudoka;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<Judoka> allCatJudoka(int id){
        //return all judokas in the selected category
        Cursor curseurJudoka;
        ArrayList<Judoka> listeDesJudoka;
        Judoka unJudoka;
        int idJudoka;
        String Nom;
        String Prenom;
        String Tel;
        LocalDate DateNaissance;
        Categorie Categorie;
        //Ouverture de la base en lecture
        open();
        //Execution de la requête de selection avec tri par nom de matière
        curseurJudoka = db.query(TABLE_JUDOKA,null,COL_CATEGORIE + " = " + id,null,null,null,null);
        //Initialisation de la liste des matières
        listeDesJudoka = new ArrayList<Judoka>();
        //Parcours du curseur
        curseurJudoka.moveToFirst();
        while (!curseurJudoka.isAfterLast()){
            idJudoka = curseurJudoka.getInt(0);
            Nom = curseurJudoka.getString(1);
            Prenom = curseurJudoka.getString(2);
            Tel = curseurJudoka.getString(3);
            DateNaissance = LocalDate.parse(curseurJudoka.getString(4));
            // ...
            int categoryId = curseurJudoka.getInt(5);
            Cursor categoryCursor = db.query("CATEGORIE", new String[]{"Libelle"}, "idCategorie = ?", new String[]{String.valueOf(categoryId)}, null, null, null);
            String categoryLabel = null;
            if (categoryCursor.moveToFirst()) {
                categoryLabel = categoryCursor.getString(0);
            }
            categoryCursor.close();
            Categorie = new Categorie(categoryId, categoryLabel);
            //Ajout de la matière dans la liste
            unJudoka = new Judoka(idJudoka,Nom,Prenom,Tel,DateNaissance,Categorie);
            listeDesJudoka.add(unJudoka);
            curseurJudoka.moveToNext();
        }
        curseurJudoka.close();
        close();
        return listeDesJudoka;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<Judoka> getStudents(int courseId) {
        Cursor cursor = db.rawQuery("SELECT * FROM JUDOKA WHERE CATEGORIE IN (SELECT IDCATEGORIE FROM COURS_CATEGORIE WHERE IDCOURS = ?)", new String[]{String.valueOf(courseId)});
        ArrayList<Judoka> students = new ArrayList<>();
        while (cursor.moveToNext()) {
            int idJudoka = cursor.getInt(0);
            String nom = cursor.getString(1);
            String prenom = cursor.getString(2);
            String tel = cursor.getString(3);
            LocalDate dateNaissance = LocalDate.parse(cursor.getString(4));
            int categoryId = cursor.getInt(5);
            Cursor categoryCursor = db.query("CATEGORIE", new String[]{"Libelle"}, "idCategorie = ?", new String[]{String.valueOf(categoryId)}, null, null, null);
            String categoryLabel = null;
            if (categoryCursor.moveToFirst()) {
                categoryLabel = categoryCursor.getString(0);
            }
            categoryCursor.close();
            Categorie category = new Categorie(categoryId, categoryLabel);
            Judoka student = new Judoka(idJudoka, nom, prenom, tel, dateNaissance, category);
            students.add(student);
        }
        cursor.close();
        return students;
    }



}