package com.example.raccacoonie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Search_Activity extends AppCompatActivity implements RecyclerViewInterface{

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        recyclerView = findViewById(R.id.recyclerView_search_recipes);
//Set the layout of the items in the RecyclerView
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
//Set my Adapter for the RecyclerView
        adapter = new RecyclerAdapter(this,this);
        recyclerView.setAdapter(adapter);



        //ADAPTER CARD BACKUPS FOR FILTERING
        ArrayList<Recipe> backup_rec = new ArrayList<>(adapter.ogrecipes);
        ArrayList<Integer> backup_post_id = new ArrayList<>(adapter.post_id);
        ArrayList<Integer> backup_drawbles = new ArrayList<>(adapter.drawables_list);

        //UI

        ImageButton log_out_button= findViewById(R.id.log_out_button);
        log_out_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loadLogInActivity= new Intent(Search_Activity.this,Log_In_Activity.class);
                startActivity(loadLogInActivity);
            }
        });

        ImageButton filters_button= findViewById(R.id.button_filters);
        filters_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFilterDialog(backup_rec,backup_post_id,backup_drawbles);
            }
        });

        ImageButton home_button= findViewById(R.id.home_button);
        home_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loadHomeActivity= new Intent(Search_Activity.this,Home_Activity.class);
                startActivity(loadHomeActivity);
            }
        });

        ImageButton search_button= findViewById(R.id.search_button);
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loadSearchActivity= new Intent(Search_Activity.this,Search_Activity.class);
                startActivity(loadSearchActivity);
            }
        });

        ImageButton create_button= findViewById(R.id.create_button);
        create_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loadCreateActivity= new Intent(Search_Activity.this,Create_Activity.class);
                startActivity(loadCreateActivity);
            }
        });

        ImageButton book_button= findViewById(R.id.book_button);
        book_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loadSavedActivity= new Intent(Search_Activity.this,Saved_Activity.class);
                startActivity(loadSavedActivity);
            }
        });

        ImageButton profile_button= findViewById(R.id.profile_button);
        profile_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loadProfileActivity= new Intent(Search_Activity.this,Profile_Activity.class);
                startActivity(loadProfileActivity);
            }
        });
    }
    /*ArrayList<Recipe> backup_rec = new ArrayList<>(adapter.ogrecipes);
        ArrayList<Integer> backup_post_id = new ArrayList<>(adapter.post_id);
        ArrayList<Integer> backup_drawbles = new ArrayList<>(adapter.drawables_list);*/

    public void showFilterDialog(ArrayList<Recipe> backup_rec,ArrayList<Integer> backup_post_id,ArrayList<Integer> backup_drawbles) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottom_sheet_layout);
        dialog.show();
        Spinner categoryf=dialog.findViewById(R.id.spinner_category_filter);
        Spinner tagf=dialog.findViewById(R.id.spinner_tag_filter);
        EditText ingredientsf=dialog.findViewById(R.id.editText_recipe_ingredients_filter);
        AutoCompleteTextView countryf=dialog.findViewById(R.id.autoCompleteTextView_country_filter);

        Button apply_filters_button=dialog.findViewById(R.id.button_apply_filters);


        apply_filters_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String category=categoryf.getSelectedItem().toString();
                String tag=tagf.getSelectedItem().toString();
                String ingredients=ingredientsf.getText().toString();
                String country=countryf.getText().toString();
                Toast.makeText(Search_Activity.this,"Filters Applied"+country,Toast.LENGTH_SHORT).show();



                dialog.dismiss();
                //adapter.updateRecipes(category,tag,ingredients,country);


                //Filtering
                //adapter.post_id.remove(i);
                //                adapter.drawables_list.remove(i);


                Map<String,Integer> types=new HashMap<>();
                adapter.ogrecipes.clear();
                adapter.post_id.clear();
                adapter.drawables_list.clear();

                for (int r = 0 ; r< backup_rec.size();r++)
                {
                    backup_rec.get(r).setCreator_id(backup_post_id.get(r));
                }

                types.put("Pescetarian",1);
                types.put("Vegetarian",2);
                types.put("Vegan",3);
                types.put("Tag",0);


                String [] filter_ingredients=ingredients.split(",");
                for (int i = 0; i < filter_ingredients.length; i++) {
                    filter_ingredients[i] = filter_ingredients[i].trim();
                }
                int pos = 0;

                for (Recipe recipe : backup_rec) {


               /*if ( (category.equals(recipe.category) || types.get(tag)==recipe.dietaryStatus)|| similarity>=2||country.equals(recipe.country)) {
                    recipes.add(recipe);
                }*/

                    if (category.equals("Category")) {
                        if (tag.equals("Tag")) {
                            if (country.equals(""))
                                Log.d("debug","null");
                            else {
                                if (recipe.country.equals((country))){
                                    adapter.ogrecipes.add(recipe);
                                    adapter.post_id.add(recipe.creator_id);
                                    adapter.drawables_list.add(backup_drawbles.get(pos));
                                }
                            }
                        } else {
                            if (country.equals("")) {
                                if (types.get(tag) == recipe.dietaryStatus) {
                                    adapter.ogrecipes.add(recipe);
                                    adapter.post_id.add(recipe.creator_id);
                                    adapter.drawables_list.add(backup_drawbles.get(pos));
                                }
                            } else {
                                if (types.get(tag) == recipe.dietaryStatus && recipe.country.equals(country)) {
                                    adapter.ogrecipes.add(recipe);
                                    adapter.post_id.add(recipe.creator_id);
                                    adapter.drawables_list.add(backup_drawbles.get(pos));
                                }
                            }
                        }

                    } else {
                        if (tag.equals("Tag")) {
                            if (country.equals("")) {
                                if (recipe.category.equals(category)) {
                                    Log.d("search","GOT HERE AND ADDING A RECIPE");
                                    adapter.ogrecipes.add(recipe);
                                    adapter.post_id.add(recipe.creator_id);
                                    adapter.drawables_list.add(backup_drawbles.get(pos));
                                    Log.d("id",String.valueOf(recipe.creator_id));
                                }
                            } else {
                                if (recipe.country.equals((country)) && recipe.category.equals(category)) {
                                    adapter.ogrecipes.add(recipe);
                                    adapter.post_id.add(recipe.creator_id);
                                    adapter.drawables_list.add(backup_drawbles.get(pos));
                                }
                            }
                        } else {
                            if (country.equals("")) {
                                if (types.get(tag) == recipe.dietaryStatus && recipe.category.equals(category)) {
                                    adapter.ogrecipes.add(recipe);
                                    adapter.post_id.add(recipe.creator_id);
                                    adapter.drawables_list.add(backup_drawbles.get(pos));
                                }
                            } else {
                                if (types.get(tag) == recipe.dietaryStatus && recipe.category.equals(category) && recipe.country.equals(country)) {
                                    adapter.ogrecipes.add(recipe);
                                    adapter.post_id.add(recipe.creator_id);
                                    adapter.drawables_list.add(backup_drawbles.get(pos));
                                }
                            }
                        }
                    }


                    if (ingredients.length()>0)
                    {


                        int similarity = 0;
                        String[] recipe_ingredients = recipe.ingredients.split(",");
                        for (int i = 0; i < recipe_ingredients.length; i++) {
                            recipe_ingredients[i] = recipe_ingredients[i].trim();
                        }

                        for (String word : filter_ingredients) {
                            if (Arrays.asList(recipe_ingredients).contains(word)) {
                                similarity++;
                            }
                        }
                        if (similarity==0)
                        {
                            //adapter.ogrecipes.remove(recipe);

                        }
                        else
                        {
                            adapter.ogrecipes.add(recipe);
                            adapter.post_id.add(recipe.creator_id);
                            adapter.drawables_list.add(backup_drawbles.get(pos));
                        }

                    }
                    pos++;}




                adapter.notifyDataSetChanged();


                //END FILTERING
            }
        });
        Button clearbtn=dialog.findViewById(R.id.button_clear_filters);
        clearbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.clearFilters();

                Toast.makeText(Search_Activity.this, "Filters Cleared", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }


    @Override
    public void onItemClick(int position) {
        Intent loadViewRecipeActivity= new Intent(Search_Activity.this,View_Recipe_Activity.class);
        startActivity(loadViewRecipeActivity);
    }
}