package com.example.raccacoonie;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.security.KeyStore;

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
    public void wipe_recipes()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM RECIPE;");
        Log.d("DEBUG","DELETED RECIPES");
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create table query

        db.execSQL("CREATE TABLE USER (\n" +
                "    email      TEXT    NOT NULL\n" +
                "                       UNIQUE,\n" +
                "    username   TEXT    UNIQUE\n" +
                "                       NOT NULL,\n" +
                "    password   TEXT    NOT NULL,\n" +
               // "    picture_id NUMERIC,\n" +
                "    _id        INTEGER        PRIMARY KEY AUTOINCREMENT\n" +
                ");\n");

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

        db.execSQL("CREATE TABLE LIKES(\n" +
                "user_id NUMERIC,\n" +
                "post_id NUMERIC,\n" +
                "PRIMARY KEY (user_id,post_id)\n" +
                ");");
        db.execSQL("CREATE TABLE SAVES(\n" +
                "user_id NUMERIC,\n" +
                "post_id NUMERIC,\n" +
                "PRIMARY KEY (user_id,post_id)\n" +
                ");");

        db.execSQL("CREATE TABLE DISLIKES(\n" +
                "user_id NUMERIC,\n" +
                "post_id NUMERIC,\n" +
                "PRIMARY KEY (user_id,post_id)\n" +
                ");");


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

    public boolean  checkPassword(String user,String pass)
    {
        if (user.equals("") && pass.equals("") )
        {
            return true;
            //TODO: DELETE IN FINAL BUILD
        }
        SQLiteDatabase db = this.getWritableDatabase();

        String[] args = {user,pass};
        Cursor auth = db.rawQuery("SELECT * FROM USER WHERE username = ? AND password = ?",args);
        if (auth.getCount()!= 0)
        {
            auth.close();
            return true;
        }
        auth.close();
        return false;



    }

    public void printRecipes_db()
    {
        Log.d("DEBUG","BEGIN PRINTING RECIPES");
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor1 = db.rawQuery("SELECT * FROM RECIPE", null);
        if (cursor1 != null && cursor1.moveToFirst()) {
            do {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < cursor1.getColumnCount(); i++) {
                    String columnName = cursor1.getColumnName(i);
                    String columnValue = cursor1.getString(i);
                    sb.append(columnName).append(": ").append(columnValue).append(", ");
                }
                Log.d("Recipe", sb.toString());
            } while (cursor1.moveToNext());
        }
        Log.d("DEBUG","END PRINTING RECIPES");
        cursor1.close();
    }
    public boolean checkEmail(String mail)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM USER WHERE email = ?";
        String[] selectionArgs = {mail};
        Cursor cursor = db.rawQuery(query, selectionArgs);

        if (cursor.getCount()>=1)
        {
            cursor.close();
            return true;
        }
        else
        {
            cursor.close();
            return false;
        }



    }

    public boolean isPostLikedByUser(int post_id, int user_id)
    {
        Log.d("post",String.valueOf(post_id));
        Log.d("user",String.valueOf(user_id));
        SQLiteDatabase db = this.getWritableDatabase();

        String [] arguments = {String.valueOf(post_id),String.valueOf(user_id)};
        Cursor query = db.rawQuery("SELECT * FROM LIKES WHERE post_id = ? AND user_id = ? ",arguments);

        if (query.getCount()> 0 )
        {
           Log.d("debug","Found");
            return true;
        }
        Log.d("debug","Not Found");
        return false;
    }
    public boolean isPostDislikedByUser(int post_id,int user_id)
    {

        SQLiteDatabase db = this.getWritableDatabase();

        String [] arguments = {String.valueOf(post_id),String.valueOf(user_id)};
        Cursor query = db.rawQuery("SELECT * FROM DISLIKES WHERE post_id = ? AND user_id = ? ",arguments);

        if (query.getCount()> 0 )
        {
            return true;
        }
        return false;
    }
    public void LikePost(int post_id, int user_id)
    {

        SQLiteDatabase db = this.getWritableDatabase();
        if (isPostLikedByUser(post_id,user_id))
        {
            return;
        }
        String [] arguments = {String.valueOf(post_id),String.valueOf(user_id)};
        ContentValues like = new ContentValues();
        like.put("post_id",arguments[0]);
        like.put("user_id",arguments[1]);
        db.insert("LIKES",null,like);
    }

    public void dislikePost(int post_id, int user_id)
    {

        SQLiteDatabase db = this.getWritableDatabase();
        if (isPostDislikedByUser(post_id,user_id))
        {
            return;
        }
        String [] arguments = {String.valueOf(post_id),String.valueOf(user_id)};
        ContentValues like = new ContentValues();
        like.put("post_id",arguments[0]);
        like.put("user_id",arguments[1]);
        db.insert("DISLIKES",null,like);
    }
    public void unlikePost(int post_id, int user_id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String [] arguments = {String.valueOf(post_id),String.valueOf(user_id)};
        if (isPostLikedByUser(post_id,user_id))
        {
            db.execSQL("DELETE FROM LIKES WHERE post_id = ? AND user_id = ?",arguments);
        }

    }
    public void removeDislikeFromPost(int post_id, int user_id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String [] arguments = {String.valueOf(post_id),String.valueOf(user_id)};
        if (isPostLikedByUser(post_id,user_id))
        {
            db.execSQL("DELETE FROM DISLIKES WHERE post_id = ? AND user_id = ?",arguments);
        }

    }
    public User getUserById(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        User user = null;

        String query = "SELECT * FROM USER WHERE _id = ?";
        String[] selectionArgs = {String.valueOf(id)};

        Cursor cursor = db.rawQuery(query, selectionArgs);
        if (cursor.moveToFirst()) {
            // Retrieve the user details from the cursor
            @SuppressLint("Range") String password = cursor.getString(cursor.getColumnIndex("password"));
            @SuppressLint("Range") String username = cursor.getString(cursor.getColumnIndex("username"));
            @SuppressLint("Range") String email=cursor.getString(cursor.getColumnIndex("email"));

            // Instantiate the User object and populate it with the retrieved data
            user = new User(username,password,email);



            // Close the cursor
            cursor.close();
        }
        return user;
    }
    //todo: build this
    public boolean userExists(int id)
    {
        return true;
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
    public int addRecipe(Recipe r)
    {
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
        return getLastId();
    }
    public int recipe_count()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM RECIPE",null);
        return cursor.getCount();
    }
    public String getUsernameById(int id)
    {
       if (id < 0 )
       {
           return "Raccacoonie team";
       }
        SQLiteDatabase db = this.getWritableDatabase();
        String [] args = {String.valueOf(id)};
        Cursor query = db.rawQuery("SELECT username FROM USER WHERE _id = ?",args);
        if (query.moveToFirst() == false)
        {
            return "Uknown User";
        }else
        {
            return query.getString(0);
        }


    }
   public Cursor rawQuery(String query)
   {
       SQLiteDatabase db = this.getWritableDatabase();
       return db.rawQuery(query,null);
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
    public boolean isPostSavedByUser(int post_id,int user_id)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String [] arguments = {String.valueOf(post_id),String.valueOf(user_id)};
        Cursor query = db.rawQuery("SELECT * FROM SAVES WHERE post_id = ? AND user_id = ? ",arguments);

        if (query.getCount()> 0 )
        {
            Log.d("CHECK","POST ALREADY SAVED");
            return true;
        }
        Log.d("CHECK","POST NOT SAVED");
        return false;
    }

    public void removeSave(int post_id,int user_id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String [] arguments = {String.valueOf(post_id),String.valueOf(user_id)};
        if (isPostSavedByUser(post_id,user_id))
        {
            Log.d("DEBUG","POST WAS SQL SAVED");
            db.execSQL("DELETE FROM SAVES WHERE post_id = ? AND user_id = ?",arguments);
        }
    }



    public void userSavePost(int user_id,int post_id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        if (isPostSavedByUser(post_id,user_id))
        {
            Log.d("DEBUG","POST WAS NTO DOULBE SAVED");
            return;
        }
        Log.d("DEBUG","will now be saved");
        String [] arguments = {String.valueOf(post_id),String.valueOf(user_id)};
        ContentValues like = new ContentValues();
        like.put("post_id",arguments[0]);
        like.put("user_id",arguments[1]);
        db.insert("SAVES",null,like);



    }

    public void printTable(String table_name)
    {
        Log.d(table_name,"-----");
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("CREATE TABLE IF NOT EXISTS LIKES(\n" +
                "user_id NUMERIC,\n" +
                "post_id NUMERIC,\n" +
                "PRIMARY KEY (user_id,post_id)\n" +
                ");");

        db.execSQL("CREATE TABLE  IF NOT EXISTS DISLIKES(\n" +
                "user_id NUMERIC,\n" +
                "post_id NUMERIC,\n" +
                "PRIMARY KEY (user_id,post_id)\n" +
                ");");

        db.execSQL("CREATE TABLE IF NOT EXISTS SAVES(\n" +
                "user_id NUMERIC,\n" +
                "post_id NUMERIC,\n" +
                "PRIMARY KEY (user_id,post_id)\n" +
                ");");
        Cursor cursor = db.rawQuery(String.format("SELECT * FROM %s",table_name),null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < cursor.getColumnCount(); i++) {
                    String columnName = cursor.getColumnName(i);
                    String columnValue = cursor.getString(i);
                    sb.append(columnName).append(": ").append(columnValue).append(", ");
                }
                Log.d("ROW", sb.toString());
            } while (cursor.moveToNext());}
        Log.d(table_name,"-----");
    }

    public  Cursor getRecipes(int count)
    {
        Cursor cursor;
        SQLiteDatabase db = this.getWritableDatabase();
        if (count==-1)
        {
             cursor = db.rawQuery("SELECT * FROM RECIPE",null);
        }else
        {
            String query = String.format("SELECT * FROM RECIPE LIMIT %S",count);
             cursor = db.rawQuery(query,null);
        }
        return cursor;
    }


    public int getLastId()
    {
        Cursor cursor;
        SQLiteDatabase db = this.getWritableDatabase();
        cursor = db.rawQuery("SELECT _id FROM RECIPE ORDER BY _id DESC LIMIT 1",null);
        if (cursor.getCount() < 1 )
        {
            Log.d("GAMO","TIN PANAGIA");
            return -1;
        }
        cursor.moveToFirst();




        return (int) cursor.getLong(0);
    }

    @SuppressLint("Range")
    public Integer getid(String user) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT _id FROM USER WHERE username =?";
        String[] selectionArgs = {user};
        Cursor cursor = db.rawQuery(query, selectionArgs);
        @SuppressLint("Range") int id = 0;
        if (cursor.moveToFirst()) {
            id = cursor.getInt(cursor.getColumnIndex("_id"));
        }
        return id;
    }
}