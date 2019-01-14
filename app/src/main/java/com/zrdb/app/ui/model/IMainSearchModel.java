package com.zrdb.app.ui.model;

import com.zrdb.app.ui.callback.IMainSearchCallback;

public interface IMainSearchModel {
    void sendNetSearchInfo(String token, String u_id, IMainSearchCallback callback);
}
