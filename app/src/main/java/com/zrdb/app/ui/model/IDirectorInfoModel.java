package com.zrdb.app.ui.model;

import com.zrdb.app.ui.callback.IDirectorInfoCallback;

public interface IDirectorInfoModel {
    void sendNetDocInfo(String token, String uid, String docId, IDirectorInfoCallback callback);
}
