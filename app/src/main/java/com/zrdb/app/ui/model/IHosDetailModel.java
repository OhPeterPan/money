package com.zrdb.app.ui.model;

import com.zrdb.app.ui.callback.IHosDetailCallback;

public interface IHosDetailModel {
    void sendNetHosDetail(String token, String uid, String hosId, IHosDetailCallback callback);

    void sendNetHosDetail(String token, String uid, String hosId, String tecId, String secId, String cuPage, IHosDetailCallback callback);

    void sendNetHosFilter(String token, String uid, IHosDetailCallback callback);
}
