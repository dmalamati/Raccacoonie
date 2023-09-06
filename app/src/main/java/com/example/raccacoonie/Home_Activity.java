package com.example.raccacoonie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class Home_Activity extends AppCompatActivity implements RecyclerViewInterface  {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerAdapter adapter;
    SharedPreferences sharedPreferences;
    DatabaseHandler dbh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPreferences=this.getSharedPreferences("MyPrefs",0);
        Integer userid = sharedPreferences.getInt("id",-1);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        dbh=new DatabaseHandler(this,1);
        User loggedUser=dbh.getUserById(userid);



        recyclerView = findViewById(R.id.recyclerView_home_recipes);
//Set the layout of the items in the RecyclerView
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
//Set my Adapter for the RecyclerView
        adapter = new RecyclerAdapter(this,this);
        recyclerView.setAdapter(adapter);

        ImageButton home_button= findViewById(R.id.home_button);
        home_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loadHomeActivity= new Intent(Home_Activity.this,Home_Activity.class);
                startActivity(loadHomeActivity);
            }
        });
        //DEBUG

        ImageButton log_out_button= findViewById(R.id.log_out_button);
        log_out_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loadLogInActivity= new Intent(Home_Activity.this,Log_In_Activity.class);
                startActivity(loadLogInActivity);
            }
        });

        ImageButton search_button= findViewById(R.id.search_button);
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loadSearchActivity= new Intent(Home_Activity.this,Search_Activity.class);

                startActivity(loadSearchActivity);
            }
        });

        ImageButton create_button= findViewById(R.id.create_button);
        create_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loadCreateActivity= new Intent(Home_Activity.this,Create_Activity.class);
                startActivity(loadCreateActivity);
            }
        });

        ImageButton book_button= findViewById(R.id.book_button);
        book_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loadSavedActivity= new Intent(Home_Activity.this,Saved_Activity.class);
                adapter.fillLikedRecipes(loggedUser.likedRecipes);
                startActivity(loadSavedActivity);
            }
        });

        ImageButton profile_button= findViewById(R.id.profile_button);
        profile_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loadProfileActivity= new Intent(Home_Activity.this,Profile_Activity.class);
                startActivity(loadProfileActivity);
            }
        });

    }


    // LISTENER FOR ITEMS ON RECYCLERVIEW
    @Override
    public void onItemClick(int position) {
        Bundle data = new Bundle();
        //data.putParcelable("Recipe",);


        //Intent loadViewRecipeActivity= new Intent(Home_Activity.this,View_Recipe_Activity.class);
        //loadViewRecipeActivity.putExtras(data);
        //startActivity(loadViewRecipeActivity);
    }
}