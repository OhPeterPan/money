package com.zrdb.director.ui.model;

import com.zrdb.director.ui.callback.ILoginCallback;

public interface ILoginModel {
    void sendNetLogin(String phone, String pwd, ILoginCallback callback);

}
