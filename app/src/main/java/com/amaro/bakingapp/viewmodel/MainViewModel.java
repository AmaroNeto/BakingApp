package com.amaro.bakingapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.amaro.bakingapp.model.Recipe;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private static final String TAG = "MainViewModel";
    private LiveData<List<Recipe>> recipes;

    public MainViewModel(@NonNull Application application) {
        super(application);
        recipes = new RecipeLiveData();
    }

    public LiveData<List<Recipe>> getRecipes() {
        return recipes;
    }
}
