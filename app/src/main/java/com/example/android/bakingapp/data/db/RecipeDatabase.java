package com.example.android.bakingapp.data.db;

import android.arch.persistence.room.RoomDatabase;

public abstract class RecipeDatabase extends RoomDatabase {
    public abstract RecipeDao getRecipeDao();
}
