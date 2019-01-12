package com.zrdb.director.ui.model;

import com.zrdb.director.ui.callback.IMainSearchCallback;

public interface IMainSearchModel {
    void sendNetSearchInfo(String token, String u_id, IMainSearchCallback callback);
}
