package com.example.android.bakingapp.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.ui.fragments.RecipeDetailsFragment;

public class RecipeIntentService extends IntentService {

    public static final String ACTION_UPDATE_INGREDIENT= "com.example.android.bakingapp.widget.update";
    private static final String TAG = "RecipeIntentService";

    public RecipeIntentService() {
        super(TAG);
    }

    public static void startUpdateIngredientWidget(Context context){
        Intent intent = new Intent(context,RecipeIntentService.class);
        intent.setAction(ACTION_UPDATE_INGREDIENT);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if(intent != null){
            final String action = intent.getAction();
            if (ACTION_UPDATE_INGREDIENT.equals(action)){
                handleUpdateIngredientWidget();
            }
        }
    }

    private void handleUpdateIngredientWidget() {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this,RecipeWidgetProvider.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_list_view);
        RecipeWidgetProvider.updateIngredientWidget(this,appWidgetManager,appWidgetIds, RecipeDetailsFragment.mTitle);
        Log.v(TAG, "Message "+ RecipeDetailsFragment.mIngredients);
    }
}
