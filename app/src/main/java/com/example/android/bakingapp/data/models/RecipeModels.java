package com.example.android.bakingapp.data.models;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;

import java.util.ArrayList;
import java.util.List;

public class RecipeModels {
    @Embedded
    private Recipe mRecipe;
    @Relation(parentColumn = "id",
            entityColumn = "recipeId", entity = Step.class)
    private List<Step> mSteps = new ArrayList<>();
    @Relation(parentColumn = "id",
            entityColumn = "recipeId", entity = Ingredient.class)
    private List<Ingredient> mIngredients = new ArrayList<>();

    public Recipe getRecipe() {
        return mRecipe;
    }

    public void setRecipe(Recipe recipe) {
        mRecipe = recipe;
    }

    public List<Step> getSteps() {
        return mSteps;
    }

    public void setSteps(List<Step> steps) {
        mSteps = steps;
    }

    public List<Ingredient> getIngredients() {
        return mIngredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        mIngredients = ingredients;
    }
}
