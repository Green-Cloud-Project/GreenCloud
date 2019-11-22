package com.share.greencloud;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.share.greencloud.presentation.activity.LayoutListActivity;
import com.share.greencloud.presentation.activity.SplashActivity;

import timber.log.Timber;

public class LayoutMenuActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (BuildConfig.BUILD_TYPE.equals("debug")) {
            startActivity(new Intent(this, LayoutListActivity.class));
            Timber.plant(new Timber.DebugTree());
        }
        else {
            SplashActivity.go(this);
        }

    }
}
