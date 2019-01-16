package com.zrdb.app.ui.presenter;

import android.widget.Toast;

import com.blankj.utilcode.util.StringUtils;
import com.zrdb.app.ui.callback.IUserInfoCallback;
import com.zrdb.app.ui.model.modelImpl.UserInfoModelImpl;
import com.zrdb.app.ui.viewImpl.IUserInfoView;
import com.zrdb.app.util.ToastUtil;

import java.io.File;

public class UserInfoPresenter extends BasePresenter<IUserInfoView> implements IUserInfoCallback {

    private final UserInfoModelImpl model;

    public UserInfoPresenter(IUserInfoView view) {
        super(view);
        model = new UserInfoModelImpl();
    }

    public boolean checkInfo(String name, String gender, String address) {
        if (StringUtils.isEmpty(name)) {
            ToastUtil.showMessage("请输入昵称", Toast.LENGTH_SHORT);
            return false;
        }
        if (StringUtils.isEmpty(gender)) {
            ToastUtil.showMessage("请选择性别", Toast.LENGTH_SHORT);
            return false;
        }
        if (StringUtils.isEmpty(address)) {
            ToastUtil.showMessage("请输入地址", Toast.LENGTH_SHORT);
            return false;
        }
        return true;
    }

    public void sendNet(String token, String uid) {
        if (mView != null)
            mView.showLoading();
        if (model != null)
            model.sendNetUserInfo(token, uid, this);
    }

    public void sendNetUploadPic(String token, String uid, File file) {
        if (mView != null)
            mView.showLoading();
        if (model != null)
            model.sendNetUploadPicture(token, uid, file, this);
    }

    public void sendNetSaveInfo(String token, String uid, String name, String picture, String gender, String address) {
        if (mView != null)
            mView.showLoading();
        if (model != null)
            model.sendNetChangeUserInfo(token, uid, name, picture, gender, address, this);
    }

    @Override
    public void getUserInfo(String result) {
        if (!checkResultError(result)) {
            mView.getUserInfoSuccess(result);
        }
    }

    @Override
    public void uploadPicture(String result) {
        if (!checkResultError(result)) {
            mView.uploadPictureSuccess(result);
        }
    }

    @Override
    public void changeUserInfo(String result) {
        if (!checkResultError(result)) {
            mView.changeUserInfoSuccess(result);
        }
    }
}
