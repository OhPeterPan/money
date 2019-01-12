package com.zrdb.director.ui.viewImpl;

import com.zrdb.director.view.IView;

public interface IMainSearchView extends IView<String> {
    void getSearchPageInfoSuccess(String result);
}
