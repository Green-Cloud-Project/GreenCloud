package com.share.greencloud.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.share.greencloud.data.api.ApiManager
import com.share.greencloud.data.api.UserFavoritePlaceRepository
import com.share.greencloud.domain.model.RentalOffice
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class UserFavoritePlaceViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = UserFavoritePlaceRepository(application)
    private val context = application
    private var result_code: MutableLiveData<Int> = MutableLiveData()

    fun getUserFavoriteData(): LiveData<List<RentalOffice>> {
        return repository.getUserFavoriteData()
    }

    fun deleteUserFavorite(header: Map<String, String>, office_id: String): MutableLiveData<Int> {
        ApiManager.getInstance().deleteUserFavoritePlace(header, office_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ (result) ->
                    result_code.postValue(result)
                }) { throwable: Throwable -> Timber.d("Throwable " + throwable.message) }
        return result_code
    }
}