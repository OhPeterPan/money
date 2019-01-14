package com.zrdb.app.ui.viewImpl;

import com.zrdb.app.view.IView;

public interface IMultipleResultView extends IView<String> {
    void getMultipleResultSuccess(String result);
}
