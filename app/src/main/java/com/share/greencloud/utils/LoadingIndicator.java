/*
 * Copyright (c) 2019. GreenCloud All rights reserved.
 */

package com.share.greencloud.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.share.greencloud.R;

import timber.log.Timber;

// Lazy singleton 사용하여 싱글톤 객체를 앱의 라이프싸이클 동안 하나의 instance만 사용하도록 수정함. - Bentley Park
// https://en.wikipedia.org/wiki/Initialization-on-demand_holder_idiom
public final class LoadingIndicator {
    private Dialog mDialog;
    private ProgressBar mProgressBar;

    private LoadingIndicator() {
    }

    public static LoadingIndicator getInstance() {
        return LazyHolder.INSTANCE;
    }

    private static class LazyHolder {
        private static final LoadingIndicator INSTANCE = new LoadingIndicator();

    }

    public void showProgress(Context m_Context) {
        Timber.d("showProgress is called");
        mDialog = new Dialog(m_Context);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(R.layout.custom_loading_indicator);
        mProgressBar = mDialog.findViewById(R.id.loading_indicator);
        mProgressBar.setVisibility(View.VISIBLE);
        mProgressBar.setIndeterminate(true);
        mDialog.setCancelable(false);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_TOAST);
        mDialog.show();
    }

    public void dismiss() {
        Timber.d("dismiss is called");
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    public boolean isShowing() {
        if (LazyHolder.INSTANCE != null && mDialog != null) {
            return mDialog.isShowing();
        }
        return false;
    }
}
