package com.example.raccacoonie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class View_Profile_Activity extends AppCompatActivity implements RecyclerViewInterface {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    //RecyclerView.Adapter<RecyclerAdapter.ViewHolder> adapter;
    RecyclerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        DatabaseHandler my_handler = new DatabaseHandler(this,1);

        recyclerView = findViewById(R.id.recyclerView_profile_recipes);
//Set the layout of the items in the RecyclerView
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
//Set my Adapter for the RecyclerView
        adapter = new RecyclerAdapter(this,this);
        recyclerView.setAdapter(adapter);


        RecyclerAdapter my_adapter = new RecyclerAdapter(this,this);

        //GET CREATED RECIPES ONLY
        //backups
        ArrayList<Recipe> backup_recipe = new ArrayList<>(my_adapter.ogrecipes);
        ArrayList<Integer> backup_likes = new ArrayList<>(my_adapter.likes);
        ArrayList<Integer> backup_dislikes = new ArrayList<>(my_adapter.dislikes);
        int [] backup_drawables = my_adapter.recipeDrawables.clone();

        //changing the adapter


        my_adapter.notifyDataSetChanged();


        //UI
        TextView username_text = findViewById(R.id.textView_View_Username);




        //set UI
        SharedPreferences  sharedPreferences=this.getSharedPreferences("MyPrefs",0);
        Integer userid = sharedPreferences.getInt("id",-1);
        username_text.setText(my_handler.getUsernameById(userid));
    }

    @Override
    public void onItemClick(int position) {
        Intent loadViewRecipeActivity= new Intent(View_Profile_Activity.this,View_Recipe_Activity.class);
        startActivity(loadViewRecipeActivity);
    }
}