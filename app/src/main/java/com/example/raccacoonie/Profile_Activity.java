package com.example.raccacoonie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Profile_Activity extends AppCompatActivity implements RecyclerViewInterface {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    //RecyclerView.Adapter<RecyclerAdapter.ViewHolder> adapter;
    RecyclerAdapter my_adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        recyclerView = findViewById(R.id.recyclerView_my_recipes);
//Set the layout of the items in the RecyclerView
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        DatabaseHandler my_handler = new DatabaseHandler(this,1);


//Set my Adapter for the RecyclerView
        my_adapter = new RecyclerAdapter(this,this);


        recyclerView.setAdapter(my_adapter);
        SharedPreferences sharedPreferences=this.getSharedPreferences("MyPrefs",0);
        Integer userid = sharedPreferences.getInt("id",-1);


        //GET CREATED RECIPES ONLY

        //backups
        ArrayList<Recipe> backup_recipe = new ArrayList<>(my_adapter.ogrecipes);
        ArrayList<Integer> backup_likes = new ArrayList<>(my_adapter.likes);
        ArrayList<Integer> backup_dislikes = new ArrayList<>(my_adapter.dislikes);
        int [] backup_drawables = my_adapter.recipeDrawables.clone();

        //changing the adapter
        int curr_creator_id = 0;
        for (int i = 0 ; i < my_adapter.ogrecipes.size();i++)
        {

            curr_creator_id = my_adapter.ogrecipes.get(i).creator_id;
            Log.d("Current Rcipe",String.valueOf(curr_creator_id));
/*
            Log.d("title",my_adapter.ogrecipes.get(i).title);
            Log.d("sizeof recipes",String.valueOf(my_adapter.ogrecipes.size()));
            Log.d("sizeof likes",String.valueOf(my_adapter.likes.size()));
            Log.d("sizeof likes",String.valueOf(my_adapter.dislikes.size()));
            Log.d("debug","---");
            */


            if (curr_creator_id != userid)
            {
                my_adapter.ogrecipes.remove(i);
                my_adapter.post_id.remove(i);
                //my_adapter.likes.remove(i);
                //my_adapter.dislikes.remove(i);
                i--;
            }
        }


        my_adapter.notifyDataSetChanged();

        //UI
        TextView username_text = findViewById(R.id.textView_View_Username);

        username_text.setText(my_handler.getUsernameById(userid));

        ImageButton home_button= findViewById(R.id.home_button);
        TextView recipes_shared = findViewById(R.id.textView_view_number_of_posts);
        recipes_shared.setText(String.valueOf(my_adapter.ogrecipes.size()));
        home_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loadHomeActivity= new Intent(Profile_Activity.this,Home_Activity.class);
                startActivity(loadHomeActivity);
            }
        });

        ImageButton search_button= findViewById(R.id.search_button);
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loadSearchActivity= new Intent(Profile_Activity.this,Search_Activity.class);
                startActivity(loadSearchActivity);
            }
        });

        ImageButton create_button= findViewById(R.id.create_button);
        create_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loadCreateActivity= new Intent(Profile_Activity.this,Create_Activity.class);
                startActivity(loadCreateActivity);
            }
        });

        ImageButton book_button= findViewById(R.id.book_button);
        book_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loadSavedActivity= new Intent(Profile_Activity.this,Saved_Activity.class);
                startActivity(loadSavedActivity);
            }
        });

        ImageButton profile_button= findViewById(R.id.profile_button);
        profile_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loadProfileActivity= new Intent(Profile_Activity.this,Profile_Activity.class);
                startActivity(loadProfileActivity);
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        // something to delete recipes
        Intent loadViewRecipeActivity= new Intent(Profile_Activity.this,View_Recipe_Activity.class);
        startActivity(loadViewRecipeActivity);
    }
}