package com.example.android.bakingapp.ui.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.data.models.Recipe;
import com.example.android.bakingapp.data.network.RestManager;
import com.example.android.bakingapp.databinding.RecipeFragmentBinding;
import com.example.android.bakingapp.ui.RecipeDetailsActivity;
import com.example.android.bakingapp.ui.adapters.RecipeAdapter;
import com.example.android.bakingapp.utils.Constants;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeFragment extends Fragment implements RecipeAdapter.OnRecipeClicked {
    private RecipeAdapter mRecipeAdapter;
    private static final String TAG = "RecipeFragment";
    private Context mContext;
    private ArrayList<Recipe> mRecipes;
    private RecipeFragmentBinding mFragmentRecipeBinding;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        mFragmentRecipeBinding= DataBindingUtil.inflate(inflater,R.layout.recipe_fragment,container,false);
        View view = mFragmentRecipeBinding.getRoot();
        mContext= getActivity().getBaseContext();
        mRecipeAdapter = new RecipeAdapter(new ArrayList<Recipe>(),this);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        mFragmentRecipeBinding.recipeFragmentRecyclerView.setLayoutManager(manager);
        mFragmentRecipeBinding.recipeFragmentRecyclerView.setHasFixedSize(true);

        RestManager restManager = new RestManager();
        Call <ArrayList<Recipe>> call = restManager.getRecipeClient().getRecipes();
        call.enqueue(new Callback<ArrayList<Recipe>>() {
            @Override
            public void onResponse(Call<ArrayList<Recipe>> call, Response<ArrayList<Recipe>> response) {
                mRecipes = response.body();
                mRecipeAdapter.addRecipe(mRecipes);
            }

            @Override
            public void onFailure(Call<ArrayList<Recipe>> call, Throwable t) {
                Log.d(TAG, "onFailure: " +String.valueOf(t));
            }
        });
        ;
        mFragmentRecipeBinding.recipeFragmentRecyclerView.setAdapter(mRecipeAdapter);
        return view;

    }
    @Override
    public void onClickedItem(Recipe recipe) {
        Intent intent = new Intent(getActivity(),RecipeDetailsActivity.class);
        intent.putExtra(Constants.PARCEL_RECIPE,recipe);
        startActivity(intent);
    }

}
