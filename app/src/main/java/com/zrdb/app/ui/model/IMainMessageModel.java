package com.zrdb.app.ui.model;

import com.zrdb.app.ui.callback.IMainMessageCallback;

public interface IMainMessageModel {
    void sendNetMessage(String token, String uid, IMainMessageCallback callback);

    void sendNetEnsureState(String token, String uid, IMainMessageCallback callback);
}
