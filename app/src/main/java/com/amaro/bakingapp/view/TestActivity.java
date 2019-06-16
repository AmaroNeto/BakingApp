package com.amaro.bakingapp.view;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.amaro.bakingapp.R;
import com.amaro.bakingapp.model.Recipe;
import com.amaro.bakingapp.view.fragment.StepFragment;

public class TestActivity extends AppCompatActivity {

    public static final String EXTRA_DATA = "recipe_extra";
    private Recipe mRecipe;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teste);

        mRecipe = getIntent().getParcelableExtra(EXTRA_DATA);

        StepFragment stepFragment = StepFragment.newInstance(mRecipe.getSteps());

        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .add(R.id.step_list_fragment, stepFragment)
                .commit();

    }
}
