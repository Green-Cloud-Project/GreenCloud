/*
 * Copyright (c) 2019. GreenCloud All rights reserved.
 */

package com.share.greencloud.presentation.adapter;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.share.greencloud.presentation.fragment.UserHistoryOneFragment;
import com.share.greencloud.presentation.fragment.UserHistoryTwoFragment;


public class UserHistoryPagerAdapter extends FragmentPagerAdapter {

    private static final String[] TAB_TITLES = new String[]{"완료","대여중"};
    private final Context mContext;

    public UserHistoryPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                UserHistoryOneFragment tab1 = new UserHistoryOneFragment();
                return tab1;
            case 1:
                UserHistoryTwoFragment tab2 = new UserHistoryTwoFragment();
                return tab2;
            default: return null;
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return TAB_TITLES[position];
    }

    @Override
    public int getCount() {
        return 2;
    }
}
