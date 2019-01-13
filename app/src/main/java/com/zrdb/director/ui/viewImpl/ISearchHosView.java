package com.zrdb.director.ui.viewImpl;

import com.zrdb.director.view.IView;

public interface ISearchHosView extends IView<String> {
    void getHosResultSuccess(String result);

    void getFilterResultSuccess(String result);
}
