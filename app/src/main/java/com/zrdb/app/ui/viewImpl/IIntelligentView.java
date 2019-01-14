package com.zrdb.app.ui.viewImpl;

import com.zrdb.app.view.IView;

public interface IIntelligentView extends IView<String> {
    void uploadPicSuccess(String result);

    void submitPageResultSuccess(String result);
}
