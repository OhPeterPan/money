package com.zrdb.director.ui.callback;

public interface IMainMessageCallback extends ICallback {
    void getMessageList(String result);

    void ensureState(String result);
}
