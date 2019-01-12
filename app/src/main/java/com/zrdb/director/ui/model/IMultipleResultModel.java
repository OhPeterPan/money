package com.zrdb.director.ui.model;

import com.zrdb.director.ui.callback.IMultipleResultCallback;

public interface IMultipleResultModel {
    void sendNetMultiple(String token, String uid, String keyword, IMultipleResultCallback callback);
}
