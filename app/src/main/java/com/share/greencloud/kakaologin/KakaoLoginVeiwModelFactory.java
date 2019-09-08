package com.share.greencloud.kakaologin;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;

public class KakaoLoginVeiwModelFactory implements ViewModelProvider.Factory {
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(KakaoLoginViewModel.class)) {
            return (T) new KakaoLoginViewModel();
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
