package com.share.greencloud.kakaologin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.kakao.auth.AuthType;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.LoginButton;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.util.exception.KakaoException;
import com.kakao.util.helper.log.Logger;
import com.share.greencloud.R;

import java.util.ArrayList;
import java.util.List;

public class KakaoLoginActiviy extends AppCompatActivity {
    private LoginButton btn_kakao_login;
    private SessionCallback callback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kakao_login_activiy);

        callback = new SessionCallback();
        Session.getCurrentSession().addCallback(callback);
        Session.getCurrentSession().checkAndImplicitOpen();

        btn_kakao_login = findViewById(R.id.com_kakao_login);
        btn_kakao_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Session.getCurrentSession().open(AuthType.KAKAO_LOGIN_ALL, KakaoLoginActiviy.this);
            }
        });

    }

    private class SessionCallback implements ISessionCallback {

        @Override
        public void onSessionOpened() {
            if (Session.getCurrentSession().isOpened()) { // 한 번더 세션을 체크해주었습니다.
                requestMe();
            }

        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            if (exception != null) {
                Logger.e(exception);
            }
        }
    }

    private void requestMe() {
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
                Toast.makeText(KakaoLoginActiviy.this, response.getNickname()+ "님이 카카오 간편 로그인에 성공하였습니다!", Toast.LENGTH_SHORT).show();
                saveShared(response.getNickname(),response.getKakaoAccount().getEmail(),response.getProfileImagePath());
            }
        });

    }

    /*쉐어드에 입력값 저장*/
    private void saveShared( String nickname, String emailAddress, String profileUrl) {
        SharedPreferences pref = getSharedPreferences("profile", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("nickname", nickname);
        editor.putString("email", emailAddress);
        editor.putString("profileImg", profileUrl);
        editor.apply();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Session.getCurrentSession().removeCallback(callback);
    }
}
