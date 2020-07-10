package com.example.examemobile.adapter;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.examemobile.R;
import com.example.examemobile.Recipe;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.MyViewHolder> {
    private List<Recipe> recipes;
    private OnItemClickListener listener;

    public RecipeAdapter(List<Recipe> recipes, OnItemClickListener listener) {
        this.recipes = recipes;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemList = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipes_layout, parent, false);
        return new MyViewHolder(itemList);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeAdapter.MyViewHolder holder, int pos) {
        Recipe recipe = recipes.get(pos);
        holder.title_name.setText(recipe.getName());
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int pos);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
        public TextView title_name;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title_name = itemView.findViewById(R.id.recipe_names);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(getAdapterPosition());
        }

    }

}
