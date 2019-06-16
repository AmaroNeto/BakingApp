package com.amaro.bakingapp.widget;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.amaro.bakingapp.R;
import com.amaro.bakingapp.model.Recipe;
import com.amaro.bakingapp.util.RetrofitConfig;
import com.amaro.bakingapp.view.RecipeDetailActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;

public class BakingWidgetRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;
    private List<Recipe> mRecipes  = new ArrayList<Recipe>();

    public BakingWidgetRemoteViewsFactory(Context applicationContext, Intent intent) {
        mContext = applicationContext;
    }

    @Override
    public void onCreate() {
        initializeData();
    }

    @Override
    public void onDataSetChanged() {
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return mRecipes.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if (position == AdapterView.INVALID_POSITION ||
                mRecipes == null) {
            return null;
        }

        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.collection_widget_list_item);
        rv.setTextViewText(R.id.widge_title, mRecipes.get(position).getName());

        Intent fillInIntent = new Intent();
        fillInIntent.putExtra(RecipeDetailActivity.EXTRA_DATA, mRecipes.get(position));
        rv.setOnClickFillInIntent(R.id.widgetItemContainer, fillInIntent);

        return rv;
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
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    private void initializeData() {
        new RetrofitConfig().getRecipesService().getRecipes().enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(retrofit2.Call<List<Recipe>> call, Response<List<Recipe>> response) {
                mRecipes  = response.body();

                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(mContext);
                int appWidgetIds[] = appWidgetManager.getAppWidgetIds(
                        new ComponentName(mContext, BakingAppWidget.class));
                appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widgetListView);

            }

            @Override
            public void onFailure(retrofit2.Call<List<Recipe>> call, Throwable t) {
            }
        });
    }

    private void notifyWidget() {

    }

}
