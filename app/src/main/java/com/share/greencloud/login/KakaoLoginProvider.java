package com.share.greencloud.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.kakao.auth.AuthType;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.util.exception.KakaoException;
import com.kakao.util.helper.log.Logger;

import java.util.ArrayList;
import java.util.List;

public class KakaoLoginProvider implements ISessionCallback, ILoginProvider {
    Context context;

    public KakaoLoginProvider() {
        Session.getCurrentSession().addCallback(this);
        Session.getCurrentSession().checkAndImplicitOpen();
    }

    public void signIn(AppCompatActivity activity) {
        context = activity;
        Session.getCurrentSession().open(AuthType.KAKAO_LOGIN_ALL, activity);
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

    /**
     각 플렛폼에서 서버를 통해 본인을 정보에 접근할 수 있는 키값

     @return 키값(엑세스토큰, authkey...)
     */
    @Override
    public String getKey() {
        return null;
    }

    private void requestMe(final Context context) {
        List<String> keys = new ArrayList<>();
        keys.add("properties.nickname");
        keys.add("properties.profile_image");
        keys.add("kakao_account.email");

        UserManagement.getInstance().me(keys, new MeV2ResponseCallback() {
            @Override
            public void onFailure(ErrorResult errorResult) {
                String message = "failed to get user info. msg=" + errorResult;
                Logger.d(message);
            }

            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                Log.d("onSessionClosed", errorResult + "");
            }

            @Override
            public void onSuccess(MeV2Response response) {
                Log.e("onSuccess", response.toString());
                Toast.makeText(context, response.getNickname() + "님이 카카오 간편 로그인에 성공하였습니다!", Toast.LENGTH_SHORT).show();
                saveShared(context, response.getNickname(), response.getKakaoAccount().getEmail(), response.getProfileImagePath());
            }
        });

    }

    /*쉐어드에 입력값 저장*/
    private void saveShared(Context context, String nickname, String emailAddress, String profileUrl) {
        SharedPreferences pref = context.getSharedPreferences("profile", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("nickname", nickname);
        editor.putString("email", emailAddress);
        editor.putString("profileImg", profileUrl);
        editor.apply();
    }

    @Override
    public void onSessionOpened() {
        requestMe(context);
    }

    @Override
    public void onSessionOpenFailed(KakaoException e) {

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data);
    }
}
