package com.example.android.bakingapp.ui.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.data.models.Ingredient;

import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.ViewHolder> {

    private List<Ingredient> mIngredient;
    @NonNull
    @Override
    public IngredientAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_details_item_sample,parent,false);
        return new ViewHolder(view);
    }

    public IngredientAdapter(List<Ingredient> ingredient) {
        mIngredient = ingredient;
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientAdapter.ViewHolder holder, int position) {
        Ingredient ing = mIngredient.get(position);
        String measure = ing.getMeasure();
        Double quantity = ing.getQuantity();
        String ingredient = ing.getIngredient();
        holder.quantity.setText(String.valueOf(quantity.intValue()));
        holder.ingredient.setText(": "+ingredient);
        holder.measure.setText(measure);

    }
    public void addIngredients(List<Ingredient> ingredient){
        mIngredient = ingredient;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mIngredient == null) return 0;
        return mIngredient.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView quantity;
        TextView measure;
        TextView ingredient;
        public ViewHolder(View itemView) {
            super(itemView);
            quantity = itemView.findViewById(R.id.text_quantity);
            measure = itemView.findViewById(R.id.text_measure);
            ingredient = itemView.findViewById(R.id.text_ingredient);
        }
    }
}
