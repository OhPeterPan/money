package com.zrdb.app.ui.presenter;

import com.zrdb.app.ui.callback.ISearchHosCallback;
import com.zrdb.app.ui.model.modelImpl.SearchHosModelImpl;
import com.zrdb.app.ui.viewImpl.ISearchHosView;

public class SearchHosPresenter extends BasePresenter<ISearchHosView> implements ISearchHosCallback {

    private SearchHosModelImpl model;

    public SearchHosPresenter(ISearchHosView view) {
        super(view);
        model = new SearchHosModelImpl();
    }

    public void sendNetHosInfo(String token, String uid, String keyword,
                               String cityId, String cateId, int page,
                               String row, boolean showDialog) {
        if (model != null)
            model.sendNetGetHos(token, uid, keyword, cityId, cateId, String.valueOf(page), row, this);
    }

    public void sendNetDocFilter(String token, String uid) {
        if (model != null)
            model.sendNetFilterHos(token, uid, this);
    }

    @Override
    public void getHosResult(String result) {
        if (!checkResultError(result)) {
            mView.getHosResultSuccess(result);
        } else {
            if (mView != null)
                mView.showDataErrInfo(result);
        }
    }

    @Override
    public void getFilterResult(String result) {
        if (!checkResultError(result)) {
            mView.getFilterResultSuccess(result);
        }
    }


}
