package com.zrdb.app.ui.callback;

public interface ISearchDocCallback extends ICallback {
    void getDocResult(String result);

    void getFilterResult(String result);
}
