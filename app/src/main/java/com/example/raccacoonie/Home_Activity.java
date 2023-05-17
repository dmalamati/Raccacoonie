package com.example.raccacoonie;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class Home_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Intent in=getIntent();
        Bundle b=in.getExtras();
        if(b!=null)
        {
            String user=(String) b.get("user");
            Toast.makeText(this, user, Toast.LENGTH_SHORT).show();
        }
        ImageButton home_button= findViewById(R.id.home_button);
        home_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loadHomeActivity= new Intent(Home_Activity.this,Home_Activity.class);
                startActivity(loadHomeActivity);
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

        //////////////////////////////////////////////////////////
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button view_recipe_temp_button = findViewById(R.id.button_temp);
        view_recipe_temp_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loadViewRecipeActivity= new Intent(Home_Activity.this,View_Recipe_Activity.class);
                startActivity(loadViewRecipeActivity);
            }
        });
        /////////////////////////////////////////////////////////
    }
}