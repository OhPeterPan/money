package com.zrdb.app.ui.presenter;

import com.zrdb.app.ui.callback.IMainCallback;
import com.zrdb.app.ui.model.modelImpl.MainModelImpl;
import com.zrdb.app.ui.viewImpl.IMainView;

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

    public void sendNetCardState(String token, String uid) {
        if (mView != null)
            mView.showLoading();
        if (model != null)
            model.sendNetCardState(token, uid, this);
    }

    @Override
    public void getMainInfo(String info) {
        if (!checkResultError(info)) {
            mView.getMainInfoSuccess(info);
        }
    }

    @Override
    public void getCardState(String result) {
        if (!checkResultError(result)) {
            mView.getCardStateSuccess(result);
        }
    }
}
