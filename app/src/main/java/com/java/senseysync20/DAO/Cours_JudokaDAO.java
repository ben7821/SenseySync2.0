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
import com.java.senseysync20.METIER.Cours_Judoka;
import com.java.senseysync20.METIER.Judoka;

import java.time.LocalDate;
import java.util.ArrayList;

public class Cours_JudokaDAO extends MainDAO<Cours_Judoka> {
    private SQLiteSenseisync dbs;
    //déclaration des outils nécessaires à la base
    private static final String TABLE_JUDOKA = "COURS_JUDOKA";
    private static final String COL_ID_JUDOKA = "IDJUDOKA";
    private static final String COL_ID_COURS = "IDCOURS";
    private static final String COL_PRESSENCE = "PRESENCE";
    private SQLiteDatabase db;
    public Cours_JudokaDAO(Context context){
        dbs = new SQLiteSenseisync(context);
    }
    public void open(){
        db = dbs.getReadableDatabase();
    }
    public void close(){
        dbs.close();
    }
    public void insert(Cours_Judoka ma) {
        ContentValues ctv = new ContentValues();
        ctv.put(COL_ID_JUDOKA, ma.getIdJudoka());
        ctv.put(COL_ID_COURS, ma.getIdCours());
        ctv.put(COL_PRESSENCE, ma.isPresence());
        open();
        db.insert(TABLE_JUDOKA, null, ctv);
        close();
    }
    //insertion de la matière dans la base
    public void update(Cours_Judoka obj) {
        ContentValues ctv = new ContentValues();
        ctv.put(COL_ID_JUDOKA, obj.getIdJudoka());
        ctv.put(COL_ID_COURS, obj.getIdCours());
        ctv.put(COL_PRESSENCE, obj.isPresence());
        open();
        db.update(TABLE_JUDOKA, ctv, COL_ID_JUDOKA + " = " + obj.getIdJudoka() + " AND " + COL_ID_COURS + " = " + obj.getIdCours(), null);
        close();
    }

    //modification du nom et coefficient de la matière en fonction du numéro
    public void delete(Cours_Judoka obj) {
        open();
        db.delete(TABLE_JUDOKA,COL_ID_JUDOKA + " = " + obj.getIdJudoka() + " AND " + COL_ID_COURS + " = " + obj.getIdCours(),null);
        close();
    }
    //suppression de la matière en fonction de son numéro

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<Cours_Judoka> read() {
        Cursor cursor = db.query(TABLE_JUDOKA, null, null, null, null, null, null);
        ArrayList<Cours_Judoka> coursJudoka = new ArrayList<>();
        while (cursor.moveToNext()) {
            int idJudoka = cursor.getInt(0);
            int idCours = cursor.getInt(1);
            String presence = cursor.getString(2);
            Cours_Judoka coursJudoka1 = new Cours_Judoka(idJudoka, idCours, presence);
            coursJudoka.add(coursJudoka1);
        }
        cursor.close();
        return coursJudoka;
    }
    //Retourne la liste de toutes les matières enregistrées dans la base.

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Cours_Judoka read(long id,long id2){
        Cursor cursor = db.query(TABLE_JUDOKA, null, COL_ID_JUDOKA + " = " + id +" AND " + COL_ID_COURS + " = " + id2, null, null, null, null);
        Cours_Judoka coursJudoka = null;
        if (cursor.moveToFirst()) {
            int idJudoka = cursor.getInt(0);
            int idCours = cursor.getInt(1);
            String presence = cursor.getString(2);
            coursJudoka = new Cours_Judoka(idJudoka, idCours, presence);
        }
        cursor.close();
        return coursJudoka;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<Cours_Judoka> read(long id){
        Cursor cursor = db.query(TABLE_JUDOKA, null, COL_ID_JUDOKA + " = " + id, null, null, null, null);
        ArrayList<Cours_Judoka> coursJudoka = new ArrayList<>();
        while (cursor.moveToNext()) {
            int idJudoka = cursor.getInt(0);
            int idCours = cursor.getInt(1);
            String presence = cursor.getString(2);
            Cours_Judoka coursJudoka1 = new Cours_Judoka(idJudoka, idCours, presence);
            coursJudoka.add(coursJudoka1);
        }
        cursor.close();
        return coursJudoka;
    }

}