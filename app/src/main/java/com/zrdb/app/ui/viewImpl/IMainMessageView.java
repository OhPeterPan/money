package com.zrdb.app.ui.viewImpl;

import com.zrdb.app.view.IView;

public interface IMainMessageView extends IView<String> {
    void getMessageList(String result);

    void ensureState(String result);
}
