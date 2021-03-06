/*
 * Copyright (c) 2019. GreenCloud All rights reserved.
 */

package com.share.greencloud.domain.login;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;

import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;

public class NaverLoginProvider extends LoginProvider implements ILoginProvider {
    private static final String OAUTH_CLIENT_ID = "4hYhfZvOSM6dBqpzxqSV";
    private static final String OAUTH_CLIENT_SECRET = "SjuqtEIoRk";
    private static final String OAUTH_CLIENT_NAME = "그린클라우드";
    private static OAuthLogin mOAuthLoginInstance;
    private boolean isInit = false;

    public NaverLoginProvider(LoginManager loginManager) {
        super(loginManager);
        mOAuthLoginInstance = OAuthLogin.getInstance();
        mOAuthLoginInstance.showDevelopersLog(true);
    }

    public void signIn(AppCompatActivity activity) {
        if (!isInit) {
            isInit = true;
            mOAuthLoginInstance.init(activity, OAUTH_CLIENT_ID, OAUTH_CLIENT_SECRET, OAUTH_CLIENT_NAME);
            mOAuthLoginInstance.startOauthLoginActivity(activity, new OAuthLoginHandler() {
                @Override
                public void run(boolean success) {
                    signInResult(success);
                }
            });
        }
    }

    /**
     로그아웃을 한다.
     */
    @Override
    public void signOut() {

    }

    /**
     현재 로그인 상태인지?
     */
    @Override
    public boolean isSignIn() {
        return false;
    }

    /**
     로그인 성공 실패여부

     @param isSuccess
     */
    @Override
    public void signInResult(boolean isSuccess) {
        Log.d("sryang", "" + isSuccess);
    }

    /**
     각 플렛폼에서 서버를 통해 본인을 정보에 접근할 수 있는 키값

     @return 키값(엑세스토큰, authkey...)
     */
    @Override
    public String getKey() {
        return null;
    }
}