package com.example.raccacoonie;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DataTrashHandler extends SQLiteOpenHelper {
    public DataTrashHandler(@Nullable Context context, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "racacoonie", factory, version);
        Toast.makeText(context, "racacoonie db", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //users
        db.execSQL("CREATE TABLE USER (\n" +
                "    email      TEXT    NOT NULL\n" +
                "                       UNIQUE,\n" +
                "    username   TEXT    UNIQUE\n" +
                "                       NOT NULL,\n" +
                "    password   TEXT    NOT NULL,\n" +
                "    picture_id NUMERIC,\n" +
                "    _id                PRIMARY KEY\n" +
                ");\n");
        // recipes
        db.execSQL("CREATE TABLE RECIPE (\n" +
                "    id          INTEGER PRIMARY KEY,\n" +
                "    title       TEXT    NOT NULL,\n" +
                "    isVegan     INTEGER DEFAULT (0),\n" +
                "    pitcture_id INTEGER,\n" +
                "    execution   TEXT    DEFAULT 'none_provided'," +
                "    creator     INTEGER\n" +
                ");\n");

        //posts
        db.execSQL("CREATE TABLE POST (\n" +
                "    recipe_id  INTEGER,\n" +
                "    creator_id INTEGER,\n" +
                "    likes      INTEGER,\n" +
                "    dislikes   INTEGER\n" +
                ");\n");
        db.execSQL("INSERT INTO USER VALUES('pavlidvg.csd.auth.gr','pavlidvg','testpass123',1,1)");
        db.execSQL("INSERT INTO USER VALUES('dmalamati.csd.auth.gr','dmalamati','test123',2,2)");
        Log.d("DataTrashHandler", "Database created successfully!");
    }
    public void fill_test_users(SQLiteDatabase trashcan)
    /**
     * fills the database with some hard-coded users on startup, just to make testing easier
     */
    {

        trashcan.execSQL("INSERT INTO USER VALUES('pavlidvg.csd.auth.gr','pavlidvg','testpass123',1,1)");
        trashcan.execSQL("INSERT INTO USER VALUES('dmalamati.csd.auth.gr','dmalamati','test123',2,2)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + "USER" );

        // Create tables again
        onCreate(db);

    }
}
