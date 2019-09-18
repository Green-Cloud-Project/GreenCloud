package com.share.greencloud.presenter;

import android.view.Menu;

public class BottomNavPresenter {

    private View view;

    public BottomNavPresenter(View view) {
        this.view = view;
    }

    public void updateMenuItemVisible(Menu menu, Boolean visibility) {
        view.updateMenuItemVisible(menu, visibility);
    }

    public interface View {
        void updateMenuItemVisible(Menu menu, Boolean visibility);
    }
}
