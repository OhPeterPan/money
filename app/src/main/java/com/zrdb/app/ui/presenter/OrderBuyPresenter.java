package com.zrdb.app.ui.presenter;

import com.zrdb.app.ui.callback.IOrderBuyCallback;
import com.zrdb.app.ui.model.modelImpl.OrderBuyModelImpl;
import com.zrdb.app.ui.viewImpl.IOrderBuyView;

public class OrderBuyPresenter extends BasePresenter<IOrderBuyView> implements IOrderBuyCallback {

    private final OrderBuyModelImpl model;

    public OrderBuyPresenter(IOrderBuyView view) {
        super(view);
        model = new OrderBuyModelImpl();
    }

    public void sendNet(String token, String uid) {
        if (mView != null)
            mView.showLoading();
        if (model != null)
            model.sendNetGetEnsureInfo(token, uid, this);
    }

    @Override
    public void getEnsureInfo(String result) {
        if (!checkResultError(result)) {
            mView.getEnsureInfoSuccess(result);
        }
    }

    @Override
    public void getPayInfo(String result) {
        if (!checkResultError(result)) {
            mView.getPayInfoSuccess(result);
        }
    }
}
