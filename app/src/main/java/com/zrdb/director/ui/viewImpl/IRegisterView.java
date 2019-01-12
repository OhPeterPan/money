package com.zrdb.director.ui.viewImpl;

import com.zrdb.director.view.IView;

public interface IRegisterView extends IView<String> {
    void getVerifySuccess(String result);

    void registerSuccess(String result);
}
