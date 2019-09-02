package com.share.greencloud.lifecycleobserver;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;
import android.content.Context;

import timber.log.Timber;

public class MyObserver implements LifecycleObserver {
    private final Context mContext;

    public MyObserver(LifecycleOwner lifecycleOwner, Context context) {
        mContext = context;
        lifecycleOwner.getLifecycle().addObserver(this);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        Timber.d("onResume is called");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {
    }
}


