package com.amaro.bakingapp.widget;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.widget.RemoteViews;

import androidx.annotation.Nullable;

import com.amaro.bakingapp.R;
import com.amaro.bakingapp.model.Recipe;
import com.amaro.bakingapp.util.RetrofitConfig;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;

public class BakingWidgetService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new RetrofitConfig().getRecipesService().getRecipes().enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(retrofit2.Call<List<Recipe>> call, Response<List<Recipe>> response) {
                ArrayList<Recipe> recipes = new ArrayList<>(response.body());
                updateWidget(recipes);
            }

            @Override
            public void onFailure(retrofit2.Call<List<Recipe>> call, Throwable t) {
                ArrayList<Recipe> recipes = new ArrayList<>();
                updateWidget(recipes);
            }
        });

        return super.onStartCommand(intent, flags, startId);
    }

    private void updateWidget(ArrayList<Recipe> recipes) {

        RemoteViews views = new RemoteViews(
                getApplicationContext().getPackageName(),
                R.layout.collection_widget
        );

        Intent intent = new Intent(getApplicationContext(), BakingWidgetRemoteViewsService.class);
        //intent.putParcelableArrayListExtra(BakingWidgetRemoteViewsService.EXTRA, recipes);
        views.setRemoteAdapter(R.id.widgetListView, intent);

        ComponentName theWidget = new ComponentName(getApplicationContext(), BakingAppWidget.class);
        AppWidgetManager manager = AppWidgetManager.getInstance(getApplicationContext());
        manager.updateAppWidget(theWidget, views);
    }
}
