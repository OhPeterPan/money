package com.zrdb.app.ui.viewImpl;

import com.zrdb.app.view.IView;

public interface IMeBespokeView extends IView<String> {
    void getBespokeListSuccess(String result);
}
