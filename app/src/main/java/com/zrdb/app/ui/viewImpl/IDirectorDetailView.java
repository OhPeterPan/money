package com.zrdb.app.ui.viewImpl;

import com.zrdb.app.view.IView;

public interface IDirectorDetailView extends IView<String> {
    void getDocListResultSuccess(String result);

    void getDocFilterSuccess(String result);
}
