package com.zrdb.app.ui.model;

import com.zrdb.app.ui.callback.IBespokeDetailCallback;

public interface IBespokeDetailModel {
    void sendNetBespokeDetail(String token, String uid, String subId, String type, IBespokeDetailCallback callback);
}
