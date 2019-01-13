package com.zrdb.director.ui.viewImpl;

import com.zrdb.director.view.IView;

public interface ISearchDocView extends IView<String> {
    void getDocResultSuccess(String result);

    void getFilterResultSuccess(String result);
}
