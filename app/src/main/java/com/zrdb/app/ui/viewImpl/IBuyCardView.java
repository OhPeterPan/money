package com.zrdb.app.ui.viewImpl;

import com.zrdb.app.view.IView;

public interface IBuyCardView extends IView<String> {
    void getEnsureCardOrderSuccess(String result);
}
