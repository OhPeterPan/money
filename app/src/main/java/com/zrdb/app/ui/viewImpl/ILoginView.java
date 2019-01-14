package com.zrdb.app.ui.viewImpl;

import com.zrdb.app.view.IView;

public interface ILoginView extends IView<String> {
    void getLoginResult(String result);
}
