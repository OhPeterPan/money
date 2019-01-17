package com.zrdb.app.ui.model;

import com.zrdb.app.ui.callback.IOrderBuyCallback;

public interface IOrderBuyModel {
    void sendNetGetEnsureInfo(String token, String uid, IOrderBuyCallback callback);

    void sendNetGetPayInfo(String token, String uid, String type, String orderId, IOrderBuyCallback callback);
}
