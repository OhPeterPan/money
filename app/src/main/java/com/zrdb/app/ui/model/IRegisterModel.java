package com.zrdb.app.ui.model;

import com.zrdb.app.ui.callback.IRegisterCallback;

public interface IRegisterModel {
    void sendNetGetVerify(String phone, IRegisterCallback callback);

    void sendNetRegister(String phone, String pwd, String nextPwd, String verify, IRegisterCallback callback);
}
