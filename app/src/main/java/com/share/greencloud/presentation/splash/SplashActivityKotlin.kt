package com.share.greencloud.presentation.splash

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.share.greencloud.R
import com.share.greencloud.domain.login.LoginManager
import com.share.greencloud.domain.network.NetworkState
import com.share.greencloud.presentation.activity.MainActivity
import com.share.greencloud.presentation.activity.LoginActivity
import com.share.greencloud.presentation.activity.OnBoardingActivity
import com.share.greencloud.utils.GreenCloudPreferences
import kotlinx.android.synthetic.main.activity_splash_kotlin.*
import timber.log.Timber

class SplashActivityKotlin : AppCompatActivity(R.layout.activity_splash_kotlin), SpashView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        scheduleSplashScreen()
    }

    private fun scheduleSplashScreen() {
        val splashScreenDuration = getSplashScreenDuration()
        Handler().postDelayed(
                {
                    checkNetworkConnection()
                },
                splashScreenDuration
        )
    }

    private fun checkNetworkConnection() {
        val spashViewModel = SpashViewModel(this, this)
        var result = spashViewModel.checkNetwork()
        Timber.d("check result: $result")
        when (result) {
            is NetworkState.Success -> routeToAppropriatePage()
            is NetworkState.Error -> this.finish()
        }
    }

    private fun makeErrorMessage() {
        Toast.makeText(this, "현재 서버 접속에 문제가 있습니다. 잠시 후 다시 이용해주시길 부탁드립니다. ", Toast.LENGTH_LONG).show()
        Handler().postDelayed({
            this.finish()
        }, 1000)
    }

    /**
     * Splash 화면 이후에, onBoarding 노출 여부와 유저의 로그인 여부에, 따라서 다음에 어떤 화면으로 이동할지 결정한다.
     *
     * @param none
     * @param none
     * @return none
     *
     */
    private fun routeToAppropriatePage() {
        if (!GreenCloudPreferences.getOnBoarding(this)) {
            GreenCloudPreferences.setOnBoarding(this, true)
            startActivity(Intent(this, OnBoardingActivity::class.java))
        } else if (LoginManager.getInstance().isLogin(this)) {
            startActivity(Intent(this, MainActivity::class.java))
        } else {
            LoginActivity.go(this)
        }
    }

    // 유저에 따라서 SplashScreen 실행시간을 구분해줌
    private fun getSplashScreenDuration(): Long {
        val sp = getPreferences(Context.MODE_PRIVATE)
        val prefKeyFirstLaunch = "pref_first_launch"

        return when (!sp.getBoolean(prefKeyFirstLaunch, false)) {
            true -> {
                // 처음 앱을 실행한 유저일 경우
                sp.edit().putBoolean(prefKeyFirstLaunch, true).apply()
                3000
            }
            false -> {
                // 기존에 실행한 적이 있는 유저일 경우
                1000
            }
        }
    }

    override fun onPause() {
        super.onPause()
        this.finish()
    }

    override fun showProgress() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressBar.visibility = View.INVISIBLE
    }

    override fun onError(throwable: Throwable) {
        Toast.makeText(this, "네트워크가 연결되지 않았습니다. $throwable", Toast.LENGTH_SHORT).show()
    }

    // kotlin extension 함수로 정의한, 액티비티 전환하는 함수
    inline fun <reified T : Activity> Activity.launchActivity() {
        val intent = Intent(this, T::class.java)
        startActivity(intent)
    }
}
