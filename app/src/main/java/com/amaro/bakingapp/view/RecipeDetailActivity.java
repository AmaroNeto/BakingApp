package com.amaro.bakingapp.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.amaro.bakingapp.R;
import com.amaro.bakingapp.model.Recipe;
import com.amaro.bakingapp.model.Step;
import com.amaro.bakingapp.view.adapter.StepAdapter;
import com.amaro.bakingapp.view.fragment.IngredientFragment;
import com.amaro.bakingapp.view.fragment.StepDetailFragment;
import com.amaro.bakingapp.view.fragment.StepFragment;

import java.util.ArrayList;


public class RecipeDetailActivity extends AppCompatActivity implements StepAdapter.ListItemClickListener {

    private static final String TAG = "RecipeDetailActivity";
    public static final String EXTRA_DATA = "recipe_extra";
    private FragmentManager fragmentManager;
    private Recipe mRecipe;

    private boolean mTwoPane;
    private int position;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_detail_activity);
        mRecipe = getIntent().getParcelableExtra(EXTRA_DATA);

        fragmentManager = getSupportFragmentManager();

        if(getResources().getBoolean(R.bool.is_sw600)) {
            mTwoPane = true;

            if(savedInstanceState == null && mRecipe != null) {
                configSideBarFragments();
                configDetailFragments();
            }

        } else {
            mTwoPane = false;
            if(mRecipe != null && savedInstanceState == null) {
                configSideBarFragments();
            }
        }
    }

    private void configDetailFragments(){
        StepDetailFragment stepDetailFragment = StepDetailFragment.newInstance(mRecipe.getSteps().get(position));
        fragmentManager.beginTransaction()
                .add(R.id.step_detail_fragment, stepDetailFragment)
                .commit();
    }

    private void configSideBarFragments(){
        IngredientFragment ingredientFragment = IngredientFragment.newInstance(mRecipe.getIngredients());

        fragmentManager.beginTransaction()
                .add(R.id.ingredient_list_fragment, ingredientFragment)
                .commit();


        StepFragment stepFragment = StepFragment.newInstance(mRecipe.getSteps());

        fragmentManager.beginTransaction()
                .add(R.id.step_list_fragment, stepFragment)
                .commit();
    }

    @Override
    public void onListItemClick(int position) {
        if(mTwoPane) {
            this.position = position;
            StepDetailFragment stepDetailFragment = StepDetailFragment.newInstance(mRecipe.getSteps().get(position));
            fragmentManager.beginTransaction()
                    .replace(R.id.step_detail_fragment, stepDetailFragment)
                    .commit();
        } else {
            Intent intent = new Intent(this, StepDetailActivity.class);
            intent.putExtra(StepDetailActivity.EXTRA_POSITION, position);
            ArrayList<Step> items = new ArrayList<Step>();
            items.addAll(mRecipe.getSteps());

            intent.putParcelableArrayListExtra(StepDetailActivity.EXTRA_STEPS, items);
            startActivity(intent);
        }
    }
}
