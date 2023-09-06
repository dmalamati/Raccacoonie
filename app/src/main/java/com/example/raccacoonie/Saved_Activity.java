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
import android.widget.Toast;

public class Saved_Activity extends AppCompatActivity implements RecyclerViewInterface  {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerAdapter adapter;
    DatabaseHandler dbh;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved);
        dbh=new DatabaseHandler(this,1);
        sharedPreferences=this.getSharedPreferences("MyPrefs",0);
        Integer userid = sharedPreferences.getInt("id",-1);

        recyclerView = findViewById(R.id.recyclerView_saved_recipes);
//Set the layout of the items in the RecyclerView
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
//Set my Adapter for the RecyclerView
        adapter = new RecyclerAdapter(this,this);
        recyclerView.setAdapter(adapter);
        User loggedUser=dbh.getUserById(userid);


        //filter adapter by saved
        int curr_post_id;
        for(int i = 0 ; i < adapter.ogrecipes.size();i++)
        {
            curr_post_id = adapter.post_id.get(i);
            if (!dbh.isPostSavedByUser(curr_post_id,userid))
            {
                adapter.ogrecipes.remove(i);
                adapter.post_id.remove(i);
                adapter.drawables_list.remove(i);
                i--;
            }
        }
        adapter.notifyDataSetChanged();



        ImageButton log_out_button= findViewById(R.id.log_out_button);
        log_out_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loadLogInActivity= new Intent(Saved_Activity.this,Log_In_Activity.class);
                startActivity(loadLogInActivity);
            }
        });

        ImageButton home_button= findViewById(R.id.home_button);
        home_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loadHomeActivity= new Intent(Saved_Activity.this,Home_Activity.class);
                startActivity(loadHomeActivity);
            }
        });

        ImageButton search_button= findViewById(R.id.search_button);
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loadSearchActivity= new Intent(Saved_Activity.this,Search_Activity.class);
                startActivity(loadSearchActivity);
            }
        });

        ImageButton create_button= findViewById(R.id.create_button);
        create_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loadCreateActivity= new Intent(Saved_Activity.this,Create_Activity.class);
                startActivity(loadCreateActivity);
            }
        });

        ImageButton book_button= findViewById(R.id.book_button);
        book_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loadSavedActivity= new Intent(Saved_Activity.this,Saved_Activity.class);
                adapter.fillLikedRecipes(loggedUser.likedRecipes);
                startActivity(loadSavedActivity);
            }
        });

        ImageButton profile_button= findViewById(R.id.profile_button);
        profile_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loadProfileActivity= new Intent(Saved_Activity.this,Profile_Activity.class);
                startActivity(loadProfileActivity);
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        Intent loadViewRecipeActivity= new Intent(Saved_Activity.this,View_Recipe_Activity.class);
        startActivity(loadViewRecipeActivity);
    }
}