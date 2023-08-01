package com.example.raccacoonie;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class View_Recipe_Activity extends AppCompatActivity {

    private boolean isLiked;
    private boolean isDisliked;
    private boolean isSaved;
    SharedPreferences sharedPreferences ;
    DatabaseHandler dbh;


    public void updateLikeStatus(int likes,int dislikes)
    {

        TextView shown_likes = findViewById(R.id.textView_like_number);
        TextView shown_dislikes = findViewById(R.id.textView_dislike_number);
        shown_likes.setText(String.valueOf(likes));
        shown_dislikes.setText(String.valueOf(dislikes));

    }
    public void increaseLikes(int amount)
    {
        TextView shown_likes = findViewById(R.id.textView_like_number);
        int likes = Integer.parseInt(String.valueOf(shown_likes.getText()));
        shown_likes.setText(String.valueOf(likes + amount));
    }
    public void increaseDislikes(int amount)
    {
        TextView shown_dislikes = findViewById(R.id.textView_dislike_number);
        int dislikes = Integer.parseInt(String.valueOf(shown_dislikes.getText()));
        shown_dislikes.setText(String.valueOf(dislikes + amount));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        dbh=new DatabaseHandler(this,1);

        setContentView(R.layout.activity_view_recipe);

        //UI ELEMENTS
        //updateLikeStatus();
        ImageButton view_profile_button= findViewById(R.id.view_profile_button);
        TextView desc = findViewById(R.id.textView_description);
        TextView title = findViewById(R.id.textView_title);
        ImageView recipe_img = findViewById(R.id.imageView_recipe_image);

        sharedPreferences=this.getSharedPreferences("MyPrefs",0);
        Integer userid = sharedPreferences.getInt("id",-1);
        Toast.makeText(this, userid.toString(), Toast.LENGTH_SHORT).show();
        User loggedUser=dbh.getUserById(userid);

        ImageButton  back_button=findViewById(R.id.back_button);
        Intent intent = getIntent();

        Bundle data = intent.getExtras();
        //load UI according to recipe
        if(data != null)
        {

            title.setText(data.getString("Rec_title"));
            recipe_img.setImageResource(data.getInt("Recipe_pic"));
            StringBuilder recipe_view = new StringBuilder();
            recipe_view.append("Ingredients:\n");
            recipe_view.append(data.getString("Recipe_ingredients"));
            recipe_view.append("\n\n");
            recipe_view.append("Execution:\n");
            recipe_view.append(data.getString("Recipe_execution"));

            TextView shown_likes = findViewById(R.id.textView_like_number);
            TextView shown_dislikes = findViewById(R.id.textView_dislike_number);
            shown_likes.setText(String.valueOf(data.getInt("likes")));
            shown_dislikes.setText(String.valueOf(data.getInt("dislikes")));

            desc.setText(recipe_view.toString());
        }

        view_profile_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loadViewProfileActivity= new Intent(View_Recipe_Activity.this,View_Profile_Activity.class);
                startActivity(loadViewProfileActivity);
            }
        });

        ImageButton like_button= findViewById(R.id.like_button);
        ImageButton dislike_button= findViewById(R.id.dislike_button);
        like_button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View v) {
                if (!isLiked && !isDisliked) {
                    isLiked = true;
                    like_button.setImageDrawable(getResources().getDrawable(R.drawable.like_color_icon));
                    increaseLikes(1);

                } else if (!isLiked ) {
                    isLiked = true;
                    isDisliked=false;
                    like_button.setImageDrawable(getResources().getDrawable(R.drawable.like_color_icon));
                    dislike_button.setImageDrawable(getResources().getDrawable(R.drawable.dislike_icon));
                    increaseLikes(1);
                    increaseDislikes(-1);

                }else{
                    isLiked=false;
                    like_button.setImageDrawable(getResources().getDrawable(R.drawable.like_icon));
                    increaseLikes(-1);

                }
               // updateLikeStatus();
            }
        });
        dislike_button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View v) {
                if (!isDisliked && !isLiked) {
                    isDisliked = true;
                    dislike_button.setImageDrawable(getResources().getDrawable(R.drawable.dislike_color_icon));
                    increaseDislikes(1);

                } else if (!isDisliked ) {
                    isDisliked = true;
                    isLiked=false;
                    dislike_button.setImageDrawable(getResources().getDrawable(R.drawable.dislike_color_icon));
                    like_button.setImageDrawable(getResources().getDrawable(R.drawable.like_icon));
                    increaseDislikes(1);
                    increaseLikes(-1);

                }else{
                    isDisliked=false;
                    dislike_button.setImageDrawable(getResources().getDrawable(R.drawable.dislike_icon));
                    increaseDislikes(-1);

                }
               // updateLikeStatus();
            }
        });

        ImageButton save_button = findViewById(R.id.save_button);
        save_button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View v) {
                if (!isSaved){
                    isSaved=true;
                    save_button.setImageDrawable(getResources().getDrawable(R.drawable.save_color_icon));


                }else{
                    isSaved=false;
                    save_button.setImageDrawable(getResources().getDrawable(R.drawable.save_icon));
                }
                loggedUser.likedRecipes.add(data.getString("Rec_title"));
                Toast.makeText(View_Recipe_Activity.this, data.getString("Rec_title"), Toast.LENGTH_SHORT).show();
            }
        });
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }
}