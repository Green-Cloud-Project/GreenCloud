/*
 * Copyright (c) 2019. GreenCloud All rights reserved.
 */

package com.share.greencloud.presentation.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.maps.model.LatLng;

public class SharedViewModel extends ViewModel {
    private MutableLiveData<LatLng> position = new MutableLiveData<>();
    private MutableLiveData<Boolean> movedNewPosition = new MutableLiveData<>();

    public SharedViewModel() {
        position.setValue(new LatLng(0,0));
        movedNewPosition.setValue(false);
    }

    public LiveData<LatLng> getPosition() {
        return position;
    }

    public void setPosition(LatLng newPosition) {
        position.setValue(newPosition);
    }

    public Boolean getMovedNewPosition() {
        return movedNewPosition.getValue();
    }

    public void clearValue() {
        position.setValue(new LatLng(0,0));
        movedNewPosition.setValue(false);
    }

    public void setMovedNewPosition(Boolean value) {
        movedNewPosition.setValue(value);
    }
}
