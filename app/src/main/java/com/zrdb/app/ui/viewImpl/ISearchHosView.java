package com.zrdb.app.ui.viewImpl;

import com.zrdb.app.view.IView;

public interface ISearchHosView extends IView<String> {
    void getHosResultSuccess(String result);

    void getFilterResultSuccess(String result);
}
