package com.zrdb.app.ui.presenter;

import com.zrdb.app.ui.callback.ILookHosIndexCallback;
import com.zrdb.app.ui.model.modelImpl.LookHosIndexModelImpl;
import com.zrdb.app.ui.viewImpl.ILookHosIndexView;

public class LookHosIndexPresenter extends BasePresenter<ILookHosIndexView> implements ILookHosIndexCallback {

    private final LookHosIndexModelImpl model;

    public LookHosIndexPresenter(ILookHosIndexView view) {
        super(view);
        model = new LookHosIndexModelImpl();
    }

    public void sendNetHosInfo(String token, String uid, String areaId, String levId, int curPage, boolean showDialog) {
        if (mView != null && showDialog)
            mView.showLoading();
        if (model != null)
            model.sendNetGetHosListResult(token, uid, areaId, levId, String.valueOf(curPage), this);
    }

    @Override
    public void getHosListResult(String result) {
        if (!checkResultError(result)) {
            mView.getHosListResultSuccess(result);
        } else {
            if (mView != null) {
                mView.showDataErrInfo(result);
            }
        }
    }

    @Override
    public void hosFilterResult(String result) {
        if (!checkResultError(result)) {
            mView.hosFilterResultSuccess(result);
        }
    }
}
