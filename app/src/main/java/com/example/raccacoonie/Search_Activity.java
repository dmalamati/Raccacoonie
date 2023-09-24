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
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        SearchView search = findViewById(R.id.search_view);
        search.setEnabled(true);
        search.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Search_Activity.this, "Search is unavailable. Try our filter dialog instead", Toast.LENGTH_SHORT).show();
            }
        });

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Toast.makeText(Search_Activity.this, "Search is unavailable. Try our filter dialog instead", Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

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

        EditText creatorf = dialog.findViewById(R.id.editText_recipe_creator_filter);


        Button apply_filters_button=dialog.findViewById(R.id.button_apply_filters);


        apply_filters_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String category=categoryf.getSelectedItem().toString();
                String tag=tagf.getSelectedItem().toString();
                String ingredients=ingredientsf.getText().toString();

                String creator = creatorf.getText().toString();

                dialog.dismiss();

                //Filtering
                boolean found_recipe = false;

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

                    if(category.equals("Category") && tag.equals("Tag") && creator.length()==0 && ingredients.length()==0){
                        adapter.ogrecipes.add(recipe);
                        adapter.post_id.add(recipe.creator_id);
                        adapter.drawables_list.add(backup_drawbles.get(pos));
                        found_recipe = true;
                    } else if (recipe.category.equals(category) && tag.equals("Tag") && creator.length()==0 && ingredients.length()==0) {
                        adapter.ogrecipes.add(recipe);
                        adapter.post_id.add(recipe.creator_id);
                        adapter.drawables_list.add(backup_drawbles.get(pos));
                        found_recipe = true;
                    } else if (category.equals("Category") && types.get(tag)==recipe.dietaryStatus && creator.length()==0 && ingredients.length()==0) {
                        adapter.ogrecipes.add(recipe);
                        adapter.post_id.add(recipe.creator_id);
                        adapter.drawables_list.add(backup_drawbles.get(pos));
                        found_recipe = true;
                    } else if (category.equals("Category") && tag.equals("Tag") && creator.length()>0 && ingredients.length()==0) {
                        //recipe.creator_id
                        adapter.ogrecipes.add(recipe);
                        adapter.post_id.add(recipe.creator_id);
                        adapter.drawables_list.add(backup_drawbles.get(pos));
                        found_recipe = true;
                    } else if (category.equals("Category") && tag.equals("Tag") && creator.length()==0 && ingredients.length()>0) {
                        StringBuilder regexPattern = new StringBuilder();
                        String[] recipe_ingredients = ingredients.split(",");
                        List<String> lst_ingredients = new ArrayList<>();

                        for (String ingredient : recipe_ingredients) {
                            String trimmedIngredient = ingredient.trim().toLowerCase();
                            lst_ingredients.add(trimmedIngredient);
                        }

                        for (String ingredient : lst_ingredients) {
                            regexPattern.append("(?=.*").append(Pattern.quote(ingredient)).append(")");
                        }
                        Pattern pattern = Pattern.compile(regexPattern.toString(), Pattern.CASE_INSENSITIVE);
                        Matcher matcher = pattern.matcher(recipe.ingredients);

                        if (matcher.find()) {
                            adapter.ogrecipes.add(recipe);
                            adapter.post_id.add(recipe.creator_id);
                            adapter.drawables_list.add(backup_drawbles.get(pos));
                            found_recipe = true;
                        }
                    } else if (recipe.category.equals(category)) {
                        if (types.get(tag) == recipe.dietaryStatus && creator.length() == 0 && ingredients.length() == 0) {
                            adapter.ogrecipes.add(recipe);
                            adapter.post_id.add(recipe.creator_id);
                            adapter.drawables_list.add(backup_drawbles.get(pos));
                            found_recipe = true;
                        } else if (tag.equals("Tag") && creator.length() > 0 && ingredients.length() == 0) {
                            // need to check the name of creator
                            adapter.ogrecipes.add(recipe);
                            adapter.post_id.add(recipe.creator_id);
                            adapter.drawables_list.add(backup_drawbles.get(pos));
                            found_recipe = true;
                        } else if (tag.equals("Tag") && creator.length() == 0 && ingredients.length() > 0) {
                            StringBuilder regexPattern = new StringBuilder();
                            String[] recipe_ingredients = ingredients.split(",");
                            List<String> lst_ingredients = new ArrayList<>();

                            for (String ingredient : recipe_ingredients) {
                                String trimmedIngredient = ingredient.trim().toLowerCase();
                                lst_ingredients.add(trimmedIngredient);
                            }

                            for (String ingredient : lst_ingredients) {
                                regexPattern.append("(?=.*").append(Pattern.quote(ingredient)).append(")");
                            }
                            Pattern pattern = Pattern.compile(regexPattern.toString(), Pattern.CASE_INSENSITIVE);
                            Matcher matcher = pattern.matcher(recipe.ingredients);

                            if (matcher.find()) {
                                adapter.ogrecipes.add(recipe);
                                adapter.post_id.add(recipe.creator_id);
                                adapter.drawables_list.add(backup_drawbles.get(pos));
                                found_recipe = true;
                            }
                        } else if (types.get(tag) == recipe.dietaryStatus && creator.length() > 0 && ingredients.length() == 0) {
                            // check for creator name
                            adapter.ogrecipes.add(recipe);
                            adapter.post_id.add(recipe.creator_id);
                            adapter.drawables_list.add(backup_drawbles.get(pos));
                            found_recipe = true;
                        } else if (types.get(tag) == recipe.dietaryStatus && creator.length() == 0 && ingredients.length() > 0) {
                            StringBuilder regexPattern = new StringBuilder();
                            String[] recipe_ingredients = ingredients.split(",");
                            List<String> lst_ingredients = new ArrayList<>();

                            for (String ingredient : recipe_ingredients) {
                                String trimmedIngredient = ingredient.trim().toLowerCase();
                                lst_ingredients.add(trimmedIngredient);
                            }

                            for (String ingredient : lst_ingredients) {
                                regexPattern.append("(?=.*").append(Pattern.quote(ingredient)).append(")");
                            }
                            Pattern pattern = Pattern.compile(regexPattern.toString(), Pattern.CASE_INSENSITIVE);
                            Matcher matcher = pattern.matcher(recipe.ingredients);

                            if (matcher.find()) {
                                adapter.ogrecipes.add(recipe);
                                adapter.post_id.add(recipe.creator_id);
                                adapter.drawables_list.add(backup_drawbles.get(pos));
                                found_recipe = true;
                            }
                        } else if (tag.equals("Tag") && creator.length() > 0 && ingredients.length() > 0) {
                            // check of creator name then ingredients
                            StringBuilder regexPattern = new StringBuilder();
                            String[] recipe_ingredients = ingredients.split(",");
                            List<String> lst_ingredients = new ArrayList<>();

                            for (String ingredient : recipe_ingredients) {
                                String trimmedIngredient = ingredient.trim().toLowerCase();
                                lst_ingredients.add(trimmedIngredient);
                            }

                            for (String ingredient : lst_ingredients) {
                                regexPattern.append("(?=.*").append(Pattern.quote(ingredient)).append(")");
                            }
                            Pattern pattern = Pattern.compile(regexPattern.toString(), Pattern.CASE_INSENSITIVE);
                            Matcher matcher = pattern.matcher(recipe.ingredients);

                            if (matcher.find()) {
                                adapter.ogrecipes.add(recipe);
                                adapter.post_id.add(recipe.creator_id);
                                adapter.drawables_list.add(backup_drawbles.get(pos));
                                found_recipe = true;
                            }
                        }
                    } else if (types.get(tag) == recipe.dietaryStatus && category.equals("Category")) {
                        if (creator.length() > 0 && ingredients.length() == 0){
                            //check for creator name
                            adapter.ogrecipes.add(recipe);
                            adapter.post_id.add(recipe.creator_id);
                            adapter.drawables_list.add(backup_drawbles.get(pos));
                            found_recipe = true;
                        } else if (creator.length() == 0 && ingredients.length() > 0) {
                            StringBuilder regexPattern = new StringBuilder();
                            String[] recipe_ingredients = ingredients.split(",");
                            List<String> lst_ingredients = new ArrayList<>();

                            for (String ingredient : recipe_ingredients) {
                                String trimmedIngredient = ingredient.trim().toLowerCase();
                                lst_ingredients.add(trimmedIngredient);
                            }

                            for (String ingredient : lst_ingredients) {
                                regexPattern.append("(?=.*").append(Pattern.quote(ingredient)).append(")");
                            }
                            Pattern pattern = Pattern.compile(regexPattern.toString(), Pattern.CASE_INSENSITIVE);
                            Matcher matcher = pattern.matcher(recipe.ingredients);

                            if (matcher.find()) {
                                adapter.ogrecipes.add(recipe);
                                adapter.post_id.add(recipe.creator_id);
                                adapter.drawables_list.add(backup_drawbles.get(pos));
                                found_recipe = true;
                            }
                        } else if (creator.length() > 0 && ingredients.length() > 0) {
                            //check for creator name
                            StringBuilder regexPattern = new StringBuilder();
                            String[] recipe_ingredients = ingredients.split(",");
                            List<String> lst_ingredients = new ArrayList<>();

                            for (String ingredient : recipe_ingredients) {
                                String trimmedIngredient = ingredient.trim().toLowerCase();
                                lst_ingredients.add(trimmedIngredient);
                            }

                            for (String ingredient : lst_ingredients) {
                                regexPattern.append("(?=.*").append(Pattern.quote(ingredient)).append(")");
                            }
                            Pattern pattern = Pattern.compile(regexPattern.toString(), Pattern.CASE_INSENSITIVE);
                            Matcher matcher = pattern.matcher(recipe.ingredients);

                            if (matcher.find()) {
                                adapter.ogrecipes.add(recipe);
                                adapter.post_id.add(recipe.creator_id);
                                adapter.drawables_list.add(backup_drawbles.get(pos));
                                found_recipe = true;
                            }
                        }
                    } else if (ingredients.length() > 0) {
                        if (creator.length() > 0){
                            // check for creator name
                            StringBuilder regexPattern = new StringBuilder();
                            String[] recipe_ingredients = ingredients.split(",");
                            List<String> lst_ingredients = new ArrayList<>();

                            for (String ingredient : recipe_ingredients) {
                                String trimmedIngredient = ingredient.trim().toLowerCase();
                                lst_ingredients.add(trimmedIngredient);
                            }

                            for (String ingredient : lst_ingredients) {
                                regexPattern.append("(?=.*").append(Pattern.quote(ingredient)).append(")");
                            }
                            Pattern pattern = Pattern.compile(regexPattern.toString(), Pattern.CASE_INSENSITIVE);
                            Matcher matcher = pattern.matcher(recipe.ingredients);

                            if (matcher.find()) {
                                adapter.ogrecipes.add(recipe);
                                adapter.post_id.add(recipe.creator_id);
                                adapter.drawables_list.add(backup_drawbles.get(pos));
                                found_recipe = true;
                            }
                        }
                    }
                    pos++;}

                adapter.notifyDataSetChanged();

                if(!found_recipe){
                    Toast.makeText(getApplicationContext(),"No matches found",Toast.LENGTH_SHORT).show();
                }

                //END FILTERING
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