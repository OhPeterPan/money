package com.zrdb.app.ui.model.modelImpl;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.zrdb.app.netcallback.AppStringCallback;
import com.zrdb.app.ui.callback.IUserInfoCallback;
import com.zrdb.app.ui.model.IUserInfoModel;
import com.zrdb.app.util.ApiUtils;
import com.zrdb.app.util.EncryptUtil;
import com.zrdb.app.util.TimeUtil;

import java.io.File;

public class UserInfoModelImpl implements IUserInfoModel {
    @Override
    public void sendNetUserInfo(String token, String uid, final IUserInfoCallback callback) {
        String url = ApiUtils.Config.getDimen() + ApiUtils.USER_INFO_URL + EncryptUtil.getMD5("Useruserinfo"
                + TimeUtil.date2String(TimeUtil.getNowDate(), TimeUtil.YEAR_MONTH_DAY)
                + ApiUtils.clientSecret);
        OkGo.<String>post(url)
                .params("token", token)
                .params("uid", uid)
                .execute(new AppStringCallback(callback) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        callback.getUserInfo(response.body());
                    }
                });
    }

    @Override
    public void sendNetUploadPicture(String token, String uid, File picture, final IUserInfoCallback callback) {
        String url = ApiUtils.Config.getDimen() + ApiUtils.UPLOAD_PIC + EncryptUtil.getMD5("Uploadupload"
                + TimeUtil.date2String(TimeUtil.getNowDate(), TimeUtil.YEAR_MONTH_DAY)
                + ApiUtils.clientSecret);
        OkGo.<String>post(url)
                .params("token", token)
                .params("uid", uid)
                .params("picture", picture)
                .execute(new AppStringCallback(callback) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        callback.uploadPicture(response.body());
                    }
                });
    }

    @Override
    public void sendNetChangeUserInfo(String token, String uid, String name, String picture, String gender, String address, final IUserInfoCallback callback) {
        String url = ApiUtils.Config.getDimen() + ApiUtils.CHANGE_USER_INFO_URL + EncryptUtil.getMD5("Usereditinfo"
                + TimeUtil.date2String(TimeUtil.getNowDate(), TimeUtil.YEAR_MONTH_DAY)
                + ApiUtils.clientSecret);
        OkGo.<String>post(url)
                .params("token", token)
                .params("uid", uid)
                .params("username", name)
                .params("thumb", picture)
                .params("sex", gender)
                .params("city", address)
                .execute(new AppStringCallback(callback) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        callback.changeUserInfo(response.body());
                    }
                });
    }
}
