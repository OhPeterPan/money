package com.zrdb.app.ui.model;

import com.zrdb.app.ui.callback.IMessageOnLineCallback;

public interface IMessageOnLineModel {
    void sendNetGetMessage(String token, String uid, IMessageOnLineCallback callback);

    void sendNetSendMessage(String token, String uid, String content, IMessageOnLineCallback callback);
}
