package com.share.greencloud.login;

import android.support.v7.app.AppCompatActivity;

/**
 로그인을 하기위한 공통기능 정의
 */
public interface ILoginProvider {
    /**
     로그인을 한다.
     */
    void signIn(AppCompatActivity appCompatActivity);

    /**
     로그아웃을 한다.
     */
    void signOut();

    /**
     현재 로그인 상태인지?
     */
    boolean isSignIn();

    /**
     로그인 성공 실패여부
     */
    void signInResult(boolean isSuccess);
}
