package com.example.android.bakingapp.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.data.models.Recipe;

public class RecipeIntentService extends IntentService {

    public static final String ACTION_UPDATE_INGREDIENT= "com.example.android.bakingapp.widget.update";
    public static final String RECIPE_SENT ="recipe_sent";
    private static final String TAG = "RecipeIntentService";
    public static Recipe mRecipe;
    public RecipeIntentService() {
        super(TAG);
    }
    public static void startUpdateIngredientWidget(Context context,Recipe recipe){
        Intent intent = new Intent(context,RecipeIntentService.class);
        intent.setAction(ACTION_UPDATE_INGREDIENT);
        intent.putExtra(RECIPE_SENT,recipe);
        context.startService(intent);

    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if(intent != null){
            final String action = intent.getAction();
            if (ACTION_UPDATE_INGREDIENT.equals(action)){
                mRecipe = intent.getParcelableExtra(RECIPE_SENT);
                Log.d(TAG, "onHandleIntent: " + String.valueOf(mRecipe));
                handleUpdateIngredientWidget(mRecipe);
            }
        }
    }

    private void handleUpdateIngredientWidget(Recipe recipe) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetsId = appWidgetManager.getAppWidgetIds(new ComponentName(this,RecipeWidgetProvider.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetsId, R.id.appwidget_listView);
        RecipeWidgetProvider.updateIngredientWidget(this,appWidgetManager,appWidgetsId,recipe);
    }
}
