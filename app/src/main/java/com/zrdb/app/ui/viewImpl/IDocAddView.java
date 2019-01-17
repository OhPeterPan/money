package com.zrdb.app.ui.viewImpl;

import com.zrdb.app.view.IView;

public interface IDocAddView extends IView<String> {
    void uploadPictureSuccess(String result);

    void docJobInfoSuccess(String result);

    void addDocResultSuccess(String result);
}
