package com.zrdb.app.ui.callback;

public interface IMessageOnLineCallback extends ICallback {
    void getMessageResult(String result);

    void sendMessage(String result);
}
