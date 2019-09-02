package com.share.greencloud.login;

import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.Task;

public class LoginManager {
    private static LoginManager loginManager;
    private GoogleLoginProvider googleLoginProvider;
    private KakaoLoginProvider kakaoLoginProvider;
    private LoginType currentLoginType;

    public static LoginManager getInstance() {
        if (loginManager == null)
            loginManager = new LoginManager();
        return loginManager;
    }

    public LoginManager() {
        googleLoginProvider = new GoogleLoginProvider();
        kakaoLoginProvider = new KakaoLoginProvider();

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
                break;
            case FACEBOOK:
                break;
        }
    }

    public void onActivityResult(Context context, int requestCode, int resultCode, Intent data) {

        switch (currentLoginType) {
            case GOOGLE:
                googleLoginProvider.hideProgressDialog();
                if (requestCode == GoogleLoginProvider.RC_SIGN_IN) {
                    Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                    googleLoginProvider.handleSignInResult(context, task);
                }
                else {
                    Toast.makeText(context, "Login Failed", Toast.LENGTH_SHORT).show();
                }
                break;
            case KAKAO:
                break;
            case NAVER:
                break;
            case FACEBOOK:
                break;
        }

    }
}
