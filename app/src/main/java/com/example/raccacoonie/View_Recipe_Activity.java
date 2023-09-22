package com.example.raccacoonie;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
        ImageButton view_profile_button= findViewById(R.id.view_profile_button);
        TextView desc = findViewById(R.id.textView_description);
        TextView title = findViewById(R.id.textView_title);
        ImageView recipe_img = findViewById(R.id.imageView_recipe_image);
        TextView creatorName = findViewById(R.id.textView_profile_username);

        sharedPreferences=this.getSharedPreferences("MyPrefs",0);
        Integer userid = sharedPreferences.getInt("id",-1);
        //Toast.makeText(this, userid.toString(), Toast.LENGTH_SHORT).show();
        User loggedUser=dbh.getUserById(userid);

        ImageButton  back_button=findViewById(R.id.back_button);
        Intent intent = getIntent();

        //load up likes or dislikes



        Bundle data = intent.getExtras();
        int post_id = data.getInt("rec_id");
        Log.d("id given to recipe",String.valueOf(post_id));
        Log.d("id given to recipe",String.valueOf(post_id));

        Log.d("id given to recipe",String.valueOf(post_id));

        //load UI according to recipe
        if(data != null)
        {

            title.setText(data.getString("Rec_title"));
            if (data.getBoolean("customPic"))
            {
                Bitmap bmp = Create_Activity.loadBitmapFromInternalStorage(this,String.valueOf(data.getInt("Recipe_pic")));
                recipe_img.setImageBitmap(bmp);
            }else
            {
                recipe_img.setImageResource(data.getInt("Recipe_pic"));
            }

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
            creatorName.setText(dbh.getUsernameById(data.getInt("creator_id")));
            Log.d("CREATOR",String.valueOf(data.getInt("creator_id")));

            desc.setText(recipe_view.toString());
        }


        ImageButton log_out_button= findViewById(R.id.log_out_button);
        log_out_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loadLogInActivity= new Intent(View_Recipe_Activity.this,Log_In_Activity.class);
                startActivity(loadLogInActivity);
            }
        });

        view_profile_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loadProfileActivity= new Intent(View_Recipe_Activity.this,Profile_Activity.class);
                startActivity(loadProfileActivity);
            }
        });

        ImageButton like_button= findViewById(R.id.like_button);
        ImageButton dislike_button= findViewById(R.id.dislike_button);
        like_button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View v) {
                Log.d("USER",String.valueOf(userid));
                Log.d("RECIPE",String.valueOf(post_id));
                if (!isLiked && !isDisliked) {
                    isLiked = true;
                    like_button.setImageDrawable(getResources().getDrawable(R.drawable.like_color_icon));
                    increaseLikes(1);
                    dbh.LikePost(post_id,userid);

                } else if (!isLiked ) {
                    isLiked = true;
                    isDisliked=false;
                    like_button.setImageDrawable(getResources().getDrawable(R.drawable.like_color_icon));
                    dislike_button.setImageDrawable(getResources().getDrawable(R.drawable.dislike_icon));
                    increaseLikes(1);
                    increaseDislikes(-1);
                    dbh.LikePost(post_id,userid);
                    dbh.removeDislikeFromPost(post_id,userid);

                }else{
                    isLiked=false;
                    like_button.setImageDrawable(getResources().getDrawable(R.drawable.like_icon));
                    increaseLikes(-1);
                    dbh.unlikePost(post_id,userid);

                }
            }
        });
        dislike_button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View v) {
                Log.d("USER",String.valueOf(userid));
                Log.d("RECIPE",String.valueOf(post_id));
                if (!isDisliked && !isLiked) {
                    isDisliked = true;
                    dislike_button.setImageDrawable(getResources().getDrawable(R.drawable.dislike_color_icon));
                    increaseDislikes(1);
                    dbh.dislikePost(post_id,userid);

                } else if (!isDisliked ) {
                    isDisliked = true;
                    isLiked=false;
                    dislike_button.setImageDrawable(getResources().getDrawable(R.drawable.dislike_color_icon));
                    like_button.setImageDrawable(getResources().getDrawable(R.drawable.like_icon));
                    increaseDislikes(1);
                    increaseLikes(-1);
                    dbh.unlikePost(post_id,userid);
                    dbh.dislikePost(post_id,userid);

                }else{
                    isDisliked=false;
                    dislike_button.setImageDrawable(getResources().getDrawable(R.drawable.dislike_icon));
                    increaseDislikes(-1);
                    dbh.removeDislikeFromPost(post_id,userid);

                }
               // updateLikeStatus();
            }
        });
        if(dbh.isPostLikedByUser(post_id,userid))
        {
            like_button.performClick();
        }else if (dbh.isPostDislikedByUser(post_id,userid))
        {
            dislike_button.performClick();
        }


        ImageButton save_button = findViewById(R.id.save_button);


        save_button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View v) {
                if (!isSaved){
                    isSaved=true;
                    save_button.setImageDrawable(getResources().getDrawable(R.drawable.save_color_icon));
                    dbh.userSavePost(userid,post_id);


                }else{
                    isSaved=false;
                    dbh.removeSave(post_id,userid);
                    Log.d("AFTER","!!!!!!");
                    dbh.printTable("SAVES");
                    save_button.setImageDrawable(getResources().getDrawable(R.drawable.save_icon));
                }
                loggedUser.likedRecipes.add(data.getString("Rec_title"));

            }
        });
        if (dbh.isPostSavedByUser(post_id,userid))
        {
            Log.d("yoo","ENTERED TH IF STATEMENT");
            save_button.performClick();
        }
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }
}