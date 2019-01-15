package com.zrdb.app.ui.viewImpl;

import com.zrdb.app.view.IView;

public interface ISubscribeDocView extends IView {
    void subDocInfoSuccess(String result);

    void submitSubPersonSuccess(String result);
}
