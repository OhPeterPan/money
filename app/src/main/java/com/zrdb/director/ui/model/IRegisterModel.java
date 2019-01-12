package com.zrdb.director.ui.model;

import com.zrdb.director.ui.callback.IRegisterCallback;

public interface IRegisterModel {
    void sendNetGetVerify(String phone, IRegisterCallback callback);

    void sendNetRegister(String phone, String pwd, String nextPwd, String verify, IRegisterCallback callback);
}
