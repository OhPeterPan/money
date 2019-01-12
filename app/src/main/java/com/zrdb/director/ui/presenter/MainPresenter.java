package com.zrdb.director.ui.presenter;

import com.zrdb.director.ui.callback.IMainCallback;
import com.zrdb.director.ui.model.modelImpl.MainModelImpl;
import com.zrdb.director.ui.viewImpl.IMainView;

public class MainPresenter extends BasePresenter<IMainView> implements IMainCallback {

    private MainModelImpl model;

    public MainPresenter(IMainView view) {
        super(view);
        model = new MainModelImpl();
    }

    public void sendNet(String token, String uid) {
        if (mView != null) mView.showLoading();
        if (model != null) model.sendNetGetMainInfo(token, uid, this);
    }

    @Override
    public void getMainInfo(String info) {
        if (!checkResultError(info)) {
            mView.getMainInfoSuccess(info);
        }
    }
}
