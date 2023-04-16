package com.example.raccacoonie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class View_Recipe_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recipe);

        ImageButton view_profile_button= findViewById(R.id.view_profile_button);
        view_profile_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loadViewProfileActivity= new Intent(View_Recipe_Activity.this,View_Profile_Activity.class);
                startActivity(loadViewProfileActivity);
            }
        });
    }
}