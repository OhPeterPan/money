package com.zrdb.app.ui.viewImpl;

import com.zrdb.app.view.IView;

public interface ISubscribeHosView extends IView<String> {
    void getSubHosInfoSuccess(String result);

    void SubmitPersonInfoSuccess(String result);
}
