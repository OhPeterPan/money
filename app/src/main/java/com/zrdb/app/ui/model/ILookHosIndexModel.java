package com.zrdb.app.ui.model;

import com.zrdb.app.ui.callback.ILookHosIndexCallback;

public interface ILookHosIndexModel {
    void sendNetGetHosListResult(String token, String uid, String areaId, String levId, String page, ILookHosIndexCallback callback);

    void sendNetHosFilter(String token, String uid, ILookHosIndexCallback callback);
}
