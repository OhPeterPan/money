package com.zrdb.app.ui.presenter;

import com.zrdb.app.ui.callback.IBuyCardCallback;
import com.zrdb.app.ui.model.modelImpl.BuyCardModelImpl;
import com.zrdb.app.ui.viewImpl.IBuyCardView;

public class BuyCardPresenter extends BasePresenter<IBuyCardView> implements IBuyCardCallback {

    private final BuyCardModelImpl model;

    public BuyCardPresenter(IBuyCardView view) {
        super(view);
        model = new BuyCardModelImpl();
    }

    public void sendNetBuyEnsureCard(String token, String uid) {
        if (mView != null)
            mView.showLoading();
        if (model != null)
            model.sendNetBuyEnsureCard(token, uid, this);
    }

    @Override
    public void getEnsureCardOrder(String result) {
        if (!checkResultError(result)) {
            mView.getEnsureCardOrderSuccess(result);
        }
    }
}
