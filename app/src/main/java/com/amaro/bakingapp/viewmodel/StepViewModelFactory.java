package com.amaro.bakingapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import com.amaro.bakingapp.model.Step;

import java.util.List;

public class StepViewModelFactory implements ViewModelProvider.Factory {

    private Application mApplication;
    private List<Step> mSteps;

    public StepViewModelFactory(Application mApplication, List<Step> mSteps) {
        this.mApplication = mApplication;
        this.mSteps = mSteps;
    }

    @NonNull
    @Override
    public StepViewModel create(@NonNull Class modelClass) {
        return new StepViewModel(mApplication, mSteps);
    }
}
