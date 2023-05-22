package com.example.raccacoonie;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private final RecyclerViewInterface recyclerViewInterface;


    //Variables storing data to display for this example
    private final String[] titles = {"Fasolakia", "Fakes", "Pastitsio", "Keftedakia", "Makaronia me kima", "Spanakorizo", "Omeleta", "Kokkinisto me kritharaki"};
    private Context context;
    private final int[] images = { R.drawable.recipe_0, R.drawable.recipe_image, R.drawable.recipe_image, R.drawable.recipe_image, R.drawable.recipe_image,
            R.drawable.recipe_image, R.drawable.recipe_image, R.drawable.recipe_image };

    private final int[] recipeDrawables = {
            R.drawable.recipe_0,
            R.drawable.recipe_1,
            R.drawable.recipe_2,
            R.drawable.recipe_3,
            R.drawable.recipe_4,
            R.drawable.recipe_5
    };
    ArrayList<Recipe> recipes = new ArrayList<>();
    ArrayList<Integer> pics = new ArrayList<>();

    public RecyclerAdapter(RecyclerViewInterface recyclerViewInterface,Context context) {
        this.recyclerViewInterface = recyclerViewInterface;
        this.context = context;
        fillPics();
        fillRecipes();
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
    public void fillRecipes()
    {
        recipes.add(new Recipe(1, "Classic Chocolate Chip Cookies",
                "chocolate_chip_cookies.jpg",
                "1. Preheat the oven to 350°F. \n2. In a mixing bowl, cream together butter and sugars. \n3. Beat in eggs and vanilla. \n4. In a separate bowl, combine flour, baking soda, and salt. \n5. Gradually add the dry ingredients to the wet ingredients, mixing well. \n6. Stir in chocolate chips. \n7. Drop rounded tablespoons of dough onto a baking sheet. \n8. Bake for 10-12 minutes or until golden brown. \n9. Allow cookies to cool on the baking sheet for a few minutes before transferring to a wire rack.",
                "Butter, granulated sugar, brown sugar, eggs, vanilla extract, all-purpose flour, baking soda, salt, chocolate chips",
                "Desserts", 0));
        recipes.add(new Recipe(2, "Chicken Parmesan",
                "chicken_parmesan.jpg",
                "1. Preheat the oven to 375°F. \n2. Season chicken cutlets with salt, pepper, and Italian seasoning. \n3. Dip each cutlet in beaten egg, then coat with breadcrumbs. \n4. Heat oil in a skillet and cook the cutlets until golden brown. \n5. Place cooked cutlets in a baking dish and top with marinara sauce and shredded mozzarella cheese. \n6. Bake for 15-20 minutes or until the cheese is melted and bubbly. \n7. Serve with cooked spaghetti or your choice of pasta.",
                "Chicken cutlets, salt, pepper, Italian seasoning, eggs, breadcrumbs, oil, marinara sauce, shredded mozzarella cheese, spaghetti",
                "Main Dishes", 1));
        recipes.add(new Recipe(3, "Caprese Salad",
                "caprese_salad.jpg",
                "1. Slice tomatoes and fresh mozzarella cheese. \n2. Arrange the tomato and mozzarella slices on a serving platter. \n3. Drizzle with olive oil and balsamic glaze. \n4. Sprinkle with salt, pepper, and fresh basil leaves. \n5. Serve immediately as a refreshing appetizer or side dish.",
                "Tomatoes, fresh mozzarella cheese, olive oil, balsamic glaze, salt, pepper, fresh basil leaves",
                "Salads", 0));
        recipes.add(new Recipe(4, "Vegetable Stir-Fry",
                "vegetable_stirfry.jpg",
                "1. Heat oil in a wok or skillet over high heat. \n2. Add sliced vegetables (e.g., bell peppers, carrots, broccoli, snap peas). \n3. Stir-fry for a few minutes until vegetables are crisp-tender. \n4. In a small bowl, mix together soy sauce, ginger, and garlic. \n5. Pour the sauce over the vegetables and stir-fry for another minute. \n6. Serve hot over steamed rice or noodles.",
                "Assorted vegetables (bell peppers, carrots, broccoli, snap peas), oil, soy sauce, ginger, garlic, steamed rice or noodles",
                "Asian", 1));
        recipes.add(new Recipe(1, "Avocado Toast",
                "avocado_toast.jpg",
                "1. Toast a slice of bread until golden brown. \n2. Mash half an avocado and spread it on the toast. \n3. Sprinkle with salt, pepper, and red pepper flakes to taste. \n4. Optionally, top with sliced tomatoes, a drizzle of olive oil, or a squeeze of lemon juice. \n5. Enjoy as a quick and healthy breakfast or snack.",
                "Bread, avocado, salt, pepper, red pepper flakes",
                "Breakfast", 1));
        recipes.add(new Recipe(4, "Peanut Butter Banana Smoothie",
                "peanut_butter_smoothie.jpg",
                "1. In a blender, combine a ripe banana, a spoonful of peanut butter, and a cup of milk. \n2. Add a drizzle of honey and a pinch of cinnamon. \n3. Blend until smooth and creamy. \n4. Pour into a glass and enjoy as a delicious and energizing smoothie.",
                "Banana, peanut butter, milk, honey, cinnamon",
                "Beverages", 0));
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

                    int position = getAdapterPosition();
                    Log.d("DEBUG",String.format("CLICKED CARD %s",position));
                    // Snackbar.make(v, "Click detected on item " + position,
                        //    Snackbar.LENGTH_LONG).show();
                    if( recyclerViewInterface!=null){
                        //int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemClick(position);
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
        //fillTitles(2);

        //fill the phone with data
        //fillPics();
        //fillRecipes();

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_layout, parent, false);

        return new ViewHolder(v, recyclerViewInterface);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.itemTitle.setText(recipes.get(position).title);

        holder.itemImage.setImageResource(recipeDrawables[position]);
        //MIN PEIRAKSEI KANEIS TO PANO, EIMAI POLI EKSIPNOS POU TO SKEFTIKA
    }
    @Override
    public int getItemCount() {
        return recipes.size();
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

        for (int i = 0 ; i < titleList.size(); i++)
        {
            Log.d("TEST",titleList.get(i));
        }
    }
}
