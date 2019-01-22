package com.zrdb.app.ui.viewImpl;

import com.zrdb.app.view.IView;

public interface IMeMeanModelView extends IView<String> {
    void getMeInfoSuccess(String result);

    void getCardStateSuccess(String result);
}
