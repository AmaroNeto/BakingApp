package com.amaro.bakingapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amaro.bakingapp.R;
import com.amaro.bakingapp.model.Recipe;
import com.amaro.bakingapp.view.adapter.RecipeAdapter;
import com.amaro.bakingapp.viewmodel.MainViewModel;
import com.amaro.bakingapp.viewmodel.MainViewModelFactory;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements RecipeAdapter.ListItemClickListener{

    private final static String TAG = "MainActivity";
    private final static int NUM_COLUMN = 3;

    @BindView(R.id.recipe_recyclerview)
    RecyclerView mRecipeRecycleView;

    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    private MainViewModel mViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        ButterKnife.bind(this);

        setupRecyclerView();
        setupViewModel();
    }

    private void setupRecyclerView() {
        if(getResources().getBoolean(R.bool.is_sw600)) {
            mRecipeRecycleView.setLayoutManager(new GridLayoutManager(this, NUM_COLUMN));
        } else {
            mRecipeRecycleView.setLayoutManager(new LinearLayoutManager(this));
        }
        mRecipeRecycleView.setHasFixedSize(true);
    }

    private void showProgressbar() {
        mProgressBar.setVisibility(View.VISIBLE);
        mRecipeRecycleView.setVisibility(View.GONE);
    }

    private void showResult() {
        mProgressBar.setVisibility(View.GONE);
        mRecipeRecycleView.setVisibility(View.VISIBLE);
    }

    private void setData(List<Recipe> recipes) {
        RecipeAdapter adapter = new RecipeAdapter(this,recipes);
        adapter.setOnListItemClick(this);
        mRecipeRecycleView.setAdapter(adapter);
    }

    private void setupViewModel() {
        mViewModel = ViewModelProviders.of(this, new MainViewModelFactory( getApplication())).get(MainViewModel.class);
        mViewModel.getRecipes().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(List<Recipe> recipes) {
                if(recipes != null) {
                    setData(recipes);
                }

                showResult();
            }
        });
    }

    @Override
    public void onListItemClick(Recipe recipe) {
        Log.d(TAG,"Recipe selected: "+recipe.getName());
        Intent intent = new Intent(this, RecipeDetailActivity.class);
        intent.putExtra(RecipeDetailActivity.EXTRA_DATA, recipe);
        startActivity(intent);
    }
}
