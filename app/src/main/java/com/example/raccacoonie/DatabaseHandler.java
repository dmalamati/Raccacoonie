package com.example.raccacoonie;
import android.annotation.SuppressLint;
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
    public void wipe_recipes()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM RECIPE;");
        Log.d("DEBUG","DELETED RECIPES");
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
        //String query = "SELECT * FROM USER WHERE username = ? AND password = ?";
        //String[] selectionArgs = {user, pass};

        //Cursor cursor = db.rawQuery(query, selectionArgs);
        Cursor cursor = db.rawQuery("SELECT * FROM USER",null);
        for (int i = 0 ; i < 20; i++)
        {
            Log.d("cursor count",String.valueOf(cursor.getCount()));
        }



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

    public void printTable(String table_name)
    {
        Log.d("USERS","-----");
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(String.format("SELECT * FROM %s",table_name),null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < cursor.getColumnCount(); i++) {
                    String columnName = cursor.getColumnName(i);
                    String columnValue = cursor.getString(i);
                    sb.append(columnName).append(": ").append(columnValue).append(", ");
                }
                Log.d("USERS", sb.toString());
            } while (cursor.moveToNext());}
        Log.d("USERS","-----");
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