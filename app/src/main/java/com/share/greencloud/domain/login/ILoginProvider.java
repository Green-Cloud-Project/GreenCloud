/*
 * Copyright (c) 2019. GreenCloud All rights reserved.
 */

package com.share.greencloud.domain.login;

import androidx.appcompat.app.AppCompatActivity;

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

    /**
     각 플렛폼에서 서버를 통해 본인을 정보에 접근할 수 있는 키값
     @return 키값(엑세스토큰, authkey...)
     */
    String getKey();
}
