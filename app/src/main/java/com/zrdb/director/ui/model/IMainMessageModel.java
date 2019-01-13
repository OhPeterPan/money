package com.zrdb.director.ui.model;

import com.zrdb.director.ui.callback.IMainMessageCallback;

public interface IMainMessageModel {
    void sendNetMessage(String token, String uid, IMainMessageCallback callback);

    void sendNetEnsureState(String token, String uid, IMainMessageCallback callback);
}
