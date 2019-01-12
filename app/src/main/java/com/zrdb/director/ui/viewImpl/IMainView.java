package com.zrdb.director.ui.viewImpl;

import com.zrdb.director.view.IView;

public interface IMainView extends IView<String> {
    void getMainInfoSuccess(String info);
}
