package com.example.raccacoonie;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class View_Recipe_Activity extends AppCompatActivity {

    private boolean isLiked;
    private boolean isDisliked;
    private boolean isSaved;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recipe);
        //UI ELEMENTS
        ImageButton view_profile_button= findViewById(R.id.view_profile_button);
        TextView desc = findViewById(R.id.textView_description);
        TextView title = findViewById(R.id.textView_title);
        ImageView recipe_img = findViewById(R.id.imageView_recipe_image);


        Intent intent = getIntent();

        Bundle data = intent.getExtras();
        //load UI according to recipe
        if(data != null)
        {
            Log.d("TITLE",title.getText().toString());
            Log.d("DATA",data.getString("Rec_title"));
            title.setText(data.getString("Rec_title"));
            recipe_img.setImageResource(data.getInt("Recipe_pic"));
            StringBuilder recipe_view = new StringBuilder();
            recipe_view.append("Ingredients:\n");
            recipe_view.append(data.getString("Recipe_ingredients"));
            recipe_view.append("\n\n");
            recipe_view.append("Execution:\n");
            recipe_view.append(data.getString("Recipe_execution"));

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
                } else if (!isLiked ) {
                    isLiked = true;
                    isDisliked=false;
                    like_button.setImageDrawable(getResources().getDrawable(R.drawable.like_color_icon));
                    dislike_button.setImageDrawable(getResources().getDrawable(R.drawable.dislike_icon));
                }else{
                    isLiked=false;
                    like_button.setImageDrawable(getResources().getDrawable(R.drawable.like_icon));
                }
            }
        });
        dislike_button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View v) {
                if (!isDisliked && !isLiked) {
                    isDisliked = true;
                    dislike_button.setImageDrawable(getResources().getDrawable(R.drawable.dislike_color_icon));
                } else if (!isDisliked ) {
                    isDisliked = true;
                    isLiked=false;
                    dislike_button.setImageDrawable(getResources().getDrawable(R.drawable.dislike_color_icon));
                    like_button.setImageDrawable(getResources().getDrawable(R.drawable.like_icon));
                }else{
                    isDisliked=false;
                    dislike_button.setImageDrawable(getResources().getDrawable(R.drawable.dislike_icon));
                }
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
            }
        });




    }
}