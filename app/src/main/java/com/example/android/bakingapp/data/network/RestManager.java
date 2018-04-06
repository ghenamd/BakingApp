package com.example.android.bakingapp.data.network;

import com.example.android.bakingapp.utils.Constants;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestManager {

    private RecipeClient mRecipeClient;

    public RecipeClient getRecipeClient(){

        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        if (mRecipeClient == null){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.RECIPE_API_REQUEST)
                    .addConverterFactory(GsonConverterFactory.create())
                    .callFactory(httpClientBuilder.build())
                    .build();
            mRecipeClient = retrofit.create(RecipeClient.class);
        }
        return  mRecipeClient;
    }
}
