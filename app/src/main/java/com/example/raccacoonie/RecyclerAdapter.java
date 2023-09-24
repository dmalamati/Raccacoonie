package com.example.raccacoonie;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private final RecyclerViewInterface recyclerViewInterface;


    //Variables storing data to display for this example
    private final String[] titles = {"Fasolakia", "Fakes", "Pastitsio", "Keftedakia", "Makaronia me kima", "Spanakorizo", "Omeleta", "Kokkinisto me kritharaki"};
    private Context context;
    private final int[] images = { R.drawable.recipe_0, R.drawable.recipe_image, R.drawable.recipe_image, R.drawable.recipe_image, R.drawable.recipe_image,
            R.drawable.recipe_image, R.drawable.recipe_image, R.drawable.recipe_image };

      int[] recipeDrawables = {
            R.drawable.recipe_0,
            R.drawable.recipe_1,
            R.drawable.recipe_2,
            R.drawable.recipe_3,
            R.drawable.recipe_4,
            R.drawable.recipe_5
    };
      ArrayList<Integer> drawables_list = new ArrayList<>();
      ArrayList<Bitmap> bitmap_list = new ArrayList<>();
    ArrayList<Recipe> recipes = new ArrayList<>();
    ArrayList<Recipe>ogrecipes=new ArrayList<>();
    ArrayList<Integer> drawables_filter = new ArrayList<>();
    ArrayList<Integer> pics = new ArrayList<>();

    ArrayList<Integer> likes = new ArrayList<>();
    ArrayList<Integer> dislikes = new ArrayList<>();

    ArrayList<Boolean> isLiked = new ArrayList<>();

    ArrayList<Integer> post_id = new ArrayList<>();
    Map<String,Integer> types=new HashMap<>();
    int preloaded = 0;



    public RecyclerAdapter(RecyclerViewInterface recyclerViewInterface,Context context) {
        this.recyclerViewInterface = recyclerViewInterface;
        this.context = context;
        for (int a : recipeDrawables)
        {
            drawables_list.add(a);
        }
        fillPics();
        fillRecipeList();


        recipes.addAll(ogrecipes);
        for (Integer pic: recipeDrawables)
        {
            drawables_filter.add(pic);
        }
        types.put("Pescetarian",1);
        types.put("Vegetarian",2);
        types.put("Vegan",3);
        types.put("Tag",0);

    }
    public void generateRatings()
    /**
     * Fills the likes and dislikes of each recipe
     */
    {
        for (int i  = 0 ; i < ogrecipes.size();i++)
        {
            likes.add(ThreadLocalRandom.current().nextInt(5, 121));
            dislikes.add(ThreadLocalRandom.current().nextInt(0, 11));
        }
        if (likes != null && !likes.isEmpty()) {
            for (int  item : likes) {

            }
        } else {
            Log.d("likes", "ArrayList is null or empty");
        }
    }
    public void fillPics()
    /**
     * fills the pics arraylist with the appropriate amount of pictures
     */
    {
        for (int i = 0 ; i < recipes.size(); i ++)
        {
            pics.add(R.drawable.recipe_image);
        }
    }
    public void fillRecipeList()
    {
        ogrecipes.add(new Recipe(2, "Classic Chocolate Chip Cookies",
                "chocolate_chip_cookies.jpg",
                "1. Preheat the oven to 350°F. \n2. In a mixing bowl, cream together butter and sugars. \n3. Beat in eggs and vanilla. \n4. In a separate bowl, combine flour, baking soda, and salt. \n5. Gradually add the dry ingredients to the wet ingredients, mixing well. \n6. Stir in chocolate chips. \n7. Drop rounded tablespoons of dough onto a baking sheet. \n8. Bake for 10-12 minutes or until golden brown. \n9. Allow cookies to cool on the baking sheet for a few minutes before transferring to a wire rack.",
                "Butter, granulated sugar, brown sugar, eggs, vanilla extract, all-purpose flour, baking soda, salt, chocolate chips",
                "Snack", 2, "United States"));
        ogrecipes.add(new Recipe(-10, "Chicken Parmesan",
                "chicken_parmesan.jpg",
                "1. Preheat the oven to 375°F. \n2. Season chicken cutlets with salt, pepper, and Italian seasoning. \n3. Dip each cutlet in beaten egg, then coat with breadcrumbs. \n4. Heat oil in a skillet and cook the cutlets until golden brown. \n5. Place cooked cutlets in a baking dish and top with marinara sauce and shredded mozzarella cheese. \n6. Bake for 15-20 minutes or until the cheese is melted and bubbly. \n7. Serve with cooked spaghetti or your choice of pasta.",
                "Chicken cutlets, salt, pepper, Italian seasoning, eggs, breadcrumbs, oil, marinara sauce, shredded mozzarella cheese, spaghetti",
                "Dinner", 0, "Italy"));
        ogrecipes.add(new Recipe(-10, "Caprese Salad with Tuna",
                "caprese_salad.jpg",
                "1. Slice tomatoes and fresh mozzarella cheese. \n2. Arrange the tomato and mozzarella slices on a serving platter. \n3. Open the tuna and distribute it evenly on the salad \n4. Drizzle with olive oil and balsamic glaze. \n5. Sprinkle with salt, pepper, and fresh basil leaves. \n5. Serve immediately as a refreshing appetizer or side dish.",
                "Tomatoes, fresh mozzarella cheese, canned tuna, olive oil, balsamic glaze, salt, pepper, fresh basil leaves",
                "Snack", 1, "Greece"));
        ogrecipes.add(new Recipe(-10, "Vegetable Stir-Fry",
                "vegetable_stirfry.jpg",
                "1. Heat oil in a wok or skillet over high heat. \n2. Add sliced vegetables (e.g., bell peppers, carrots, broccoli, snap peas). \n3. Stir-fry for a few minutes until vegetables are crisp-tender. \n4. In a small bowl, mix together soy sauce, ginger, and garlic. \n5. Pour the sauce over the vegetables and stir-fry for another minute. \n6. Serve hot over steamed rice or noodles.",
                "Assorted vegetables (bell peppers, carrots, broccoli, snap peas), oil, soy sauce, ginger, garlic, steamed rice or noodles",
                "Dinner", 3, "China"));
        ogrecipes.add(new Recipe(-10, "Avocado Toast",
                "avocado_toast.jpg",
                "1. Toast a slice of bread until golden brown. \n2. Mash half an avocado and spread it on the toast. \n3. Sprinkle with salt, pepper, and red pepper flakes to taste. \n4. Optionally, top with sliced tomatoes, a drizzle of olive oil, or a squeeze of lemon juice. \n5. Enjoy as a quick and healthy breakfast or snack.",
                "Bread, avocado, salt, pepper, red pepper flakes",
                "Snack", 3, "United States"));
        ogrecipes.add(new Recipe(3, "Peanut Butter Banana Smoothie",
                "peanut_butter_smoothie.jpg",
                "1. In a blender, combine a ripe banana, a spoonful of peanut butter, and a cup of milk. \n2. Add a drizzle of honey and a pinch of cinnamon. \n3. Blend until smooth and creamy. \n4. Pour into a glass and enjoy as a delicious and energizing smoothie.",
                "Banana, peanut butter, milk, honey, cinnamon",
                "Drink", 2, "Greece"));

        preloaded = ogrecipes.size();
        int k = 1;
        for (Recipe rec : ogrecipes)
        {
            post_id.add(k-10);  //all preloaded recipes get an id form -10 to -4
            k++;
        }

        addRecipesFromDatabase(ogrecipes);

        likes.add(12);
        likes.add(25);
        likes.add(41);
        likes.add(14);
        likes.add(78);
        likes.add(54);

        dislikes.add(3);
        dislikes.add(5);
        dislikes.add(0);
        dislikes.add(0);
        dislikes.add(2);
        dislikes.add(9);

    }

    public void addRecipesFromDatabase(ArrayList<Recipe> recipe_list)
    {
        DatabaseHandler myHandler  = new DatabaseHandler(context.getApplicationContext(), 1);
        SharedPreferences prefs = context.getSharedPreferences("MyPrefs",0);
        int creator_id = prefs.getInt("id",-1);


        Cursor cursor = myHandler.getRecipes(-1);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < cursor.getColumnCount(); i++) {
                    String columnName = cursor.getColumnName(i);
                    String columnValue = cursor.getString(i);
                    sb.append(columnName).append(": ").append(columnValue).append(", ");
                }
                Log.d("Recipe", sb.toString());
                Recipe new_recipe = new Recipe(cursor.getInt(6),cursor.getString(1),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(7),cursor.getInt(2),"Greece"); //ADD RECIPES

                ogrecipes.add(new_recipe);
                post_id.add(cursor.getInt(0)); //add the id so the recycler post_id array has access

                drawables_list.add(R.drawable.recipe_image);


            } while (cursor.moveToNext());}
        //ogrecipes.add(new Recipe(-2,"test title","nullpic","test exec","test ingredients","snack",2,"Greece"));
        notifyDataSetChanged();
    }

    public ArrayList<Recipe> getOgrecipes()
    {
        return ogrecipes;
    }

    public void addNewRecipeToView(int count)
    /**
     * Adds the newest entries to the RecyclerView.
     * @param count is how many entries it will add, starting from the newest rows of the RECIPE table
     *              returns if count < 1
     */
    {
        if (count<1)
        {
            Log.d("DEBUG error","asked to add less than one recipe on view");
        }else
        {
            DatabaseHandler myHandler  = new DatabaseHandler(context.getApplicationContext(), 1);
             Cursor cursor = myHandler.rawQuery("SELECT * FROM mytable ORDER BY ROWID DESC LIMIT 1");

        }
    }


    public void updateRecipes(String category,String tag,String ingredients,String country) {

        String [] filter_ingredients=ingredients.split(",");
        for (int i = 0; i < filter_ingredients.length; i++) {
            filter_ingredients[i] = filter_ingredients[i].trim();
        }
            recipes.clear();
            for (Recipe recipe : ogrecipes) {


               /*if ( (category.equals(recipe.category) || types.get(tag)==recipe.dietaryStatus)|| similarity>=2||country.equals(recipe.country)) {
                    recipes.add(recipe);
                }*/

                    if (category.equals("Category")) {
                        if (tag.equals("Tag")) {
                            if (country.equals(""))
                                Toast.makeText(context, "null", Toast.LENGTH_SHORT).show();
                            else {
                                if (recipe.country.equals((country))){
                                    recipes.add(recipe);
                                }
                            }
                        } else {
                            if (country.equals("")) {
                                if (types.get(tag) == recipe.dietaryStatus) {
                                    recipes.add(recipe);
                                }
                            } else {
                                if (types.get(tag) == recipe.dietaryStatus && recipe.country.equals(country)) {
                                    recipes.add(recipe);
                                }
                            }
                        }

                    } else {
                        if (tag.equals("Tag")) {
                            if (country.equals("")) {
                                if (recipe.category.equals(category)) {
                                    recipes.add(recipe);
                                }
                            } else {
                                if (recipe.country.equals((country)) && recipe.category.equals(category)) {
                                    recipes.add(recipe);
                                }
                            }
                        } else {
                            if (country.equals("")) {
                                if (types.get(tag) == recipe.dietaryStatus && recipe.category.equals(category)) {
                                    recipes.add(recipe);
                                }
                            } else {
                                if (types.get(tag) == recipe.dietaryStatus && recipe.category.equals(category) && recipe.country.equals(country)) {
                                    recipes.add(recipe);
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
                    recipes.remove(recipe);
                }
                else
                {
                    recipes.add(recipe);
                }
            }}




        notifyDataSetChanged();
    }
    public void clearFilters()
    {
        recipes.clear();
        recipes.addAll(ogrecipes);
        notifyDataSetChanged();
    }
    public void fillLikedRecipes(ArrayList<String> liked)
    {
        recipes.clear();

        for (String title:liked)
        {
            for(Recipe recipe:ogrecipes)
            {
                if (recipe.title.equals(title))
                {
                    //recipes.add(recipe);
                }
            }
        }
      notifyDataSetChanged();
    }



    //Class that holds the items to be displayed (Views in card_layout)
    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView itemImage;
        TextView itemTitle;

        public ViewHolder(View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.item_image);
            itemTitle = itemView.findViewById(R.id.item_title);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    //card position

                    int position = getAdapterPosition();
                    Intent intent = new Intent(v.getContext(), View_Recipe_Activity.class);
                    //get binding adapter where all the cards are
                    RecyclerView.Adapter adapter = (RecyclerAdapter)getBindingAdapter();

                    Bundle data = new Bundle();

                    //FILL RECIPE DATA ON BUNDLE TO GIVE TO THE ACTIVITY
                    data.putString("Rec_title",((RecyclerAdapter) adapter).ogrecipes.get(position).title);
                    data.putString("Recipe_execution",((RecyclerAdapter) adapter).ogrecipes.get(position).getExecution());
                    Bitmap bitmap_preloaded = Create_Activity.loadBitmapFromInternalStorage(((RecyclerAdapter) adapter).context, "recipe_"+String.valueOf(((RecyclerAdapter) adapter).post_id.get(position)+9)+".jpg");
                    if (((RecyclerAdapter) adapter).post_id.get(position)<-3)//position < ((RecyclerAdapter) adapter).preloaded)
                    {

                        data.putInt("Recipe_pic",((RecyclerAdapter) adapter).recipeDrawables[((RecyclerAdapter) adapter).post_id.get(position)+9]);

                    }else
                    {
                        String test =  String.valueOf(((RecyclerAdapter) adapter).post_id.get(position));
                        Log.d("post id we got",test);
                        Bitmap bitmap = Create_Activity.loadBitmapFromInternalStorage(((RecyclerAdapter) adapter).context, String.valueOf(((RecyclerAdapter) adapter).post_id.get(position)));
                        if (bitmap!=null)
                        {
                          data.putBoolean("customPic",true);

                          data.putInt("Recipe_pic",((RecyclerAdapter) adapter).post_id.get(position));

                        }else {
                            data.putInt("Recipe_pic",R.drawable.recipe_image);

                        }


                    }


                    data.putString("Recipe_ingredients",((RecyclerAdapter) adapter).ogrecipes.get(position).getIngredients());
                    data.putInt("likes",((RecyclerAdapter) adapter).ogrecipes.get(position).getLikes());
                    data.putInt("dislikes",((RecyclerAdapter) adapter).ogrecipes.get(position).getDislikes());
                    data.putInt("rec_id",((RecyclerAdapter) adapter).post_id.get(position));
                    data.putInt("creator_id",((RecyclerAdapter) adapter).ogrecipes.get(position).creator_id);
                    Log.d("CREATOR ID",String.valueOf(((RecyclerAdapter) adapter).ogrecipes.get(position).getCreator_id()));
                    Log.d("Position",String.valueOf(position));


                    intent.putExtras(data);
                    Log.d("DEBUG",data.getString("Rec_title"));


                    //start activity
                    v.getContext().startActivity(intent,data);


                    if( recyclerViewInterface!=null){
                        //int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                           // recyclerViewInterface.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
    //Methods that must be implemented for a RecyclerView.Adapter
    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_layout, parent, false);


        return new ViewHolder(v, recyclerViewInterface);
    }
    public Recipe getRecipefromPosition(int pos)
    {
        return this.ogrecipes.get(pos);
    }
    public void print_all_recipes_view()
    {
        Log.d("DEBUG","BEGIN");
        for (Recipe x : ogrecipes)
        {
            Log.d("Recipe",x.title);

        }
        Log.d("DEBUG","END");
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.itemTitle.setText(ogrecipes.get(position).title);
        /*if (position < recipeDrawables.length)
        {
            holder.itemImage.setImageResource(drawables_list.get(position));
        }else
        {
            holder.itemImage.setImageResource(R.drawable.recipe_image);
        }*/
        /*if (position==6)
        {
            Bitmap bitmap = Create_Activity.loadBitmapFromInternalStorage(this.context,"-3");
            return;
        }*/
        Bitmap bitmap = Create_Activity.loadBitmapFromInternalStorage(this.context,String.valueOf(post_id.get(position)));
        Log.d("Checking",String.valueOf(post_id.get(position)));
        if (bitmap!=null)
        {
            holder.itemImage.setImageBitmap(bitmap);
            Log.d("result","BITMAP FOUND");

        }else {
            Log.d("PROBLEM","GOT NULL BITMAP");
            holder.itemImage.setImageResource(drawables_list.get(position));
        }
        Log.d("end","-------");



        //MIN PEIRAKSEI KANEIS TO PANO, EIMAI POLI EKSIPNOS POU TO SKEFTIKA
    }
    @Override
    public int getItemCount() {
        return ogrecipes.size();
        //return titles.length;
    }
    public void fillTitles(int limit)
    {
        DatabaseHandler myhandler = new DatabaseHandler(this.context,1);
        Cursor cursor = myhandler.getRecipes(-1);
        ArrayList<String> titleList = new ArrayList<>();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String title = cursor.getString(1);
                titleList.add(title);
            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }

    }
}