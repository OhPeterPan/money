package com.zrdb.app.ui.model;

import com.zrdb.app.ui.callback.IMeBespokeCallback;

public interface IMeBespokeModel {
    void sendNetBespoke(String token, String uid, IMeBespokeCallback callback);
}
