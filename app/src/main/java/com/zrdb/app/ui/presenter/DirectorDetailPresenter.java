package com.zrdb.app.ui.presenter;

import com.zrdb.app.ui.callback.IDirectorDetailCallback;
import com.zrdb.app.ui.model.modelImpl.DirectorDetailModelImpl;
import com.zrdb.app.ui.viewImpl.IDirectorDetailView;

public class DirectorDetailPresenter extends BasePresenter<IDirectorDetailView> implements IDirectorDetailCallback {

    private final DirectorDetailModelImpl model;

    public DirectorDetailPresenter(IDirectorDetailView view) {
        super(view);
        model = new DirectorDetailModelImpl();
    }

    public void sendNetGetDoc(String token, String uid, String secId, String disId,
                              String areaId, String tecId, String cateId, String levId,
                              int curPage, boolean showDialog) {
        if (mView != null && showDialog) mView.showLoading();
        if (model != null)
            model.sendNetGetDocList(token, uid, secId, disId, areaId, tecId, cateId, levId, String.valueOf(curPage), this);
    }

    public void sendNetDocFilter(String token, String uid, String secId) {
        if (mView != null) mView.showLoading();
        if (model != null) model.sendNetFilter(token, uid, secId, this);
    }

    @Override
    public void getDocListResult(String result) {
        if (!checkResultError(result)) {
            mView.getDocListResultSuccess(result);
        } else {
            if (mView != null)
                mView.showDataErrInfo(result);
        }
    }

    @Override
    public void getDocFilter(String result) {
        if (!checkResultError(result)) {
            mView.getDocFilterSuccess(result);
        }
    }


}
