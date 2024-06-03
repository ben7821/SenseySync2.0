package com.java.senseysync20.DAO;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.java.senseysync20.IHM.SQLiteSenseisync;
import com.java.senseysync20.METIER.Categorie;
import com.java.senseysync20.METIER.Cours;
import com.java.senseysync20.METIER.Cours_Categorie;

import java.util.ArrayList;


public class Cours_CategorieDAO extends MainDAO<Cours_Categorie> {
    private SQLiteSenseisync dbs;
    private Context context;
    private static final String TABLE_CATEGORIE = "COURS CATEGORIE";
    private static final String COL_ID_CATEGORIE = "IDCATEGORIE";
    private static final String COL_ID_COURS = "IDCOURS";
    private SQLiteDatabase db;
    public Cours_CategorieDAO(Context context){
        this.context = context;
        dbs = new SQLiteSenseisync(context);
    }
    public void open(){
        db = dbs.getReadableDatabase();
    }
    public void close(){
        dbs.close();
    }
    public void insert(Cours_Categorie ma) {
        ContentValues ctv = new ContentValues();
        ctv.put(COL_ID_CATEGORIE, ma.getCategories());
        ctv.put(COL_ID_COURS, ma.getCours());
        open();
        db.insert(TABLE_CATEGORIE,null,ctv);
        close();
    }
    //insertion de la matière dans la base
    public void update(Cours_Categorie obj) {
        ContentValues ctv = new ContentValues();
        ctv.put(COL_ID_CATEGORIE,obj.getCategories());
            ctv.put(COL_ID_COURS,obj.getCours());
        open();
        db.update(TABLE_CATEGORIE,ctv,COL_ID_CATEGORIE + " = " + obj.getCategories() + " AND " + COL_ID_COURS + " = " + obj.getCours(),null);
        close();
    }
    //modification du nom et coefficient de la matière en fonction du numéro
    public void delete(Cours_Categorie obj) {
        open();
        db.delete(TABLE_CATEGORIE,COL_ID_CATEGORIE + " = " + obj.getCategories() + " AND " + COL_ID_COURS + " = " + obj.getCours(),null);
        close();
    }
    //suppression de la matière en fonction de son numéro

    public Cours_Categorie read(long id){
        Cursor curseurCours_Categorie;
        Cours_Categorie unCours_Categorie = null;
        int idCategorie;
        int idCours;
        open();
        curseurCours_Categorie = db.query(TABLE_CATEGORIE,null,COL_ID_CATEGORIE + " = " + id,null,null,null,null);
        if (curseurCours_Categorie.getCount() > 0){
            curseurCours_Categorie.moveToFirst();
            idCategorie = curseurCours_Categorie.getInt(0);
            idCours = curseurCours_Categorie.getInt(1);
            unCours_Categorie = new Cours_Categorie(idCategorie,idCours);
        }
        curseurCours_Categorie.close();
        close();
        return unCours_Categorie;
    }
    //lecture d'une matière en fonction de son numéro

    public ArrayList<Cours_Categorie> read() {
        Cursor curseurCours_Categorie;
        ArrayList<Cours_Categorie> listeDesCours_Categorie = new ArrayList<Cours_Categorie>();
        Cours_Categorie unCours_Categorie;
        int idCategorie;
        int idCours;
        open();
        curseurCours_Categorie = db.query(TABLE_CATEGORIE,null,null,null,null,null,null);
        curseurCours_Categorie.moveToFirst();
        while (!curseurCours_Categorie.isAfterLast()){
            idCategorie = curseurCours_Categorie.getInt(0);
            idCours = curseurCours_Categorie.getInt(1);
            unCours_Categorie = new Cours_Categorie(idCategorie,idCours);
            listeDesCours_Categorie.add(unCours_Categorie);
            curseurCours_Categorie.moveToNext();
        }
        curseurCours_Categorie.close();
        close();
        return listeDesCours_Categorie;
    }

}