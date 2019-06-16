package com.amaro.bakingapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

public class MainViewModelFactory implements ViewModelProvider.Factory {

    private final Application application;

    public MainViewModelFactory(Application app) {
        application = app;
    }

    @NonNull
    @Override
    public MainViewModel create(@NonNull Class modelClass) {
        return new MainViewModel(application);
    }
}
