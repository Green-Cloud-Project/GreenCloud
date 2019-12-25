/*
 * Copyright (c) 2019. GreenCloud All rights reserved.
 */

package com.share.greencloud.presentation.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.share.greencloud.R;
import com.share.greencloud.presentation.viewmodel.OnBoardingViewModel;

public class OnBoardingFragment extends Fragment {

    private OnBoardingViewModel mViewModel;

    public static OnBoardingFragment newInstance() {
        return new OnBoardingFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.on_boarding_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(OnBoardingViewModel.class);
        // TODO: Use the ViewModel
    }

}