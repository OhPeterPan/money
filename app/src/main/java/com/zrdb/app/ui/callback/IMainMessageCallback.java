package com.zrdb.app.ui.callback;

public interface IMainMessageCallback extends ICallback {
    void getMessageList(String result);

    void ensureState(String result);
}
