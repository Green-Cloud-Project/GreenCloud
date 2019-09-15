package com.share.greencloud.common;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.kakao.auth.KakaoSDK;
import com.share.greencloud.kakaologin.KakaoSDKAdapter;
import com.squareup.leakcanary.LeakCanary;


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
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        instance = null;
    }
}
