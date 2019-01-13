package com.zrdb.director.ui.viewImpl;

import com.zrdb.director.view.IView;

public interface IMainMessageView extends IView<String> {
    void getMessageList(String result);

    void ensureState(String result);
}
