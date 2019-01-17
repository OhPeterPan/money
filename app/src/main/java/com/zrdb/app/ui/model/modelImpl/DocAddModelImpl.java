package com.zrdb.app.ui.model.modelImpl;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.zrdb.app.netcallback.AppStringCallback;
import com.zrdb.app.ui.callback.IDocAddCallback;
import com.zrdb.app.ui.model.IDocAddModel;
import com.zrdb.app.util.ApiUtils;
import com.zrdb.app.util.EncryptUtil;
import com.zrdb.app.util.TimeUtil;

import java.io.File;

public class DocAddModelImpl implements IDocAddModel {
    @Override
    public void sendNetAddDoc(String token, String uid, String realname,
                              String phone, String hospital, String section,
                              String tec_id, String picture, final IDocAddCallback callback) {
        String url = ApiUtils.Config.getDimen() + ApiUtils.ADD_DOC_URL + EncryptUtil.getMD5("Indexdoctor_send"
                + TimeUtil.date2String(TimeUtil.getNowDate(), TimeUtil.YEAR_MONTH_DAY)
                + ApiUtils.clientSecret);
        OkGo.<String>post(url)
                .params("token", token)
                .params("uid", uid)
                .params("realname", realname)
                .params("phone", phone)
                .params("hospital", hospital)
                .params("section", section)
                .params("tec_id", tec_id)
                .params("picture", picture)
                .execute(new AppStringCallback(callback) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        callback.addDocResult(response.body());
                    }
                });
    }

    @Override
    public void sendNetDocJob(String token, String uid, final IDocAddCallback callback) {
        String url = ApiUtils.Config.getDimen() + ApiUtils.DOC_JOB_URL + EncryptUtil.getMD5("Indexdoctor_type"
                + TimeUtil.date2String(TimeUtil.getNowDate(), TimeUtil.YEAR_MONTH_DAY)
                + ApiUtils.clientSecret);
        OkGo.<String>post(url)
                .params("token", token)
                .params("uid", uid)
                .execute(new AppStringCallback(callback) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        callback.docJobInfo(response.body());
                    }
                });

    }

    @Override
    public void sendNetUploadPic(String token, String uid, File picture, final IDocAddCallback callback) {
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
}
