package com.amaro.bakingapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.amaro.bakingapp.model.Ingredient;

import java.util.List;

public class IngredientViewModel extends AndroidViewModel {

    private List<Ingredient> mIngredients;

    public IngredientViewModel(@NonNull Application application, List<Ingredient> ingredients){
        super(application);
        mIngredients = ingredients;
    }


    public List<Ingredient> getIngredients() {
        return mIngredients;
    }
}
