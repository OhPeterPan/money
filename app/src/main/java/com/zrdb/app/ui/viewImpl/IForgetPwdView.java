package com.zrdb.app.ui.viewImpl;

import com.zrdb.app.view.IView;

public interface IForgetPwdView extends IView<String> {
    void getVerifyResultSuccess(String result);

    void changePwdResultSuccess(String result);
}
