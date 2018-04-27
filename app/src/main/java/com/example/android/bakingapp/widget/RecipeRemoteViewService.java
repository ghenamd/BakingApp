package com.example.android.bakingapp.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.data.models.Ingredient;
import com.example.android.bakingapp.utils.Util;

import java.util.ArrayList;
import java.util.List;

import static com.example.android.bakingapp.ui.fragments.RecipeDetailsFragment.recipe;


public class RecipeRemoteViewService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RecipeRemoteViewsFactory(this.getApplicationContext());
    }

    public class RecipeRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
        private List<Ingredient> mIngredientList = new ArrayList<>();
        private Context mContext;
        private static final String TAG = "RecipeRemoteViewsFactor";

        public RecipeRemoteViewsFactory(Context context) {
            mContext = context;
        }

        @Override
        public void onCreate() {

        }

        @Override
        public void onDataSetChanged() {
            mIngredientList = recipe.getIngredients();
        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            if (mIngredientList == null) return 0;
            return mIngredientList.size();

        }

        @Override
        public RemoteViews getViewAt(int position) {

            RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.app_widget_list_item);
            views.setTextViewText(R.id.widget_ingredient_name, Util.getIngredientFromList(mIngredientList,position));
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
