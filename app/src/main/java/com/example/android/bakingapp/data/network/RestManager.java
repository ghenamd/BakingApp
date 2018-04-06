package com.example.android.bakingapp.data.network;

import com.example.android.bakingapp.utils.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestManager {

    private RecipeClient mRecipeClient;

    public RecipeClient getRecipeClient(){
        if (mRecipeClient == null){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.RECIPE_API_REQUEST)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            mRecipeClient = retrofit.create(RecipeClient.class);
        }
        return  mRecipeClient;
    }
}
