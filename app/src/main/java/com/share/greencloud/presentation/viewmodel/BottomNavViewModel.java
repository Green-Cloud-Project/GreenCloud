package com.share.greencloud.presentation.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BottomNavViewModel extends ViewModel {

    private MutableLiveData<Boolean> hideSearchMenu = new MutableLiveData<>();

    public BottomNavViewModel() {
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
