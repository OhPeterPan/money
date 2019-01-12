package com.zrdb.director.ui.presenter;

import com.zrdb.director.ui.callback.IMainSearchCallback;
import com.zrdb.director.ui.model.modelImpl.MainSearchModelImpl;
import com.zrdb.director.ui.viewImpl.IMainSearchView;

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
