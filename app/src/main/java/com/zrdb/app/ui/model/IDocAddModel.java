package com.zrdb.app.ui.model;

import com.zrdb.app.ui.callback.IDocAddCallback;

import java.io.File;

public interface IDocAddModel {
    void sendNetAddDoc(String token, String uid, String realname,
                       String phone, String hospital,
                       String section, String tec_id, String picture,
                       IDocAddCallback callback);

    void sendNetDocJob(String token, String uid, IDocAddCallback callback);

    void sendNetUploadPic(String token, String uid, File picture, IDocAddCallback callback);
}
