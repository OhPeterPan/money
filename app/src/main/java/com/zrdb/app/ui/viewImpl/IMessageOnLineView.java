package com.zrdb.app.ui.viewImpl;

import com.zrdb.app.view.IView;

public interface IMessageOnLineView extends IView<String> {
    void getMessageResultSuccess(String result);

    void sendMessageSuccess(String result);
}
