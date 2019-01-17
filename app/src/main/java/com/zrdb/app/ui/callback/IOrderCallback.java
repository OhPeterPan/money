package com.zrdb.app.ui.callback;

public interface IOrderCallback extends ICallback {
    void getOrder(String result);

    void orderDelete(String result);
}
