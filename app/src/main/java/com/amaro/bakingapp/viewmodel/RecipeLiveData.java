package com.amaro.bakingapp.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.amaro.bakingapp.model.Recipe;
import com.amaro.bakingapp.util.RetrofitConfig;

import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;

public class RecipeLiveData extends LiveData<List<Recipe>> {

    private final String TAG = "RecipeLiveData";

    public RecipeLiveData() {
        loadData();
    }

    private void loadData() {
        new RetrofitConfig().getRecipesService().getRecipes().enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(retrofit2.Call<List<Recipe>> call, Response<List<Recipe>> response) {
                setValue(response.body());
            }

            @Override
            public void onFailure(retrofit2.Call<List<Recipe>> call, Throwable t) {
                Log.d(TAG, "rerror "+t.getMessage());
                setValue(null);
            }
        });
    }
}
