package com.example.android.bakingapp.ui.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
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
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeFragment extends Fragment implements RecipeAdapter.OnRecipeClicked {
    private RecipeAdapter mRecipeAdapter;
    private static final String TAG = "RecipeFragment";
    private static final String LIST_OF_SAVED_RECIPES = "list_of_saved_recipes";
    private ArrayList<Recipe> mRecipes;
    private RecipeFragmentBinding mFragmentRecipeBinding;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        boolean isTablet = getActivity().getResources().getBoolean(R.bool.isTablet);
        mFragmentRecipeBinding= DataBindingUtil.inflate(inflater,R.layout.recipe_fragment,container,false);

        Context context = getActivity().getBaseContext();
        mRecipeAdapter = new RecipeAdapter(new ArrayList<Recipe>(),this,getActivity().getBaseContext());
        LinearLayoutManager manager = new LinearLayoutManager(context);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context,2);
        if (isTablet){
            mFragmentRecipeBinding.recipeFragmentRecyclerView.setLayoutManager(gridLayoutManager);
        }else{
            mFragmentRecipeBinding.recipeFragmentRecyclerView.setLayoutManager(manager);
        }
        if (!isConnected()){
            mFragmentRecipeBinding.status.setText(R.string.no_internet_connection);
            mFragmentRecipeBinding.status.setVisibility(View.VISIBLE);
            mFragmentRecipeBinding.progressBar.setVisibility(View.INVISIBLE);
        }
        mFragmentRecipeBinding.recipeFragmentRecyclerView.setHasFixedSize(true);
        mFragmentRecipeBinding.recipeFragmentRecyclerView.setNestedScrollingEnabled(false);
        RestManager restManager = new RestManager();
        Call <ArrayList<Recipe>> call = restManager.getRecipeClient().getRecipes();
        call.enqueue(new Callback<ArrayList<Recipe>>() {
            @Override
            public void onResponse(Call<ArrayList<Recipe>> call, Response<ArrayList<Recipe>> response) {
                mRecipes = response.body();
                mRecipeAdapter.addRecipe(mRecipes);
                mFragmentRecipeBinding.progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<ArrayList<Recipe>> call, Throwable t) {
            }
        });

        mFragmentRecipeBinding.recipeFragmentRecyclerView.setAdapter(mRecipeAdapter);
        View view = mFragmentRecipeBinding.getRoot();
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null){
            mRecipes = savedInstanceState.getParcelableArrayList(LIST_OF_SAVED_RECIPES);
            mRecipeAdapter.addRecipe(mRecipes);
        }
    }

    @Override
    public void onClickedItem(Recipe recipe) {
        Intent intent = new Intent(getActivity(),RecipeDetailsActivity.class);
        intent.putExtra(Constants.PARCEL_RECIPE,recipe);
        startActivity(intent);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(LIST_OF_SAVED_RECIPES,mRecipes);
    }
    private boolean isConnected() {
        ConnectivityManager manager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = Objects.requireNonNull(manager).getActiveNetworkInfo();
        return info != null && info.isConnectedOrConnecting();
    }
}
