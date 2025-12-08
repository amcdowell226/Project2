package com.example.project2.viewHolders;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.project2.database.entities.RecipeLog;

public class RecipeLogAdapter extends ListAdapter<RecipeLog, RecipeLogViewHolder>  {
    public RecipeLogAdapter(@NonNull DiffUtil.ItemCallback<RecipeLog> diffCallback) {
        super(diffCallback);
    }

    @Override
    public RecipeLogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return RecipeLogViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeLogViewHolder holder, int position) {
        RecipeLog current = getItem(position);
        holder.bind(current.toString());
    }

    public static class RecipeLogDiff extends DiffUtil.ItemCallback<RecipeLog> {
        @Override
        public boolean areItemsTheSame(@NonNull RecipeLog oldItem, @NonNull RecipeLog newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull RecipeLog oldItem, @NonNull RecipeLog newItem) {
            return oldItem.equals(newItem);
        }
    }
}
