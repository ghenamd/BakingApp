package com.example.android.bakingapp.ui.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.data.models.Ingredient;
import com.example.android.bakingapp.data.models.Recipe;
import com.example.android.bakingapp.data.models.Step;
import com.example.android.bakingapp.databinding.RecipeDetailsFragmentBinding;
import com.example.android.bakingapp.ui.StepDetailActivity;
import com.example.android.bakingapp.ui.adapters.IngredientAdapter;
import com.example.android.bakingapp.ui.adapters.StepAdapter;
import com.example.android.bakingapp.utils.Constants;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RecipeDetailsFragment extends Fragment implements StepAdapter.OnStepClicked {
    private RecipeDetailsFragmentBinding mBinding;
    private IngredientAdapter mAdapter;
    private StepAdapter mStepAdapter;
    private static final String TAG = "RecipeDetailsFragment";
    private List<Step> steps;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater,R.layout.recipe_details_fragment,container,false);
        View view = mBinding.getRoot();
        mAdapter = new IngredientAdapter(new ArrayList<Ingredient>());
        mStepAdapter = new StepAdapter(new ArrayList<Step>(),this);
        Recipe recipe = getActivity().getIntent().getParcelableExtra(Constants.PARCEL_RECIPE);

        List<Ingredient> ingredients = recipe.getIngredients();
        steps = recipe.getSteps();
        mAdapter.addIngredients(ingredients);
        mStepAdapter.addSteps(steps);
        mBinding.ingredientRecyclerView.setHasFixedSize(true);
        mBinding.ingredientRecyclerView.setNestedScrollingEnabled(false);
        mBinding.stepsRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getBaseContext());
        LinearLayoutManager layoutManagerSteps = new LinearLayoutManager(getActivity().getBaseContext());
        mBinding.ingredientRecyclerView.setLayoutManager(layoutManager);
        mBinding.ingredientRecyclerView.setAdapter(mAdapter);
        mBinding.stepsRecyclerView.setLayoutManager(layoutManagerSteps);
        mBinding.stepsRecyclerView.setAdapter(mStepAdapter);
        return view;
    }

    @Override
    public void onClicked(Step step) {
        Intent intent = new Intent(getActivity(), StepDetailActivity.class);
        intent.putExtra(Constants.PARCEL_STEP,step);
        intent.putExtra("Steps", (Serializable) steps);
        startActivity(intent);

    }
}
