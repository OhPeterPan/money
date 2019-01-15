package com.zrdb.app.ui.viewImpl;

import com.zrdb.app.view.IView;

public interface IHosDetailView extends IView<String> {
    void getHosDetailSuccess(String result);

    void getHosDocResultSuccess(String result);

}
