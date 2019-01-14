package com.zrdb.app.ui.viewImpl;

import com.zrdb.app.view.IView;

public interface IRegisterView extends IView<String> {
    void getVerifySuccess(String result);

    void registerSuccess(String result);
}
