package com.example.android.bakingapp.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.ui.fragments.StepDetailsFragment;

public class StepDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_details);
        setTitle(getString(R.string.steps));
        if (savedInstanceState == null){
            StepDetailsFragment fragment = new StepDetailsFragment();

            getFragmentManager().beginTransaction().add(R.id.step_details_container,fragment).commit();
        }

    }
}
