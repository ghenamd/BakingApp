package com.example.android.bakingapp.data.db;

import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.android.bakingapp.data.models.Recipe;

import java.util.List;

@android.arch.persistence.room.Dao
public interface RecipeDao {

    @Query("SELECT * FROM recipe")
    List<Recipe> getAllRecipes();

    @Query("SELECT * FROM recipe WHERE  name LIKE :name")
    Recipe getRecipeByName(String name);

    @Insert()
    void insertRecipe(Recipe... recipes);

}
