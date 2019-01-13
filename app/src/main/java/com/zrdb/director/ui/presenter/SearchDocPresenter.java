package com.zrdb.director.ui.presenter;

import com.zrdb.director.ui.callback.ISearchDocCallback;
import com.zrdb.director.ui.model.modelImpl.SearchDocModelImpl;
import com.zrdb.director.ui.viewImpl.ISearchDocView;

public class SearchDocPresenter extends BasePresenter<ISearchDocView> implements ISearchDocCallback {

    private final SearchDocModelImpl model;

    public SearchDocPresenter(ISearchDocView view) {
        super(view);
        model = new SearchDocModelImpl();
    }

    public void sendNetDocInfo(String token, String uid, String keyword,
                               String cityId, String tecId, String levId, String cateId,
                               int page, String row, boolean showDialog) {
        if (model != null)
            model.sendNetGetDoc(token, uid, keyword, cityId, tecId, levId, cateId, String.valueOf(page), row, this);
    }

    public void sendNetDocFilter(String token, String uid) {
        if (model != null)
            model.sendNetFilterDoc(token, uid, this);
    }

    @Override
    public void getDocResult(String result) {
        if (!checkResultError(result)) {
            mView.getDocResultSuccess(result);
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
