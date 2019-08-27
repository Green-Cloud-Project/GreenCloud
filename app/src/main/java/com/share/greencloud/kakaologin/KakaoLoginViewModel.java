package com.share.greencloud.kakaologin;

import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

class KakaoLoginViewModel extends ViewModel {
    final private Context context;
    private String accesToken = "";

    KakaoLoginViewModel(Context context) {
        this.context = context;
    }

    /*쉐어드에 입력값 저장*/
    void saveShared(String nickname, String emailAddress, String profileUrl) {
        SharedPreferences pref = context.getSharedPreferences("profile", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("nickname", nickname);
        editor.putString("email", emailAddress);
        editor.putString("profileImg", profileUrl);
        editor.apply();
    }

    void saveAccessToken(String accesToken){
        //todo
    }
}
