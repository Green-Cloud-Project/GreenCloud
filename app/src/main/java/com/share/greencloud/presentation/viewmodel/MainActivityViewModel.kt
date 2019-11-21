package com.share.greencloud.presentation.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import com.share.greencloud.R
import com.share.greencloud.data.api.ApiManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {
    val context = application

    fun addUserFavorite(header: Map<String, String>, office_id: String) {
        ApiManager.getInstance().addUserFavoritePlace(header, office_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ (result) ->
                    if (result == 0) {
                        Toast.makeText(context, context.getString(R.string.success_msg_add_user_favorite_place), Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, context.getString(R.string.fail_msg_add_user_favorite_place), Toast.LENGTH_SHORT).show()
                    }
                }
                ) { throwable: Throwable -> Timber.d("Throwable " + throwable.message) }
    }
}