package com.zrdb.app.ui.presenter;

import com.zrdb.app.ui.callback.IDocAddCallback;
import com.zrdb.app.ui.model.modelImpl.DocAddModelImpl;
import com.zrdb.app.ui.viewImpl.IDocAddView;

import java.io.File;

public class DocAddPresenter extends BasePresenter<IDocAddView> implements IDocAddCallback {

    private final DocAddModelImpl model;

    public DocAddPresenter(IDocAddView view) {
        super(view);
        model = new DocAddModelImpl();
    }

    public void sendNetUploadPic(String token, String uid, File file) {
        if (mView != null)
            mView.showLoading();
        if (model != null)
            model.sendNetUploadPic(token, uid, file, this);
    }

    @Override
    public void uploadPicture(String result) {
        if (!checkResultError(result)) {
            mView.uploadPictureSuccess(result);
        }
    }

    @Override
    public void docJobInfo(String result) {
        if (!checkResultError(result)) {
            mView.docJobInfoSuccess(result);
        }
    }

    @Override
    public void addDocResult(String result) {
        if (!checkResultError(result)) {
            mView.addDocResultSuccess(result);
        }
    }
}
