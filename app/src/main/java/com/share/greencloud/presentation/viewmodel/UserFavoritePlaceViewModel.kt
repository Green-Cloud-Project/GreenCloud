package com.share.greencloud.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.share.greencloud.data.api.UserFavoritePlaceRepository
import com.share.greencloud.domain.model.RentalOffice

class UserFavoritePlaceViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = UserFavoritePlaceRepository(application)

    fun getUserFavoriteData(): LiveData<List<RentalOffice>> {
        return repository.getUserFavoriteData()
    }
}