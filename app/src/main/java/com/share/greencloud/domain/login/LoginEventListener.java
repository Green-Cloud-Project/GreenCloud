/*
 * Copyright (c) 2019. GreenCloud All rights reserved.
 */

package com.share.greencloud.domain.login;

public interface LoginEventListener {
    public void onLogin(LoginType loginType);
}
