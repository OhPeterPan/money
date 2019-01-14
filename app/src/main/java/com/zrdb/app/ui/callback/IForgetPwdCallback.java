package com.zrdb.app.ui.callback;

public interface IForgetPwdCallback extends ICallback {
    void getVerifyResult(String result);

    void changePwdResult(String result);
}
