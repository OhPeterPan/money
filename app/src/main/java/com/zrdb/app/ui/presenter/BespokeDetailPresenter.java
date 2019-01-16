package com.zrdb.app.ui.presenter;

import com.zrdb.app.ui.callback.IBespokeDetailCallback;
import com.zrdb.app.ui.model.modelImpl.BespokeDetailModelImpl;
import com.zrdb.app.ui.viewImpl.IBespokeDetailView;

public class BespokeDetailPresenter extends BasePresenter<IBespokeDetailView> implements IBespokeDetailCallback {

    private final BespokeDetailModelImpl model;

    public BespokeDetailPresenter(IBespokeDetailView view) {
        super(view);
        model = new BespokeDetailModelImpl();
    }

    public void sendNet(String token, String uid, String subId, String type) {
        if (mView != null)
            mView.showLoading();
        if (model != null)
            model.sendNetBespokeDetail(token, uid, subId, type, this);
    }

    @Override
    public void bespokeDetail(String result) {
        if (!checkResultError(result)) {
            mView.bespokeDetailSuccess(result);
        }
    }
}
