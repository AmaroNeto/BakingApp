package com.amaro.bakingapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.amaro.bakingapp.model.Step;

import java.util.List;

public class StepViewModel extends AndroidViewModel {

    private List<Step> mSteps;

    public StepViewModel(@NonNull Application application, List<Step> steps) {
        super(application);
        mSteps = steps;
    }

    public List<Step> getSteps() {
        return mSteps;
    }
}
