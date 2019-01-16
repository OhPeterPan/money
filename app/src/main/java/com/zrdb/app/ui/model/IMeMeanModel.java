package com.zrdb.app.ui.model;

import com.zrdb.app.ui.callback.IMeMeanCallback;

public interface IMeMeanModel {
    void sendNetPersonInfo(String token, String uid, IMeMeanCallback callback);
}
