package com.share.greencloud.login;

import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.util.exception.KakaoException;
import com.kakao.util.helper.log.Logger;

public class SessionCallback implements ISessionCallback {

    @Override
    public void onSessionOpened() {
        if (Session.getCurrentSession().isOpened()) { // 한 번더 세션을 체크해주었습니다.
        }

    }

    @Override
    public void onSessionOpenFailed(KakaoException exception) {
        if (exception != null) {
            Logger.e(exception);
        }
    }
}
