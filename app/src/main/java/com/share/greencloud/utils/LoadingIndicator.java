package com.share.greencloud.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;

import com.share.greencloud.R;

import timber.log.Timber;

// Lazy singleton 사용하여 싱글톤 객체를 앱의 라이프싸이클 동안 하나의 instance만 사용하도록 수정함. - Bentley Park
// https://en.wikipedia.org/wiki/Initialization-on-demand_holder_idiom
public final class LoadingIndicator {
//    public static LoadingIndicator mLoadingIndicator;

    public Dialog mDialog;
    private ProgressBar mProgressBar;
    private static Context mContext;

    private LoadingIndicator(Context context) {
        this.mContext = context;
    }

    public static LoadingIndicator getInstance() {
//        if (mLoadingIndicator == null) {
//            mLoadingIndicator = new LoadingIndicator(mContext);
//        }
//        return mLoadingIndicator;
        return LazyHolder.INSTANCE;
    }
/**/
    private static class LazyHolder {
        private static final LoadingIndicator INSTANCE = new LoadingIndicator(mContext);
    }

    private Object readResolve() {
        return LazyHolder.INSTANCE;
    }

    public void showProgress(Context m_Context) {
        Timber.d("showProgress is called");
        mDialog = new Dialog(m_Context);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(R.layout.custom_loading_indicator);
        mProgressBar = (ProgressBar) mDialog.findViewById(R.id.loading_indicator);
        mProgressBar.setVisibility(View.VISIBLE);
        mProgressBar.setIndeterminate(true);
        mDialog.setCancelable(false);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();

    }

    public void dismiss() {
        Timber.d("dismiss is called");
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }
}
