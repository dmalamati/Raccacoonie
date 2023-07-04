package com.example.raccacoonie;

/*
db.execSQL("CREATE TABLE RECIPE (\n" +
                "    _id          INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    title       TEXT    NOT NULL,\n" +
                "    dietaryStatus     INTEGER DEFAULT (0),\n" +
                "    picture TEXT,\n" +
                "    execution   TEXT    DEFAULT 'none provided'," +
                "    ingredients TEXT DEFAULT 'none provided'
                "    creator_id     INTEGER\n" +
                     category    TEXT
                ");\n");
 */
public class Recipe{
    int id,creator_id;
    int dietaryStatus; // 1 is pesceterian, 2 is vegeterian and 3 is vegan, anything else means it contains meat
    String title,picture,execution,ingredients,category,country;

    public Recipe(int creator_id, String title, String picture, String execution, String ingredients, String category, int dietaryStatus, String country) {
        this.creator_id = creator_id;
        this.title = title;
        this.picture = picture;
        this.execution = execution;
        this.ingredients = ingredients;
        this.category = category;
        this.dietaryStatus = dietaryStatus;
        this.country = country;

        this.id = -1; // if not set by the database, id is -1
    }
    @Override
    public String toString()
    {

       String out = String.format("%s %nCreator:%s %nExecution:%s %n",this.title,this.creator_id,this.execution);
        return out;
    }
    public String getCountry()
    {
        return this.country;
    }

    public int getCreator_id() {
        return creator_id;
    }

    public void setCreator_id(int creator_id) {
        this.creator_id = creator_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getExecution() {
        return execution;
    }

    public void setExecution(String execution) {
        this.execution = execution;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    public void setDietaryStatus(int dietaryStatus){this.dietaryStatus=dietaryStatus;};
    public int getDietaryStatus(){return dietaryStatus;}


}
