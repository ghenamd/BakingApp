package com.example.android.bakingapp.ui.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.data.models.Recipe;
import com.example.android.bakingapp.data.network.RestManager;
import com.example.android.bakingapp.ui.adapters.RecipeAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecipeAdapter mRecipeAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.recipe_fragment,container,false);
        mRecyclerView = rootView.findViewById(R.id.recipe_fragment_recyclerView);
        mRecipeAdapter = new RecipeAdapter(new ArrayList<Recipe>());
        LinearLayoutManager manager = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            manager = new LinearLayoutManager(getContext());
        }
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setHasFixedSize(true);
        getRecipesFromApi();
        mRecyclerView.setAdapter(mRecipeAdapter);
        return rootView;

    }

    public void getRecipesFromApi(){
        RestManager restManager = new RestManager();
        Call<List<Recipe>> call = restManager.getRecipeClient().getRecipes();
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                List<Recipe> recipes = response.body();
                mRecipeAdapter.addRecipe(recipes);

            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {

            }
        });
    }
}
