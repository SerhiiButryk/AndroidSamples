/*
 * Copyright 2021. Happy codding ! :)
 * Author: Serhii Butryk
 */
package com.example.app.ui.factory;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AbstractSavedStateViewModelFactory;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import androidx.savedstate.SavedStateRegistryOwner;

import com.example.app.viewmodel.AppViewModel;

import static com.example.app.utils.AppUtils.APP;

/**
 * Demonstrate the usage of AbstractSavedStateViewModelFactory factory
 */

public class AppViewModelFactory extends AbstractSavedStateViewModelFactory {

    public static final String TAG = APP + AppViewModelFactory.class.getSimpleName();

    public AppViewModelFactory(SavedStateRegistryOwner owner) {
        super(owner, null);
    }

    @NonNull
    @Override
    protected <T extends ViewModel> T create(@NonNull String key, @NonNull Class<T> modelClass, @NonNull SavedStateHandle handle) {

        AppViewModel viewModel = new AppViewModel(handle);

        Log.i(TAG, "create: ViewModel created - " + viewModel + " modelClass " + modelClass.getSimpleName());

        return (T) viewModel;
    }
}
