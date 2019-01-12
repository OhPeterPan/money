package com.zrdb.director.ui.viewImpl;

import com.zrdb.director.view.IView;

public interface IForgetPwdView extends IView<String> {
    void getVerifyResultSuccess(String result);

    void changePwdResultSuccess(String result);
}
