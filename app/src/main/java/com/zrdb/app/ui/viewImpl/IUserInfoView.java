package com.zrdb.app.ui.viewImpl;

import com.zrdb.app.view.IView;

public interface IUserInfoView extends IView {
    void getUserInfoSuccess(String result);

    void uploadPictureSuccess(String result);

    void changeUserInfoSuccess(String result);
}
