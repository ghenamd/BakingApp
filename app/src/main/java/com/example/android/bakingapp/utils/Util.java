package com.example.android.bakingapp.utils;

import com.example.android.bakingapp.data.models.Ingredient;

import java.util.List;

public class Util {
    public static String getIngredientFromList(List<Ingredient> list,int position) {
        String finalIngredient = "";
        Ingredient ing = list.get(position);
        double quantity = ing.getQuantity();
        String cup = ing.getMeasure();
        String description = ing.getIngredient();
        finalIngredient = String.valueOf(quantity) + " " + cup + " " + description;
        return finalIngredient;
    }
}
