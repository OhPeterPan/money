package com.zrdb.app.ui.viewImpl;

import com.zrdb.app.view.IView;

public interface ISearchDocView extends IView<String> {
    void getDocResultSuccess(String result);

    void getFilterResultSuccess(String result);
}
