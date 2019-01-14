package com.zrdb.app.ui.model;

import com.zrdb.app.ui.callback.IMultipleResultCallback;

public interface IMultipleResultModel {
    void sendNetMultiple(String token, String uid, String keyword, IMultipleResultCallback callback);
}
