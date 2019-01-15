package com.zrdb.app.ui.viewImpl;

import com.zrdb.app.view.IView;

public interface ILookHosIndexView extends IView<String> {
    void getHosListResultSuccess(String result);

    void hosFilterResultSuccess(String result);
}
