package com.share.greencloud.login;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;

import java.util.Arrays;
import java.util.Collection;

public class FacebookLoginProvider implements ILoginProvider {

    private CallbackManager callbackManager;

    public FacebookLoginProvider() {
        callbackManager = CallbackManager.Factory.create();

        com.facebook.login.LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                        signInResult(true);
                    }

                    @Override
                    public void onCancel() {
                        // App code
                        signInResult(false);
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                        signInResult(false);
                    }
                });
    }

    /**
     로그인을 한다.

     @param appCompatActivity
     */
    @Override
    public void signIn(AppCompatActivity appCompatActivity) {
        Collection<String> permission = Arrays.asList("public_profile");
        com.facebook.login.LoginManager.getInstance().logIn(appCompatActivity, permission);
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
     로그인 성공 실패여부@param isSuccess
     */
    @Override
    public void signInResult(boolean isSuccess) {

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (callbackManager != null)
            callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}