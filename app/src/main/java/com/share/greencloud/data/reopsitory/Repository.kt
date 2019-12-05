package com.share.greencloud.data.reopsitory

import com.share.greencloud.data.api.ApiManager
import com.share.greencloud.domain.model.GreenCloudRestResponse
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import retrofit2.Response

class Repository {
    fun addUserFavorite(header: Map<String, String>, office_id: String): Single<GreenCloudRestResponse<Any>> = ApiManager.getInstance().addUserFavoritePlace(header, office_id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { t: Response<GreenCloudRestResponse<Any>> ->
                if (t.isSuccessful) t.body()
                else throw HttpException(t)
            }

    fun deleteUserFavorite(header: Map<String, String>, office_id: String): Single<GreenCloudRestResponse<Any>> = ApiManager.getInstance().deleteUserFavoritePlace(header, office_id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { t: Response<GreenCloudRestResponse<Any>> ->
                if (t.isSuccessful) t.body()
                else throw HttpException(t)
            }
}