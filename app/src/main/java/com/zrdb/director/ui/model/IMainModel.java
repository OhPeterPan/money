package com.zrdb.director.ui.model;

import com.zrdb.director.ui.callback.IMainCallback;

public interface IMainModel {
    void sendNetGetMainInfo(String token, String u_id, IMainCallback callback);
}
