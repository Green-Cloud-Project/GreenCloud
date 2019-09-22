package com.share.greencloud.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.share.greencloud.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(() -> startActivity(new Intent(SplashActivity.this, BottomNavActivity.class)), 1000);
    }

    public static void go(AppCompatActivity activity) {
        activity.startActivity(new Intent(activity, SplashActivity.class));
    }
}
