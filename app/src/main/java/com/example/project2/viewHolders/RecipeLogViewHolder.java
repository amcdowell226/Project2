package com.example.project2.viewHolders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project2.R;

public class RecipeLogViewHolder extends RecyclerView.ViewHolder {
    private final TextView recipeLogViewItem;
    private RecipeLogViewHolder(View recipeLogView) {
        super(recipeLogView);
        recipeLogViewItem = recipeLogView.findViewById(R.id.recyclerItemTextView);
    }

    public void bind (String text) {
        recipeLogViewItem.setText(text);
    }

    static RecipeLogViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipelog_recycler_item, parent, false);
        return new RecipeLogViewHolder(view);
    }
}
