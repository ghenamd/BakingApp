package com.example.android.bakingapp.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.data.models.Recipe;
import com.example.android.bakingapp.ui.fragments.StepDetailsFragment;
import com.example.android.bakingapp.utils.Constants;

public class RecipeDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        Recipe recipe = getIntent().getParcelableExtra(Constants.PARCEL_RECIPE);
        String title = recipe.getName();
        setTitle(title);
        boolean isTablet = getResources().getBoolean(R.bool.isTablet);
        if (isTablet && savedInstanceState == null){
            StepDetailsFragment fragment = new StepDetailsFragment();
            getFragmentManager().beginTransaction().add(R.id.step_details_container,fragment).commit();
        }
    }
}
