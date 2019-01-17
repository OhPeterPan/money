package com.zrdb.app.ui.viewImpl;

import com.zrdb.app.view.IView;

public interface IOrderModelView extends IView<String> {
    void getOrderSuccess(String result);

    void orderDeleteSuccess(String result);
}
