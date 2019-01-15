package com.zrdb.app.ui.model;

import com.zrdb.app.ui.callback.ISubscribeHosCallback;

public interface ISubscribeHosModel {
    void sendNetGetSunHosInfo(String token, String uid, String hosId, ISubscribeHosCallback callback);

    void sendNetSubmitPersonInfo(String token, String uid, String hosId,
                                  String name,
                                  String phone,
                                  String disease,
                                  String secName,
                                  ISubscribeHosCallback callback);
}
