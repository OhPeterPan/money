package com.zrdb.app.ui.model;

import com.zrdb.app.ui.callback.ISubscribeDocCallback;

public interface ISubscribeDocModel {
    void sendNetSubDocInfo(String token, String uid, String docId, ISubscribeDocCallback callback);

    void sendNetSubmitSubPersonInfo(String token, String uid, String docId, String name, String phone, String disease, ISubscribeDocCallback callback);
}
