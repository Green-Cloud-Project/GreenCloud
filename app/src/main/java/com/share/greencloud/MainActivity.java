package com.share.greencloud;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.share.greencloud.activity.LayoutListActivity;
import com.share.greencloud.common.Constants;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Constants.MODE == Constants.MODE.DEBUG) {
            startActivity(new Intent(this, LayoutListActivity.class));
            Timber.plant(new Timber.DebugTree());
        }

    }
}
