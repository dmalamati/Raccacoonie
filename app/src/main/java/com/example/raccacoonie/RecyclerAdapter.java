package com.example.raccacoonie;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private final RecyclerViewInterface recyclerViewInterface;


    //Variables storing data to display for this example
    private final String[] titles = {"Fasolakia", "Fakes", "Pastitsio", "Keftedakia", "Makaronia me kima", "Spanakorizo", "Omeleta", "Kokkinisto me kritharaki"};

    private final int[] images = { R.drawable.recipe_image, R.drawable.recipe_image, R.drawable.recipe_image, R.drawable.recipe_image, R.drawable.recipe_image,
            R.drawable.recipe_image, R.drawable.recipe_image, R.drawable.recipe_image };

    public RecyclerAdapter(RecyclerViewInterface recyclerViewInterface) {
        this.recyclerViewInterface = recyclerViewInterface;
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
//                    int position = getAdapterPosition();
//                    Snackbar.make(v, "Click detected on item " + position,
//                            Snackbar.LENGTH_LONG).show();
                    if( recyclerViewInterface!=null){
                        int position = getAdapterPosition();
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
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_layout, parent, false);
        return new ViewHolder(v, recyclerViewInterface);
    }
    @Override
    public void onBindViewHolder(RecyclerAdapter.ViewHolder holder, int position) {
        holder.itemTitle.setText(titles[position]);
        holder.itemImage.setImageResource(images[position]);
    }
    @Override
    public int getItemCount() {
        return titles.length;
    }
}
