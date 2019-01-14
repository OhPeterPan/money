package com.zrdb.app.ui.viewImpl;

import com.zrdb.app.view.IView;

public interface IMainSearchView extends IView<String> {
    void getSearchPageInfoSuccess(String result);
}
