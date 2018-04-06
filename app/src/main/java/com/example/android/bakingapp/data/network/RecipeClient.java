package com.example.android.bakingapp.data.network;

import com.example.android.bakingapp.data.models.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RecipeClient {
    @GET("baking.json")
    Call <List<Recipe>> getRecipes();
}
