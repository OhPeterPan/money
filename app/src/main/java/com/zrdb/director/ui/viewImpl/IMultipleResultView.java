package com.zrdb.director.ui.viewImpl;

import com.zrdb.director.view.IView;

public interface IMultipleResultView extends IView<String> {
    void getMultipleResultSuccess(String result);
}
