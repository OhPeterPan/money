package com.zrdb.app.ui.viewImpl;

import com.zrdb.app.view.IView;

public interface IMainView extends IView<String> {
    void getMainInfoSuccess(String info);

    void getCardStateSuccess(String result);
}
