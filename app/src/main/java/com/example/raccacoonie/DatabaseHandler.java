package com.example.raccacoonie;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "racacoonie";

    // Table Name
    //private static final String TABLE_NAME = "my_table";

    // Table Columns
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_AGE = "age";

    public DatabaseHandler(Context context,int version) {
        super(context, DATABASE_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create table query
        db.execSQL("DROP TABLE IF EXISTS RECIPE");
        db.execSQL("CREATE TABLE USER (\n" +
                "    email      TEXT    NOT NULL\n" +
                "                       UNIQUE,\n" +
                "    username   TEXT    UNIQUE\n" +
                "                       NOT NULL,\n" +
                "    password   TEXT    NOT NULL,\n" +
                "    picture_id NUMERIC,\n" +
                "    _id                PRIMARY KEY AUTOINCREMENT\n" +
                ");\n");
       /* // recipes
        db.execSQL("CREATE TABLE RECIPE (\n" +
                "    _id          INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    title       TEXT    NOT NULL,\n" +
                "    isVegan     INTEGER DEFAULT (0),\n" +
                "    pitcture_id INTEGER,\n" +
                "    execution   TEXT    DEFAULT 'none_provided'," +
                "    creator     INTEGER\n" +
                ");\n");*/
        //recipes
        db.execSQL("CREATE TABLE RECIPE (\n" +
                        "    _id          INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                        "    title       TEXT    NOT NULL,\n" +
                        "    dietaryStatus     INTEGER DEFAULT (0),\n" +
                        "    picture TEXT,\n" +
                        "    execution   TEXT    DEFAULT 'none_provided'," +
                        "    ingredients TEXT DEFAULT 'none provided'," +
                "    creator_id     INTEGER," +
                        "category    TEXT"+");\n");
        //posts
        db.execSQL("CREATE TABLE POST (\n" +
                "    recipe_id  INTEGER,\n" +
                "    creator_id INTEGER,\n" +
                "    likes      INTEGER,\n" +
                "    dislikes   INTEGER\n" +
                ");\n");
        db.execSQL("INSERT INTO USER VALUES('pavlidvg.csd.auth.gr','pavlidvg','testpass123',1)");
        db.execSQL("INSERT INTO USER VALUES('dmalamati.csd.auth.gr','dmalamati','test123',2)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + "USER");

        // Create tables again
        onCreate(db);

    }
    /*public boolean checkUsername(String username)
    { return true;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM USER WHERE username = ?",new String[] {username});
        if(cursor.getCount()>=1) {
            return true;
        } else {
            return  false;
        }*/

    public boolean  checkPassword(String user,String pass)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM USER WHERE username = ? AND password = ?";
        String[] selectionArgs = {user, pass};

        Cursor cursor = db.rawQuery(query, selectionArgs);
        //Cursor cursor = db.rawQuery("SELECT * FROM USER",null);


        if (cursor != null && cursor.moveToFirst()) {
            do {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < cursor.getColumnCount(); i++) {
                    String columnName = cursor.getColumnName(i);
                    String columnValue = cursor.getString(i);
                    sb.append(columnName).append(": ").append(columnValue).append(", ");
                }
                Log.d("USERS", sb.toString());
            } while (cursor.moveToNext());
        }//PRINT OUTPUT TO LOGFILE


        if(cursor.getCount()>=1)
        {
            cursor.close();
            return true;
        }
        cursor.close();
        return false ;
    }

    public String getUsers()
    {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM RECIPE",null);

        //return new String(String.valueOf(cursor.getCount()));
        return String.join(",",cursor.getColumnNames());
    }
    public void  addUser(User u)
    {
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues userValues= new ContentValues();
        userValues.put("username",u.getUsername());
        userValues.put("email",u.getEmail());
        userValues.put("password",u.getPassword());
        db.insert("USER",null,userValues);
        db.close();

    }

    public int addRecipe(Recipe r) //todo:add a user
    {
        r.setCreator_id(-1); // set the creator id
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues recipeValues = new ContentValues();
        recipeValues.put("title",r.getTitle());
        recipeValues.put("dietaryStatus",r.dietaryStatus);
        recipeValues.put("picture",r.getPicture());
        recipeValues.put("execution",r.getExecution());
        recipeValues.put("ingredients",r.getIngredients());
        recipeValues.put("creator_id",r.getCreator_id());
        recipeValues.put("category",r.getCategory());
        db.insert("RECIPE",null,recipeValues);
        db.close();

        return recipe_count();

    }
    public int recipe_count()
    {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM RECIPE",null);
        return cursor.getCount();
    }

    public String getRecipe(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = String.format("SELECT * FROM RECIPE");
        Cursor cursor =  db.rawQuery(query,null);
        if (cursor.getCount()>0)
        {
            while (cursor.moveToNext()) {

                for (int i = 0 ; i < cursor.getCount();i++)
                    Log.d("TEST",String.format(cursor.getColumnName(i)+" : " +cursor.getString(i)));
            }
            for (int i = 0 ; i < cursor.getColumnCount();i++)
            {
                Log.d("debug",cursor.getColumnNames()[i]);
            }
        }
        return "nope";
    }





/*"CREATE TABLE RECIPE (\n" +
                        "    _id          INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                        "    title       TEXT    NOT NULL,\n" +
                        "    dietaryStatus     INTEGER DEFAULT (0),\n" +
                        "    picture TEXT,\n" +
                        "    execution   TEXT    DEFAULT 'none_provided'," +
                        "    ingredients TEXT DEFAULT 'none provided'," +
                "    creator_id     INTEGER," +
                        "category    TEXT"+");\n"*/



}
