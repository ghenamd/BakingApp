package com.example.android.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.data.models.Ingredient;
import com.example.android.bakingapp.data.models.Recipe;
import com.example.android.bakingapp.ui.RecipeDetailsActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidgetProvider extends AppWidgetProvider{
    private static final String TAG = "RecipeWidgetProvider";
 static List<Ingredient> mIngredientList = new ArrayList<>();
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, Recipe recipe) {
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.app_widget_provider);
        Intent startAppFromWidget = new Intent(context, RecipeDetailsActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,
                startAppFromWidget,PendingIntent.FLAG_UPDATE_CURRENT);
        Intent intent = new Intent(context, RecipeRemoteViewService.class);
        intent.addCategory(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        views.setRemoteAdapter(R.id.appwidget_listView,intent);
        mIngredientList = recipe.getIngredients();
        Log.d(TAG, String.valueOf(mIngredientList));
        String title = recipe.getName();
        views.setTextViewText(R.id.widget_title,title);
        views.setOnClickPendingIntent(R.id.widget_title, pendingIntent);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them

    }
    public static void updateIngredientWidget (Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds, Recipe recipe) {
        // There may be multiple widgets active, so update all of them

        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, recipe);
        }

    }
    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

}

