package com.zrdb.app.ui.callback;

public interface IOrderBuyCallback extends ICallback {
    void getEnsureInfo(String result);

    void getPayInfo(String result);
}
