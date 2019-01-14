package com.zrdb.app.ui.model;

import com.zrdb.app.ui.callback.IMainCallback;

public interface IMainModel {
    void sendNetGetMainInfo(String token, String u_id, IMainCallback callback);
}
