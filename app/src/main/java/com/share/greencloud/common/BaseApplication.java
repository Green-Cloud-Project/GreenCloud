package com.share.greencloud.common;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.kakao.auth.KakaoSDK;
import com.share.greencloud.kakaologin.KakaoSDKAdapter;
import com.share.greencloud.utils.GreenCloudPreferences;
import com.squareup.leakcanary.LeakCanary;

import timber.log.Timber;


public class BaseApplication extends Application {
    private static BaseApplication instance;

    public static BaseApplication getGlobalApplicationContext() {
        if (instance == null) {
            throw new IllegalStateException("This Application does not inherit com.kakao.BaseApplication");
        }

        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        // Kakao Sdk 초기화
        KakaoSDK.init(new KakaoSDKAdapter());

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);

        Stetho.initializeWithDefaults(this);

        //토큰 셋팅
        Constants.token = GreenCloudPreferences.getToken(getBaseContext());

        Timber.plant(new Timber.DebugTree());
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        instance = null;
    }
}
