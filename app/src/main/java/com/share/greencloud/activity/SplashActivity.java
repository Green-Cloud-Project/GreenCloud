package com.share.greencloud.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.share.greencloud.R;
import com.share.greencloud.fragment.LoginFragment;
import com.share.greencloud.login.LoginManager;
import com.share.greencloud.utils.GreenCloudPreferences;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!GreenCloudPreferences.getOnBoarding(SplashActivity.this)) {
                    GreenCloudPreferences.setOnBoarding(SplashActivity.this, true);
                    startActivity(new Intent(SplashActivity.this, OnBoardingActivity.class));
                }
                else if (LoginManager.getInstance().isLogin()) {
                    startActivity(new Intent(SplashActivity.this, BottomNavActivity.class));
                }
                else {
                    LoginActivity.go(SplashActivity.this);
                }
            }
        }, 1000);
    }

    public static void go(AppCompatActivity activity) {
        activity.startActivity(new Intent(activity, SplashActivity.class));
    }
}
