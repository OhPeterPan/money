package com.zrdb.app.ui.presenter;

import com.zrdb.app.ui.callback.IMultipleResultCallback;
import com.zrdb.app.ui.model.modelImpl.MultipleResultModelImpl;
import com.zrdb.app.ui.viewImpl.IMultipleResultView;

public class MultipleResultPresenter extends BasePresenter<IMultipleResultView> implements IMultipleResultCallback {

    private final MultipleResultModelImpl model;

    public MultipleResultPresenter(IMultipleResultView view) {
        super(view);
        model = new MultipleResultModelImpl();
    }

    public void sendNet(String token, String uid, String keyword) {
        if (model != null) model.sendNetMultiple(token, uid, keyword, this);
    }

    @Override
    public void getMultipleResult(String result) {
        if (!checkResultError(result)) {
            mView.getMultipleResultSuccess(result);
        }
    }
}
