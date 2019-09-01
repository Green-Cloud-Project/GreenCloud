package com.share.greencloud.login;

import android.support.v7.app.AppCompatActivity;

import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;

public class NaverLoginProvider implements ILoginProvider {
    private static final String OAUTH_CLIENT_ID = "";
    private static final String OAUTH_CLIENT_SECRET = "";
    private static final String OAUTH_CLIENT_NAME = "";
    private static OAuthLogin mOAuthLoginInstance;
    private boolean isInit = false;

    public NaverLoginProvider() {
        mOAuthLoginInstance = OAuthLogin.getInstance();
        mOAuthLoginInstance.showDevelopersLog(true);
    }

    public void signIn(AppCompatActivity activity) {
        if (isInit) {
            isInit = true;
            mOAuthLoginInstance.init(activity, OAUTH_CLIENT_ID, OAUTH_CLIENT_SECRET, OAUTH_CLIENT_NAME);
            mOAuthLoginInstance.startOauthLoginActivity(activity, new OAuthLoginHandler() {
                @Override
                public void run(boolean success) {

                }
            });
        }
    }
}