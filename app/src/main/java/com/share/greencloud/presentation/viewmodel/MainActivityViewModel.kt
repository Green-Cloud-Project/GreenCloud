package com.share.greencloud.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.share.greencloud.data.reopsitory.Repository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class MainActivityViewModel : ViewModel() {

    private val _result = MutableLiveData<Int?>()
    val result: LiveData<Int?>
        get() = _result

    private val repository = Repository()
    private  var disposable: Disposable? = null

    init {
        _result.value = 1
    }

    fun addUserFavorite(header: Map<String, String>, office_id: String) {
        disposable = repository.addUserFavorite(header, office_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _result.value = it.result
                }, this::handleError)
    }

    fun clearResult() {
        _result.value = 1
    }

    private fun handleError(throwable: Throwable) = Timber.d("Throwable %s", throwable.message)

    override fun onCleared() {
        super.onCleared()
        if(!disposable!!.isDisposed && disposable!= null)  {
            disposable?.dispose()
        }
    }
}