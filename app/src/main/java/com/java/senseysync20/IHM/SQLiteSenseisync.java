package com.java.senseysync20.IHM;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteSenseisync extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 26;
    private static final String DATABASE_NAME = "BDDSenseySync2.0";


    public SQLiteSenseisync(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
    try {
        // Ajout de la tables Categorie
        db.execSQL("DROP TABLE IF EXISTS CATEGORIE");
        db.execSQL("CREATE TABLE CATEGORIE (" +
                "idCategorie INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "Libelle VARCHAR(100))");
        db.execSQL("INSERT INTO Categorie VALUES(0,'Non défini')");
        db.execSQL("INSERT INTO Categorie VALUES(1,'Eveils')");
        db.execSQL("INSERT INTO Categorie VALUES(2,'Poussinets')");
        db.execSQL("INSERT INTO Categorie VALUES(3,'Poussins')");
        db.execSQL("INSERT INTO Categorie VALUES(4,'Benjamins')");
        db.execSQL("INSERT INTO Categorie VALUES(5,'Minimes')");
        db.execSQL("INSERT INTO Categorie VALUES(6,'Cadets')");
        db.execSQL("INSERT INTO Categorie VALUES(7,'Juniors')");
        db.execSQL("INSERT INTO Categorie VALUES(8,'Seniors')");
        // Ajout de la tables Judoka
        db.execSQL("DROP TABLE IF EXISTS JUDOKA");
        db.execSQL("CREATE TABLE JUDOKA (" +
                "idJudoka INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "Nom VARCHAR(100), " +
                "Prenom VARCHAR(100), " +
                "Tel VARCHAR(100), " +
                "DateNaissance TEXT, " +
                "Categorie INTEGER, " +
                "FOREIGN KEY(Categorie) REFERENCES Categorie(idCategorie))");
        db.execSQL("INSERT INTO Judoka VALUES(1,'Toto','Tata',0488127318,'2001-01-01',1)");
        db.execSQL("INSERT INTO Judoka VALUES(2,'Titi','Tutu',0488127318,'2002-01-01',2)");
        db.execSQL("INSERT INTO Judoka VALUES(3,'Tata','Titi',0488127318,'2003-01-01',3)");
        db.execSQL("INSERT INTO Judoka VALUES(4,'Tutu','Toto',0488127318,'2004-01-01',4)");
        db.execSQL("INSERT INTO Judoka VALUES(5,'RaRa','Titi',0488127318,'2005-01-01',5)");
        db.execSQL("INSERT INTO Judoka VALUES(6,'RiRi','Toto',0488127318,'2006-01-01',6)");
        db.execSQL("INSERT INTO Judoka VALUES(7,'RuRu','Tata',0488127318,'2007-01-01',7)");
        db.execSQL("INSERT INTO Judoka VALUES(8,'ReRe','Tutu',0488127318,'2007-01-01',8)");

        // Ajout de la tables Cours
        db.execSQL("DROP TABLE IF EXISTS COURS");
        db.execSQL("CREATE TABLE COURS (" +
                "idCours INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "Date TEXT, " +
                "nom VARCHAR(100))");
        db.execSQL("INSERT INTO Cours VALUES(1,'2024-06-06','Cours1')");
        db.execSQL("INSERT INTO Cours VALUES(2,'2024-06-06','Cours2')");
        db.execSQL("INSERT INTO Cours VALUES(3,'2024-06-06','Cours3')");
        db.execSQL("INSERT INTO Cours VALUES(4,'2024-06-06','Cours4')");;
        db.execSQL("INSERT INTO Cours VALUES(5,'2024-06-07','Cours55')");
        db.execSQL("INSERT INTO Cours VALUES(6,'2024-06-08','Cours44')");
        db.execSQL("INSERT INTO Cours VALUES(7,'2024-06-09','Cours66')");
        db.execSQL("INSERT INTO Cours VALUES(8,'2024-06-09','Cours67')");
        db.execSQL("INSERT INTO Cours VALUES(9,'2024-06-09','Cours68')");
        db.execSQL("INSERT INTO Cours VALUES(10,'2024-06-09','Cours69')");
        db.execSQL("INSERT INTO Cours VALUES(11,'2024-06-09','Cours70')");
        db.execSQL("INSERT INTO Cours VALUES(12,'2024-06-09','Cours71')");
        db.execSQL("INSERT INTO Cours VALUES(13,'2024-06-09','Cours72')");
        db.execSQL("INSERT INTO Cours VALUES(14,'2024-06-09','Cours73')");
        db.execSQL("INSERT INTO Cours VALUES(15,'2024-06-09','Cours74')");
        db.execSQL("INSERT INTO Cours VALUES(16,'2024-06-09','Cours76')");
        db.execSQL("INSERT INTO Cours VALUES(17,'2024-06-09','Cours77')");
        db.execSQL("INSERT INTO Cours VALUES(18,'2024-06-09','Cours78')");

        db.execSQL("DROP TABLE IF EXISTS COURS_JUDOKA");
        db.execSQL("CREATE TABLE COURS_JUDOKA (" +
                "idCours INTEGER, " +
                "idJudoka INTEGER, " +
                "presence TEXT," +
                "PRIMARY KEY (idCours, idJudoka), " +
                "FOREIGN KEY(idCours) REFERENCES Cours(idCours), " +
                "FOREIGN KEY(idJudoka) REFERENCES Judoka(idJudoka))");
        db.execSQL("INSERT INTO COURS_JUDOKA VALUES(1,1,'P')");
        db.execSQL("INSERT INTO COURS_JUDOKA VALUES(2,1,'P')");
        db.execSQL("INSERT INTO COURS_JUDOKA VALUES(3,1,'P')");
        db.execSQL("INSERT INTO COURS_JUDOKA VALUES(4,1,'P')");
        db.execSQL("INSERT INTO COURS_JUDOKA VALUES(5,1,'P')");
        db.execSQL("INSERT INTO COURS_JUDOKA VALUES(6,1,'P')");
        db.execSQL("INSERT INTO COURS_JUDOKA VALUES(7,1,'P')");
        db.execSQL("INSERT INTO COURS_JUDOKA VALUES(8,1,'P')");
        db.execSQL("INSERT INTO COURS_JUDOKA VALUES(9,1,'P')");
        db.execSQL("INSERT INTO COURS_JUDOKA VALUES(10,1,'P')");
        db.execSQL("INSERT INTO COURS_JUDOKA VALUES(11,1,'P')");
        db.execSQL("INSERT INTO COURS_JUDOKA VALUES(12,1,'P')");
        db.execSQL("INSERT INTO COURS_JUDOKA VALUES(13,1,'P')");
        db.execSQL("INSERT INTO COURS_JUDOKA VALUES(1,2,'P')");
        db.execSQL("INSERT INTO COURS_JUDOKA VALUES(1,3,'A')");
        db.execSQL("INSERT INTO COURS_JUDOKA VALUES(1,4,'A')");
        db.execSQL("INSERT INTO COURS_JUDOKA VALUES(1,5,'P')");


        // Ajout de la tables agregat Cours_Judoka
        db.execSQL("DROP TABLE IF EXISTS COURS_CATEGORIE");
        db.execSQL("CREATE TABLE COURS_CATEGORIE (" +
                "idCours INTEGER, " +
                "idCategorie INTEGER, " +
                "PRIMARY KEY (idCours, idCategorie), " +
                "FOREIGN KEY(idCours) REFERENCES Cours(idCours), " +
                "FOREIGN KEY(idCategorie) REFERENCES Categorie(idCategorie))");
        db.execSQL("INSERT INTO COURS_CATEGORIE VALUES(1,1)");
        db.execSQL("INSERT INTO COURS_CATEGORIE VALUES(2,2)");
        db.execSQL("INSERT INTO COURS_CATEGORIE VALUES(2,3)");
        db.execSQL("INSERT INTO COURS_CATEGORIE VALUES(3,4)");
        db.execSQL("INSERT INTO COURS_CATEGORIE VALUES(4,5)");
        db.execSQL("INSERT INTO COURS_CATEGORIE VALUES(5,6)");
        db.execSQL("INSERT INTO COURS_CATEGORIE VALUES(6,7)");



    } catch (Exception e) {
        e.printStackTrace();
    }
}
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int
            newVersion) {
        onCreate(db);
    }
}