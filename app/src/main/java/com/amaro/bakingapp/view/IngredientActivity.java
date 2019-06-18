package com.amaro.bakingapp.view;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.amaro.bakingapp.R;
import com.amaro.bakingapp.model.Recipe;
import com.amaro.bakingapp.view.fragment.IngredientFragment;

public class IngredientActivity extends AppCompatActivity {

    public final static String EXTRA_DATA = "extra_data";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ingredient_activity);

        Recipe recipe = getIntent().getParcelableExtra(EXTRA_DATA);
        ActionBar actionBar = getSupportActionBar();

        if(recipe != null) {
            IngredientFragment ingredientFragment = IngredientFragment.newInstance(recipe.getIngredients());
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.frame_ingredients, ingredientFragment)
                    .commit();

            if(actionBar != null) {
                actionBar.setTitle(recipe.getName());
            }
        }



    }
}
