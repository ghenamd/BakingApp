package com.example.android.bakingapp.data.network;

import com.example.android.bakingapp.utils.Constants;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestManager {

    private RecipeClient mRecipeClient;

    public RecipeClient getRecipeClient(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        if (mRecipeClient == null){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.RECIPE_API_REQUEST)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
            mRecipeClient = retrofit.create(RecipeClient.class);
        }
        return  mRecipeClient;
    }
}
