package com.zrdb.app.ui.presenter;

import com.zrdb.app.ui.callback.IMainSearchCallback;
import com.zrdb.app.ui.model.modelImpl.MainSearchModelImpl;
import com.zrdb.app.ui.viewImpl.IMainSearchView;

public class MainSearchPresenter extends BasePresenter<IMainSearchView> implements IMainSearchCallback {

    private final MainSearchModelImpl model;

    public MainSearchPresenter(IMainSearchView view) {
        super(view);
        model = new MainSearchModelImpl();
    }

    public void sendNet(String token, String uid) {
        if (mView != null) mView.showLoading();
        if (model != null) model.sendNetSearchInfo(token, uid, this);
    }

    @Override
    public void getSearchPageInfo(String result) {
        if (!checkResultError(result)) {
            mView.getSearchPageInfoSuccess(result);
        }
    }
}
