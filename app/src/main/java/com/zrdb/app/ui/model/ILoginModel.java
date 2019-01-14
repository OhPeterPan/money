package com.zrdb.app.ui.model;

import com.zrdb.app.ui.callback.ILoginCallback;

public interface ILoginModel {
    void sendNetLogin(String phone, String pwd, ILoginCallback callback);

}
