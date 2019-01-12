package com.zrdb.director.ui.callback;

public interface IForgetPwdCallback extends ICallback {
    void getVerifyResult(String result);

    void changePwdResult(String result);
}
