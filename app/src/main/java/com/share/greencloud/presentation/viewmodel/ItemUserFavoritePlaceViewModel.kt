/*
 * Copyright (c) 2019. GreenCloud All rights reserved.
 */

package com.share.greencloud.presentation.viewmodel

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.databinding.BaseObservable
import com.google.android.gms.maps.model.LatLng
import com.share.greencloud.domain.model.RentalOffice
import com.share.greencloud.presentation.activity.MainActivity
import com.share.greencloud.utils.RxBus

class ItemUserFavoritePlaceViewModel(var rentalOffice: RentalOffice, val context: Context) : BaseObservable() {
    fun getOffice_name(): String? {
        return rentalOffice.office_name
    }

    fun getOffice_location(): String? {
        return rentalOffice.office_location
    }

    fun getLat(): Double {
        return rentalOffice.lat
    }

    fun getLon(): Double {
        return rentalOffice.lon
    }

    fun getUmbrella_count(): String {
        return rentalOffice.umbrella_count.toString()
    }

    fun updateRentalOffice(newData: RentalOffice) {
        rentalOffice = newData
        notifyChange()
    }

    fun onItemClick(view: View) {
        RxBus.getInstance().sendBus(LatLng(getLat(), getLon()))
        val i = Intent(view.context, MainActivity::class.java)
        i.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
        context.startActivity(i)
    }
}