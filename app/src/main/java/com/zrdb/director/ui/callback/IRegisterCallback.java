package com.zrdb.director.ui.callback;

public interface IRegisterCallback extends ICallback {
    void getVerify(String result);

    void register(String result);
}
