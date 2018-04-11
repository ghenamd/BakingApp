package com.example.android.bakingapp.ui.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.data.models.Recipe;

import java.util.ArrayList;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {

    private ArrayList<Recipe> mRecipeList;
    private OnRecipeClicked mRecipeClicked;

    @NonNull
    @Override
    public RecipeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_item_sample, parent, false);
        return new RecipeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeAdapter.ViewHolder holder, int position) {
        Recipe recipe = mRecipeList.get(position);
        String title = recipe.getName();
        holder.mTitle.setText(title);
    }

    public RecipeAdapter(ArrayList<Recipe> recipeList, OnRecipeClicked recipeClicked) {
        mRecipeList = recipeList;
        mRecipeClicked = recipeClicked;
    }

    public interface OnRecipeClicked {
        void onClickedItem(Recipe recipe);
    }

    @Override
    public int getItemCount() {
        if (null == mRecipeList) return 0;
        return mRecipeList.size();
    }

    public void addRecipe(ArrayList<Recipe> list) {
        mRecipeList = list;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView mTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.recipe_title);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Recipe recipe = mRecipeList.get(position);
            mRecipeClicked.onClickedItem(recipe);

        }
    }

}
