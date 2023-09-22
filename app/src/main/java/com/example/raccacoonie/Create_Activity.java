package com.example.raccacoonie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Create_Activity extends AppCompatActivity implements RecyclerViewInterface{

    int TEMP_CREATOR_ID = 2;
    private EditText recipe_title;
    private int id_post;
    private Button share_recipe_button;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);


        //get elements
        String[] countries = getResources().getStringArray(R.array.countries);
        AutoCompleteTextView country = findViewById(R.id.autoCompleteTextView_country);
        ArrayAdapter<String> country_adapter= new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,countries);
        country.setAdapter(country_adapter);
        share_recipe_button = findViewById(R.id.button_share);
        ImageButton search_button= findViewById(R.id.search_button);
        ImageButton home_button= findViewById(R.id.home_button);
        EditText ingredients = findViewById(R.id.editText_recipe_ingredients);
        EditText execution = findViewById(R.id.editText_recipe_execution);
        Spinner category_spinner = findViewById(R.id.spinner_category);
        Spinner tag_spinner = findViewById(R.id.spinner_tag);
        recipe_title = findViewById(R.id.editText_recipe_title);
        FrameLayout fragmentContainerBox = findViewById(R.id.bottom_container);



        DatabaseHandler myHandler = new DatabaseHandler(this,1);
        RecyclerAdapter adapter=new RecyclerAdapter(this,this);
        SharedPreferences prefs = getSharedPreferences("MyPrefs",0);
         int creator_id = prefs.getInt("id",-1);


        ImageButton log_out_button= findViewById(R.id.log_out_button);
        log_out_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loadLogInActivity= new Intent(Create_Activity.this,Log_In_Activity.class);
                startActivity(loadLogInActivity);
            }
        });

        home_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loadHomeActivity= new Intent(Create_Activity.this,Home_Activity.class);
                startActivity(loadHomeActivity);
            }
        });

        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loadSearchActivity= new Intent(Create_Activity.this,Search_Activity.class);
                startActivity(loadSearchActivity);
            }
        });

        ImageButton create_button= findViewById(R.id.create_button);
        create_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loadCreateActivity= new Intent(Create_Activity.this,Create_Activity.class);
                startActivity(loadCreateActivity);
            }
        });

        ImageButton book_button= findViewById(R.id.book_button);
        book_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loadSavedActivity= new Intent(Create_Activity.this,Saved_Activity.class);
                startActivity(loadSavedActivity);
            }
        });

        ImageButton profile_button= findViewById(R.id.profile_button);
        profile_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loadProfileActivity= new Intent(Create_Activity.this,Profile_Activity.class);
                startActivity(loadProfileActivity);
            }
        });


        share_recipe_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (ingredients.getText().length() == 0 || recipe_title.getText().length()==0|| execution.getText().length()==0)
                {

                    //Toast.makeText(Create_Activity.this, String.valueOf(myHandler.recipe_count()), Toast.LENGTH_SHORT).show();
                   // myHandler.printRecipes_db();


                    Toast.makeText(Create_Activity.this, "Please add all the necessary fields before sharing", Toast.LENGTH_SHORT).show();


                }else {

                    String recipe_title_str = recipe_title.getText().toString();
                    String execution_str = execution.getText().toString();
                    String ingredients_str = ingredients.getText().toString();
                    String country_str = country.getText().toString();

                    int dietaryStatus = 0;


                    if (tag_spinner.getSelectedItem().toString().equals("Pescetarian")) {
                        dietaryStatus = 1;
                    } else if (tag_spinner.getSelectedItem().toString().equals("Vegetarian")) {
                        dietaryStatus = 2;
                    } else if (tag_spinner.getSelectedItem().toString().equals("Vegan")) {
                        dietaryStatus = 3;
                    }

                    String category = "Dinner";
                    if (category_spinner.getSelectedItem().toString().equals("Snack")) {
                        category = "Snack";
                    } else if (category_spinner.getSelectedItem().toString().equals("Dessert")) {
                        category = "Dessert";
                    } else if (category_spinner.getSelectedItem().toString().equals("Drink")) {
                        category = "Drink";
                    }

                    Log.d("CREATOR ID TRANSMITTED TO THE ADAPTER", String.valueOf(creator_id));
                    Recipe user_recipe = new Recipe(creator_id, recipe_title_str, "nullpic", execution_str, ingredients_str, category, dietaryStatus, country_str);
                    //todo: add pictures, for now "nullpic" works fine


                    //backend for recipe input

                    int recipe_id = submitRecipe(user_recipe, myHandler);

                    adapter.ogrecipes.add(user_recipe);
                    adapter.post_id.add(recipe_id);
                    adapter.notifyItemInserted(adapter.getItemCount() - 1);

                    PhotoUploadFragment photoUploadFragment = new PhotoUploadFragment();
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.bottom_container, photoUploadFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();

                    findViewById(R.id.bottom_container).setVisibility(View.VISIBLE);

                }



            }









        });
        id_post=adapter.post_id.get(adapter.post_id.size()-1)+1;

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ( resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();


            saveImageToAppDirectory(imageUri,id_post);
            loadBitmapFromInternalStorage(this, String.valueOf(id_post));










        }
    }
    private void saveImageToAppDirectory(Uri imageUri,int title) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(imageUri);
            Bitmap imageBitmap = BitmapFactory.decodeStream(inputStream);

            if (imageBitmap != null) {
                saveBitmapToInternalStorage(this,imageBitmap, String.valueOf(title));
                Toast.makeText(this, "Image saved successfully", Toast.LENGTH_SHORT).show();
                Intent loadHomeActivity=new Intent(this,Home_Activity.class);
                startActivity(loadHomeActivity);
                findViewById(R.id.bottom_container).setVisibility(View.GONE);

            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to save image", Toast.LENGTH_SHORT).show();
        }
    }
    public void saveBitmapToInternalStorage(Context context, Bitmap bitmap, String fileName) {
        try {
            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            Toast.makeText(this,fileName,Toast.LENGTH_SHORT).show();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static Bitmap loadBitmapFromInternalStorage(Context context, String fileName) {
        File file = new File(context.getFilesDir(), fileName);
        if (file.exists()) {
            return BitmapFactory.decodeFile(file.getAbsolutePath());
        }
        return null;
    }


    public int submitRecipe(Recipe r, DatabaseHandler handler)
    {
        Toast.makeText(this, "Recipe was created", Toast.LENGTH_SHORT).show();

        handler.addRecipe(r);
        return handler.getLastId();
        //Toast.makeText(this, String.valueOf(handler.recipe_count()), Toast.LENGTH_SHORT).show();



    }

    @Override
    public void onItemClick(int position) {

    }
}
