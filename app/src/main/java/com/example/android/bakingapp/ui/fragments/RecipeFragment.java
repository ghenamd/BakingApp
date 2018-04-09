package com.example.android.bakingapp.ui.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.data.models.Recipe;
import com.example.android.bakingapp.data.network.RestManager;
import com.example.android.bakingapp.ui.adapters.RecipeAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecipeAdapter mRecipeAdapter;
    private static final String TAG = "RecipeFragment";
    private Context mContext;
    private ArrayList<Recipe> mRecipes;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.recipe_fragment,container,false);
        mContext= getActivity().getBaseContext();
        mRecyclerView = rootView.findViewById(R.id.recipe_fragment_recyclerView);

        mRecipeAdapter = new RecipeAdapter();
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setHasFixedSize(true);

        RestManager restManager = new RestManager();
        Call <ArrayList<Recipe>> call = restManager.getRecipeClient().getRecipes();
        call.enqueue(new Callback<ArrayList<Recipe>>() {
            @Override
            public void onResponse(Call<ArrayList<Recipe>> call, Response<ArrayList<Recipe>> response) {
                mRecipes = response.body();
                mRecipeAdapter.addRecipe(mRecipes);
                Log.d(TAG, "onResponse: " + response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<Recipe>> call, Throwable t) {
                Log.d(TAG, "onFailure: " +String.valueOf(t));
            }
        });
        ;
        mRecyclerView.setAdapter(mRecipeAdapter);
        return rootView;

    }

}
