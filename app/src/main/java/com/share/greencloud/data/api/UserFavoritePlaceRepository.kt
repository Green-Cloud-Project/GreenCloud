/*
 * Copyright (c) 2019. GreenCloud All rights reserved.
 */

package com.share.greencloud.data.api

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.share.greencloud.domain.interator.CallbackListener
import com.share.greencloud.domain.model.RentalOffice
import com.share.greencloud.utils.GreenCloudPreferences
import timber.log.Timber

class UserFavoritePlaceRepository(val application: Application) {
    private var userData: MutableLiveData<List<RentalOffice>> = MutableLiveData()

    fun getUserFavoriteData(): MutableLiveData<List<RentalOffice>> {
        val header: Map<String, String> = mapOf("token" to GreenCloudPreferences.getToken(application))
        ApiManager.getInstance().getUserFavoriteList(header, object : CallbackListener<List<RentalOffice>>() {
            override fun callback(result: List<RentalOffice>?) {
                Timber.d("user data size" + result!!.size)
                userData.postValue(result)
            }

            override fun failed(msg: String?) {
                Timber.d("error msg:" + msg)
            }

            override fun startApi() {}

            override fun endApi() {}
        })
        return userData
    }
}