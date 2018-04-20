package com.example.android.bakingapp.utils;

import com.example.android.bakingapp.data.models.Ingredient;

import java.util.List;

public class Util {
    public static String getIngredientFromList (List<Ingredient> list , int position){
        Ingredient ingredient = list.get(position);
        String finalIngredient= "";
        double quantity = ingredient.getQuantity();
        String cup = ingredient.getMeasure();
        String description = ingredient.getIngredient();
        finalIngredient = String.valueOf(quantity)+ " " + cup + " " + description;
        return  finalIngredient;
    }
}
