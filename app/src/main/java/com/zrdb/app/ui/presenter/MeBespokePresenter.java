package com.zrdb.app.ui.presenter;

import com.zrdb.app.ui.callback.IMeBespokeCallback;
import com.zrdb.app.ui.model.modelImpl.MeBespokeModelImpl;
import com.zrdb.app.ui.viewImpl.IMeBespokeView;

public class MeBespokePresenter extends BasePresenter<IMeBespokeView> implements IMeBespokeCallback {

    private final MeBespokeModelImpl model;

    public MeBespokePresenter(IMeBespokeView view) {
        super(view);
        model = new MeBespokeModelImpl();
    }

    public void sendNet(String token, String uid) {
        if (mView != null)
            mView.showLoading();
        if (model != null)
            model.sendNetBespoke(token, uid, this);
    }

    @Override
    public void getBespokeList(String result) {
        if (!checkResultError(result)) {
            mView.getBespokeListSuccess(result);
        } else {
            if (mView != null)
                mView.showDataErrInfo(result);
        }
    }
}
