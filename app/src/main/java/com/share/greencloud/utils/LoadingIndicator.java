package com.share.greencloud.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;

import com.share.greencloud.R;

public class LoadingIndicator {


    public static LoadingIndicator mLoadingIndicator;

    public Dialog mDialog;
    private ProgressBar mProgressBar;
    private static Context mContext;

    private LoadingIndicator(Context context) {

        this.mContext = context;

    }

    public static LoadingIndicator getInstance() {

        if (mLoadingIndicator == null) {
            mLoadingIndicator = new LoadingIndicator(mContext);
        }
        return mLoadingIndicator;
    }

    public void showProgress(Context m_Context) {

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

        if (mDialog!= null && mDialog.isShowing()) {
            mDialog.dismiss();
        }

    }
}
