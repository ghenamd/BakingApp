package com.example.android.bakingapp.data.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.android.bakingapp.data.models.Ingredient;
import com.example.android.bakingapp.data.models.RecipeRoom;
import com.example.android.bakingapp.data.models.Step;

@Database(entities = {RecipeRoom.class, Ingredient.class, Step.class},version = 1)
public abstract class RecipeDatabase extends RoomDatabase {
    public abstract RecipeDao getRecipeDao();

    /* Create a singleton*/
    private static final String DATABASE_NAME = "recipeRoom";

    private static final Object LOCK = new Object();

    private static volatile RecipeDatabase sInstance;

    public static RecipeDatabase getInstance(Context context) {

        if (sInstance == null) {
            synchronized (LOCK) {
                if (sInstance == null) {
                    sInstance = Room.databaseBuilder(context.getApplicationContext(),
                            RecipeDatabase.class, RecipeDatabase.DATABASE_NAME).build();
                }
            }
        }
        return sInstance;

    }

}
