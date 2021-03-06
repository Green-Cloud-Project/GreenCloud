/*
 * Copyright (c) 2019. GreenCloud All rights reserved.
 */

package com.share.greencloud.presentation.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.databinding.BaseObservable;

import com.google.android.gms.maps.model.LatLng;
import com.share.greencloud.domain.model.RentalOffice;
import com.share.greencloud.presentation.activity.MainActivity;
import com.share.greencloud.utils.RxBus;

public class ItemSearchResultViewModel extends BaseObservable {

    private RentalOffice rentalOffice;
    private Context context;

    public ItemSearchResultViewModel(RentalOffice rentalOffice, Context context) {
        this.rentalOffice = rentalOffice;
        this.context = context;
    }

    public void setRentalOffice(RentalOffice rentalOffice) {
        this.rentalOffice = rentalOffice;
        notifyChange();
    }

    public int getOffice_id() {
        return rentalOffice.getOffice_id();
    }

    public String getOffice_name() {
        return rentalOffice.getOffice_name();
    }

    public String getOffice_location() {
        return rentalOffice.getOffice_location();
    }

    public double getLat() {
        return rentalOffice.getLat();
    }

    public double getLon() {
        return rentalOffice.getLon();
    }

    public int getUmbrella_count() {
        return rentalOffice.getUmbrella_count();
    }

    public String getDistance() {
        return addDistanceSign(rentalOffice.getDistance());
    }

    // 현재 위치에서 마커까지 거리 계산
    private String addDistanceSign(int result) {

        // 1km 기준으로 표기법을 구분지음
        if (result > 1000)
            return ((int) result / 1000) + " km";
        else
            return (int) result + " m";
    }

    public void onItemClick(View view) {
        RxBus.getInstance().sendBus(new LatLng(getLat(), getLon()));
        Intent i = new Intent(view.getContext(), MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        context.startActivity(i);
    }
}
