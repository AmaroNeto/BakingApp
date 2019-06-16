package com.amaro.bakingapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import com.amaro.bakingapp.model.Ingredient;

import java.util.List;

public class IngredientViewModelFactory implements ViewModelProvider.Factory {

    private final Application application;
    private final List<Ingredient> ingredients;

    public IngredientViewModelFactory(Application app, List<Ingredient> ingredientList) {
        application = app;
        ingredients = ingredientList;
    }

    @NonNull
    @Override
    public IngredientViewModel create(@NonNull Class modelClass) {
        return new IngredientViewModel(application,ingredients);
    }
}
