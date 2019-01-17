package com.zrdb.app.ui.model;

import com.zrdb.app.ui.callback.IOrderCallback;

public interface IOrderModel {
    void sendNetGetOrder(String token, String uid, String status, IOrderCallback callback);

    void sendNetGetOrder(String token, String uid, String type, String subId, IOrderCallback callback);
}
