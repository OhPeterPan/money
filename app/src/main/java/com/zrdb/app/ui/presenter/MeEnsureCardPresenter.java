package com.zrdb.app.ui.presenter;

import com.zrdb.app.ui.callback.IMeEnsureCardCallback;
import com.zrdb.app.ui.model.modelImpl.MeEnsureCardModelImpl;
import com.zrdb.app.ui.viewImpl.IMeEnsureCardView;

public class MeEnsureCardPresenter extends BasePresenter<IMeEnsureCardView> implements IMeEnsureCardCallback {

    private final MeEnsureCardModelImpl model;

    public MeEnsureCardPresenter(IMeEnsureCardView view) {
        super(view);
        model = new MeEnsureCardModelImpl();
    }

    public void sendNetMyEnsureCard(String token, String uid) {
        if (mView != null)
            mView.showLoading();
        if (model != null)
            model.sendNetGetEnsureCard(token, uid, this);
    }

    @Override
    public void getMyEnsureCard(String result) {
        if (!checkResultError(result)) {
            mView.getMyEnsureCardSuccess(result);
        }
    }
}
