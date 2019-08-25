package com.share.greencloud.login;

import android.support.v7.app.AppCompatActivity;

import com.kakao.auth.AuthType;
import com.kakao.auth.Session;
import com.share.greencloud.kakaologin.KakaoLoginActiviy;

public class KakaoLoginProvider {
    private KakaoLoginActiviy.SessionCallback callback;

    public KakaoLoginProvider(){
        //callback = new KakaoLoginActiviy.SessionCallback();
        //Session.getCurrentSession().addCallback(callback);
        Session.getCurrentSession().checkAndImplicitOpen();
    }

    public void signIn(AppCompatActivity activity) {
        Session.getCurrentSession().open(AuthType.KAKAO_LOGIN_ALL, activity);
    }
}
