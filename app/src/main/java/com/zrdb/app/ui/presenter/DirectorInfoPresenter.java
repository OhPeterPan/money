package com.zrdb.app.ui.presenter;

import com.zrdb.app.ui.callback.IDirectorInfoCallback;
import com.zrdb.app.ui.model.modelImpl.DirectorInfoModelImpl;
import com.zrdb.app.ui.viewImpl.IDirectorInfoView;

public class DirectorInfoPresenter extends BasePresenter<IDirectorInfoView> implements IDirectorInfoCallback {

    private DirectorInfoModelImpl model;

    public DirectorInfoPresenter(IDirectorInfoView view) {
        super(view);
        model = new DirectorInfoModelImpl();
    }

    public void sendNet(String token, String uid, String docId) {
        if (mView != null)
            mView.showLoading();
        if (model != null)
            model.sendNetDocInfo(token, uid, docId, this);
    }

    @Override
    public void getDocInfo(String result) {
        if (!checkResultError(result)) {
            mView.getDocInfoSuccess(result);
        }
    }
}
