package com.share.greencloud.kakaologin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.kakao.auth.ApiResponseCallback;
import com.kakao.auth.AuthService;
import com.kakao.auth.AuthType;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.auth.network.response.AccessTokenInfoResponse;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.util.exception.KakaoException;
import com.share.greencloud.R;
import com.share.greencloud.databinding.ActivityKakaoLoginActiviyBinding;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class KakaoLoginActiviy extends AppCompatActivity {
    private SessionCallback callback;
    private KakaoLoginViewModel viewModel;
    private KakaoLoginVeiwModelFactory veiwModelFactory;
    private ActivityKakaoLoginActiviyBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_kakao_login_activiy);

        callback = new SessionCallback();
        Session.getCurrentSession().addCallback(callback);
        Session.getCurrentSession().checkAndImplicitOpen();

        veiwModelFactory = new KakaoLoginVeiwModelFactory();
        viewModel = ViewModelProviders.of(this, veiwModelFactory).get(KakaoLoginViewModel.class);

        binding.comKakaoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Session.getCurrentSession().open(AuthType.KAKAO_LOGIN_ALL, KakaoLoginActiviy.this);
            }
        });

    }

    public class SessionCallback implements ISessionCallback {

        @Override
        public void onSessionOpened() {
            if (Session.getCurrentSession().isOpened()) { // 한 번더 세션을 체크해주었습니다.
                requestMe();
                requestAccessTokenInfo();
            }

        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            if (exception != null) {
                Timber.e(exception);
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
                Timber.d(message);
            }

            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                Timber.i("onSessionClosed: %s", errorResult);
            }

            @Override
            public void onSuccess(MeV2Response response) {
                Timber.i("KaKaLogin Success: %s", response.toString());

                // 토큰 만료시 갱신을 시켜준다
                if (Session.getCurrentSession().isOpenable()) {
                    Session.getCurrentSession().checkAndImplicitOpen();
                }

                Toast.makeText(KakaoLoginActiviy.this, response.getNickname() + "님이 카카오 간편 로그인에 성공하였습니다!", Toast.LENGTH_SHORT).show();
                // todo: Access Token 서버 전달
                String accessToken = Session.getCurrentSession().getTokenInfo().getAccessToken();
                String refeshToken = Session.getCurrentSession().getTokenInfo().getRefreshToken();

                viewModel.saveAccessToken(accessToken);
                //viewModel.saveShared(response.getNickname(), response.getKakaoAccount().getEmail(), response.getProfileImagePath());
            }
        });

    }

    private void requestAccessTokenInfo() {
        AuthService.getInstance().requestAccessTokenInfo(new ApiResponseCallback<AccessTokenInfoResponse>() {
            @Override
            public void onSessionClosed(ErrorResult errorResult) {

            }

            @Override
            public void onNotSignedUp() {
                // not happened
            }

            @Override
            public void onFailure(ErrorResult errorResult) {
                Timber.e("failed to get access token info. msg=" + errorResult);
            }

            @Override
            public void onSuccess(AccessTokenInfoResponse accessTokenInfoResponse) {
                // todo: Access Token를 발급 받기 위해서 필요한 값을 서버에 전달
                long userId = accessTokenInfoResponse.getUserId();
                Timber.d(accessTokenInfoResponse.toString());
                Timber.d("this access token is for userId=" + userId);

                long expiresInMilis = accessTokenInfoResponse.getExpiresInMillis();
                Timber.d("this access token expires after " + expiresInMilis + " milliseconds.");
            }
        });
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
