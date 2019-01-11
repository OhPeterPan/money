package com.zrdb.director.ui.viewImpl;

import com.zrdb.director.view.IView;

public interface ILoginView extends IView<String> {
    void getLoginResult(String result);
}
