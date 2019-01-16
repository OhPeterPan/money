package com.zrdb.app.ui.presenter;

import com.zrdb.app.ui.callback.IMeMeanCallback;
import com.zrdb.app.ui.model.modelImpl.MeMeanModelImpl;
import com.zrdb.app.ui.viewImpl.IMeMeanModelView;

public class MeMeanPresenter extends BasePresenter<IMeMeanModelView> implements IMeMeanCallback {

    private final MeMeanModelImpl model;

    public MeMeanPresenter(IMeMeanModelView view) {
        super(view);
        model = new MeMeanModelImpl();
    }

    public void sendNet(String token, String uid) {
        if (mView != null) mView.showLoading();
        if (model != null) model.sendNetPersonInfo(token, uid, this);
    }

    @Override
    public void getMeInfo(String result) {
        if (!checkResultError(result)) {
            mView.getMeInfoSuccess(result);
        }
    }
}
