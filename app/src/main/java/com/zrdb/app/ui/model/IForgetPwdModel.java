package com.zrdb.app.ui.model;

import com.zrdb.app.ui.callback.IForgetPwdCallback;

public interface IForgetPwdModel {
    void sendNetGetVerify(String phone, IForgetPwdCallback callback);

    void sendNetChangePwd(String phone, String pwd, String code, IForgetPwdCallback callback);
}
