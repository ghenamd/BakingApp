package com.example.android.bakingapp.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.idlingRes.TestDelayer;
import com.example.android.bakingapp.idlingRes.SimpleIdlingResource;

public class MainActivity extends AppCompatActivity implements TestDelayer.DelayerCallback   {
    @Nullable
    private SimpleIdlingResource mIdlingResource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getIdlingResource();
        TestDelayer.processTest( mIdlingResource);
    }

    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }


    @Override
    public void onDone() {
        Toast.makeText(getApplicationContext(), R.string.recipe_loaded_success,Toast.LENGTH_SHORT).show();

    }
}
