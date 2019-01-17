package com.zrdb.app.ui.viewImpl;

import com.zrdb.app.view.IView;

public interface IOrderBuyView extends IView<String> {
    void getEnsureInfoSuccess(String result);

    void getPayInfoSuccess(String result);
}
