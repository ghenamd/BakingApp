package com.example.android.bakingapp.data.db;

import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.android.bakingapp.data.models.Ingredient;
import com.example.android.bakingapp.data.models.Recipe;
import com.example.android.bakingapp.data.models.RecipeRoom;
import com.example.android.bakingapp.data.models.Step;

import java.util.List;

@android.arch.persistence.room.Dao
public interface RecipeDao {

    @Query("SELECT * FROM recipeRoom")
    List<RecipeRoom> getAllRecipes();

    @Query("SELECT * FROM recipeRoom WHERE  name LIKE :name")
    Recipe getRecipeByName(String name);

    @Insert()
    void insertRecipe(RecipeRoom... recipes);

    @Insert
    void insertIngredient(List<Ingredient> ingredients);

    @Insert
    void insertSteps(List<Step> steps);

    @Query("SELECT * FROM ingredient WHERE recipeId IS :id")
    List<Ingredient> getRecipeIngredients (int id);

    @Query("SELECT * FROM step WHERE recipeId IS :id")
    List<Step> getRecipeSteps(int id);

}
