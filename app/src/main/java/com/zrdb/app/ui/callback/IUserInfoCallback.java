package com.zrdb.app.ui.callback;

public interface IUserInfoCallback extends ICallback {
    void getUserInfo(String result);

    void uploadPicture(String result);

    void changeUserInfo(String result);
}
