package com.share.greencloud.presentation.splash

interface SpashView {

    fun showProgress()

    fun hideProgress()

    fun onError(throwable: Throwable)
}