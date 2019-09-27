package com.share.greencloud.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BottomNavVIewModel extends ViewModel {

    private MutableLiveData<Boolean> hideSearchMenu = new MutableLiveData<>();

    public BottomNavVIewModel() {
        hideSearchMenu.setValue(false);
    }

    public MutableLiveData<Boolean> getHideSearchMenu() {
        return hideSearchMenu;
    }

    public void hideSearchMenu(){
        hideSearchMenu.setValue(true);
    }

    public void showSearchMenu(){
        hideSearchMenu.setValue(false);
    }
}
