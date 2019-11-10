package com.share.greencloud.presentation.splash

import android.content.Context
import android.net.ConnectivityManager
import com.share.greencloud.domain.network.NetworkState
import timber.log.Timber
import kotlin.properties.Delegates
import kotlin.reflect.KProperty

class SpashViewModel(val view: SpashView, var context: Context?) {

    private var currentState: NetworkState<Boolean> by Delegates.observable(
        NetworkState.Init(),
        { _: KProperty<*>, _: NetworkState<Boolean>, newState: NetworkState<Boolean> ->

            when (newState) {
                is NetworkState.Init -> view.hideProgress()
                is NetworkState.Loading -> view.showProgress()
                is NetworkState.Success -> view.hideProgress()
                is NetworkState.Error -> view.onError(newState.throwable)
            }
        })

    fun checkNetwork(): NetworkState<Boolean> {
        currentState = NetworkState.Loading()
        currentState = try {
            NetworkState.Success(context!!.isConnected())
        } catch (error: Throwable) {
            NetworkState.Error(error)
        } finally {
            currentState = NetworkState.Init()
        }
        Timber.d("current state: $currentState")
        return currentState
    }

    // 네트워크 체크 함수 -> 연결에 실패하면 Exception 발생
    @Throws(IllegalStateException::class)
    private fun Context.isConnected(): Boolean {
        val connectivityManager =
            (getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetwork

        if (connectivityManager != null) {
            return true
        } else {
            throw IllegalStateException("네트워크 연결 후 다시 실행해주세요")
        }
    }
}