package com.zrdb.director.ui.model;

import com.zrdb.director.ui.callback.IForgetPwdCallback;

public interface IForgetPwdModel {
    void sendNetGetVerify(String phone, IForgetPwdCallback callback);

    void sendNetChangePwd(String phone, String pwd, String code, IForgetPwdCallback callback);
}
