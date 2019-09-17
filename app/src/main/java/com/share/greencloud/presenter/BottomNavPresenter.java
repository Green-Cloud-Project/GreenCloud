package com.share.greencloud.presenter;

import android.view.Menu;

public class BottomNavPresenter {

    private View view;

    public BottomNavPresenter(View view) {
        this.view = view;
    }

    public interface View{
        void updateMenuItemVisible(Menu menu,Boolean status);
    }
}
