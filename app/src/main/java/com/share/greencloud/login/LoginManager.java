package com.share.greencloud.login;

import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

public class LoginManager {
    private static LoginManager loginManager;
    private GoogleLoginProvider googleLoginProvider;
    private KakaoLoginProvider kakaoLoginProvider;
    private NaverLoginProvider naverLoginProvider;
    private FacebookLoginProvider facebookLoginProvider;
    private LoginType currentLoginType;
    private LoginEventListener loginEventListener;

    public static LoginManager getInstance() {
        if (loginManager == null)
            loginManager = new LoginManager();
        return loginManager;
    }

    public LoginManager() {
        googleLoginProvider = new GoogleLoginProvider(this);
        kakaoLoginProvider = new KakaoLoginProvider(this);
        naverLoginProvider = new NaverLoginProvider(this);
        facebookLoginProvider = new FacebookLoginProvider(this);

    }

    public void signIn(LoginType loginType, AppCompatActivity activity) {
        currentLoginType = loginType;
        switch (loginType) {
            case GOOGLE:
                googleLoginProvider.signIn(activity);
                break;
            case KAKAO:
                kakaoLoginProvider.signIn(activity);
                break;
            case NAVER:
                naverLoginProvider.signIn(activity);
                break;
            case FACEBOOK:
                facebookLoginProvider.signIn(activity);
                break;
        }
    }

    public void onActivityResult(Context context, int requestCode, int resultCode, Intent data) {

        switch (currentLoginType) {
            case GOOGLE:
                googleLoginProvider.onActivityResult(context, requestCode, resultCode, data);
                break;
            case KAKAO:
                kakaoLoginProvider.onActivityResult(requestCode, resultCode, data);
                break;
            case NAVER:
                break;
            case FACEBOOK:
                facebookLoginProvider.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

    public void setLoginEventListener(LoginEventListener loginEventListener) {
        this.loginEventListener = loginEventListener;
    }

    protected void onLogin(LoginType loginType) {
        if (loginEventListener != null)
            loginEventListener.onLogin(loginType);
    }
}
