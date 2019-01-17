package com.zrdb.app.ui.model;

import com.zrdb.app.ui.callback.ILoginCallback;

public interface ILoginModel {
    void sendNetLogin(String phone, String pwd, ILoginCallback callback);

    void sendNetLogin(String access_token, String expires_in, String openid, String refresh_token, String scope, ILoginCallback callback);
}
