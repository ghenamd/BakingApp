package com.example.android.bakingapp.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.data.models.Ingredient;

import java.util.List;

public class RecipeRemoteViewService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RecipeRemoteViewsFactory(this.getApplicationContext(),intent);
    }

    public class RecipeRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
        private List<Ingredient> mIngredientList;
        private Context mContext ;
        private int appWidgetId;
        public RecipeRemoteViewsFactory(Context context, Intent intent) {
            mContext = context;
            appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        @Override
        public void onCreate() {

        }

        @Override
        public void onDataSetChanged() {
            if (mIngredientList == null){
                mIngredientList = RecipeWidgetProvider.mIngredientList;
            }

        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            if (mIngredientList == null)return 0;
            return mIngredientList.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {

            RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.app_widget_list_item);
            views.setTextViewText(R.id.widget_ingredient_name, (CharSequence) mIngredientList.get(position));
            Bundle extras = new Bundle();
            extras.putInt(AppWidgetManager.EXTRA_APPWIDGET_ID,appWidgetId);
            Intent i = new Intent();
            i.putExtra("This",extras);
            views.setOnClickFillInIntent(R.id.widget_title,i);
            return views;

        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}
