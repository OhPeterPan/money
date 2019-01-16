package com.zrdb.app.ui.model;

import com.zrdb.app.ui.callback.IUserInfoCallback;

import java.io.File;

public interface IUserInfoModel {
    void sendNetUserInfo(String token, String uid, IUserInfoCallback callback);

    void sendNetUploadPicture(String token, String uid, File picture, IUserInfoCallback callback);

    void sendNetChangeUserInfo(String token, String uid, String name, String picture, String gender, String address, IUserInfoCallback callback);
}
