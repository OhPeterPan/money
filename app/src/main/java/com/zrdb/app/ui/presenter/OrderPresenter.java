package com.zrdb.app.ui.presenter;

import com.zrdb.app.ui.callback.IOrderCallback;
import com.zrdb.app.ui.model.modelImpl.OrderModelImpl;
import com.zrdb.app.ui.viewImpl.IOrderModelView;

public class OrderPresenter extends BasePresenter<IOrderModelView> implements IOrderCallback {

    private final OrderModelImpl model;

    public OrderPresenter(IOrderModelView view) {
        super(view);
        model = new OrderModelImpl();
    }

    public void sendNetGetOrder(String token, String uid, String status) {
        if (mView != null)
            mView.showLoading();
        if (model != null)
            model.sendNetGetOrder(token, uid, status, this);
    }

    @Override
    public void getOrder(String result) {
        if (!checkResultError(result)) {
            mView.getOrderSuccess(result);
        } else {
            if (mView != null)
                mView.showDataErrInfo(result);
        }
    }

    @Override
    public void orderDelete(String result) {
        if (!checkResultError(result)) {
            mView.orderDeleteSuccess(result);
        }
    }
}
