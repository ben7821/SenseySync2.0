package com.java.senseysync20.DAO;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.java.senseysync20.IHM.SQLiteSenseisync;
import com.java.senseysync20.METIER.Categorie;

import java.util.ArrayList;


public class CategorieDAO extends MainDAO<Categorie> {
    private SQLiteSenseisync dbs;
    private Context context;
    private static final String TABLE_CATEGORIE = "CATEGORIE";
    private static final String COL_ID_CATEGORIE = "IDCATEGORIE";
    private static final String COL_LIB = "LIBELLE";
    private SQLiteDatabase db;
    public CategorieDAO(Context context){
        this.context = context; // And this line
        dbs = new SQLiteSenseisync(context);
    }
    public void open(){
        db = dbs.getReadableDatabase();
    }
    public void close(){
        dbs.close();
    }
    public void insert(Categorie ma) {
        ContentValues ctv = new ContentValues();
        ctv.put(COL_LIB,ma.getLibelle());
        open();
        db.insert(TABLE_CATEGORIE,null,ctv);
        close();
    }
    //insertion de la matière dans la base
    public void update(Categorie obj) {
        ContentValues ctv = new ContentValues();
        ctv.put(COL_LIB,obj.getLibelle());
        open();
        db.update(TABLE_CATEGORIE,ctv,COL_ID_CATEGORIE + " = " + obj.getIdCategorie(),null);
        close();
    }
    //modification du nom et coefficient de la matière en fonction du numéro
    public void delete(Categorie obj) {
        open();
        db.delete(TABLE_CATEGORIE,COL_ID_CATEGORIE + " = " + obj.getIdCategorie(),null);
        close();
    }
    //suppression de la matière en fonction de son numéro

    public Categorie read(long id){
        Cursor curseurCategorie;
        Categorie uneCategorie;
        String Lib;
        //Ouverture de la base en lecture
        open();
        //Execution de la requête de selection avec tri par nom de matière
        curseurCategorie = db.query(TABLE_CATEGORIE,null,COL_ID_CATEGORIE + " = " + id,null,null,null,null);
        //Initialisation de la liste des matières
        curseurCategorie.moveToFirst();
        //Récupération de la matière
        Lib = curseurCategorie.getString(1);
        uneCategorie = new Categorie((int)id,Lib);
        //Fermeture du curseur et de la base
        curseurCategorie.close();
        close();
        return uneCategorie;
    }
    //lecture d'une matière en fonction de son numéro

    public ArrayList<Categorie> read() {
        Cursor curseurCategorie;
        ArrayList<Categorie> listeDesCategories;
        Categorie uneCategorie;
        int idCategorie;
        String Lib;
        open();
        curseurCategorie = db.query(TABLE_CATEGORIE,null,null,null,null,null,null);
        listeDesCategories = new ArrayList<>();
        curseurCategorie.moveToFirst();
        while (!curseurCategorie.isAfterLast()){
            idCategorie = curseurCategorie.getInt(0);
            Lib = curseurCategorie.getString(1);
            uneCategorie = new Categorie(idCategorie,Lib);
            listeDesCategories.add(uneCategorie);
            curseurCategorie.moveToNext();
        }
        curseurCategorie.close();
        close();
        return listeDesCategories;
    }

}