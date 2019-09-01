package com.share.greencloud.login;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

public class LoginManager {
    private static LoginManager loginManager;
    private GoogleLoginProvider googleLoginProvider;
    private KakaoLoginProvider kakaoLoginProvider;
    private NaverLoginProvider naverLoginProvider;
    private FacebookLoginProvider facebookLoginProvider;
    private LoginType currentLoginType;

    public static LoginManager getInstance() {
        if (loginManager == null)
            loginManager = new LoginManager();
        return loginManager;
    }

    public LoginManager() {
        googleLoginProvider = new GoogleLoginProvider();
        kakaoLoginProvider = new KakaoLoginProvider();
        naverLoginProvider = new NaverLoginProvider();
        facebookLoginProvider = new FacebookLoginProvider();

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
}
