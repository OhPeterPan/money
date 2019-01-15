package com.zrdb.app.ui.presenter;

import com.zrdb.app.ui.callback.IHosDetailCallback;
import com.zrdb.app.ui.model.modelImpl.HosDetailModelImpl;
import com.zrdb.app.ui.viewImpl.IHosDetailView;

public class HosDetailPresenter extends BasePresenter<IHosDetailView> implements IHosDetailCallback {

    private final HosDetailModelImpl model;

    public HosDetailPresenter(IHosDetailView view) {
        super(view);
        model = new HosDetailModelImpl();
    }

    public void sendNetHosDetail(String token, String uid, String hosId) {
        if (mView != null) mView.showLoading();
        if (model != null) model.sendNetHosDetail(token, uid, hosId, this);
    }

    public void sendNetHosDoc(String token, String uid, String hosId, String tecId, String secId, int curPage, boolean showDialog) {
        if (mView != null && showDialog) mView.showLoading();
        if (model != null)
            model.sendNetHosDetail(token, uid, hosId, tecId, secId, String.valueOf(curPage), this);
    }

    @Override
    public void getHosDetail(String result) {
        if (!checkResultError(result)) {
            mView.getHosDetailSuccess(result);
        }
    }

    @Override
    public void getHosDocResult(String result) {
        if (!checkResultError(result)) {
            mView.getHosDocResultSuccess(result);
        } else {
            if (mView != null)
                mView.showDataErrInfo(result);
        }
    }
}
