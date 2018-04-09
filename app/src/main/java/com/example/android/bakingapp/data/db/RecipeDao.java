package com.example.android.bakingapp.data.db;

import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.android.bakingapp.data.models.Recipe;
import com.example.android.bakingapp.data.models.RecipeRoom;

import java.util.List;

@android.arch.persistence.room.Dao
public interface RecipeDao {

    @Query("SELECT * FROM recipeRoom")
    List<RecipeRoom> getAllRecipes();

    @Query("SELECT * FROM recipeRoom WHERE  name LIKE :name")
    Recipe getRecipeByName(String name);

    @Insert()
    void insertRecipe(RecipeRoom... recipes);

}
