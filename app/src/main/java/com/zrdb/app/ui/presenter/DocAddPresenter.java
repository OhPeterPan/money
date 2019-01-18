package com.zrdb.app.ui.presenter;

import android.widget.Toast;

import com.blankj.utilcode.util.StringUtils;
import com.zrdb.app.ui.callback.IDocAddCallback;
import com.zrdb.app.ui.model.modelImpl.DocAddModelImpl;
import com.zrdb.app.ui.viewImpl.IDocAddView;
import com.zrdb.app.util.ToastUtil;

import java.io.File;

public class DocAddPresenter extends BasePresenter<IDocAddView> implements IDocAddCallback {

    private final DocAddModelImpl model;

    public DocAddPresenter(IDocAddView view) {
        super(view);
        model = new DocAddModelImpl();
    }

    public boolean checkInfo(String name, String phone, String address, String offices, String tecId, String picture) {
        if (StringUtils.isEmpty(name)) {
            ToastUtil.showMessage("请输入姓名！", Toast.LENGTH_SHORT);
            return false;
        }
        if (StringUtils.isEmpty(phone)) {
            ToastUtil.showMessage("请输入电话号码！", Toast.LENGTH_SHORT);
            return false;
        }
        if (StringUtils.isEmpty(address)) {
            ToastUtil.showMessage("请输入工作单位！", Toast.LENGTH_SHORT);
            return false;
        }
        if (StringUtils.isEmpty(offices)) {
            ToastUtil.showMessage("请输入科室！", Toast.LENGTH_SHORT);
            return false;
        }
        if (StringUtils.isEmpty(tecId)) {
            ToastUtil.showMessage("请选择职称！", Toast.LENGTH_SHORT);
            return false;
        }
        if (StringUtils.isEmpty(picture)) {
            ToastUtil.showMessage("请上传头像！", Toast.LENGTH_SHORT);
            return false;
        }
        return true;
    }

    public void sendNetUploadPic(String token, String uid, File file) {
        if (mView != null)
            mView.showLoading();
        if (model != null)
            model.sendNetUploadPic(token, uid, file, this);
    }

    public void sendNetUploadDocInfo(String token, String uid, String name, String phone, String address, String offices, String tecId, String picture) {
        if (mView != null)
            mView.showLoading();
        if (model != null)
            model.sendNetAddDoc(token, uid, name, phone, address, offices, tecId, picture, this);
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

    public void sendNetGetDocJob(String token, String uid) {
        if (mView != null)
            mView.showLoading();
        if (model != null)
            model.sendNetDocJob(token, uid, this);
    }


}
