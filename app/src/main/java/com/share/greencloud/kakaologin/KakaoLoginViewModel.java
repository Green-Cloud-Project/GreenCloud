package com.share.greencloud.kakaologin;

import android.arch.lifecycle.ViewModel;

public class KakaoLoginViewModel extends ViewModel {
    //    final private Context context;
    private String accesToken = "";

//    /*쉐어드에 입력값 저장*/
//    void saveShared(String nickname, String emailAddress, String profileUrl) {
//        SharedPreferences pref = context.getSharedPreferences("profile", MODE_PRIVATE);
//        SharedPreferences.Editor editor = pref.edit();
//        editor.putString("nickname", nickname);
//        editor.putString("email", emailAddress);
//        editor.putString("profileImg", profileUrl);
//        editor.apply();
//    }

public void saveAccessToken(String accesToken) {
        //todo
    }
}
