package com.share.greencloud.presentation;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.share.greencloud.presentation.viewmodel.BottomNavViewModel;

public class ViewModelFactory implements ViewModelProvider.Factory {
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(BottomNavViewModel.class)) {
            return (T) new BottomNavViewModel();
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
