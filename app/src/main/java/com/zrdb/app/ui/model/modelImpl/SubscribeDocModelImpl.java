package com.zrdb.app.ui.model.modelImpl;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.zrdb.app.netcallback.AppStringCallback;
import com.zrdb.app.ui.callback.ISubscribeDocCallback;
import com.zrdb.app.ui.model.ISubscribeDocModel;
import com.zrdb.app.util.ApiUtils;
import com.zrdb.app.util.EncryptUtil;
import com.zrdb.app.util.TimeUtil;

public class SubscribeDocModelImpl implements ISubscribeDocModel {
    @Override
    public void sendNetSubDocInfo(String token, String uid, String docId, final ISubscribeDocCallback callback) {
        String url = ApiUtils.Config.getDimen() + ApiUtils.SUBSCRIBE_DOC_INFO_URL + EncryptUtil.getMD5("Doctorsubscribe"
                + TimeUtil.date2String(TimeUtil.getNowDate(), TimeUtil.YEAR_MONTH_DAY)
                + ApiUtils.clientSecret);
        OkGo.<String>get(url)
                .params("token", token)
                .params("uid", uid)
                .params("doc_id", docId)
                .execute(new AppStringCallback(callback) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        callback.subDocInfo(response.body());
                    }
                });
    }

    @Override
    public void sendNetSubmitSubPersonInfo(String token, String uid, String docId, String name, String phone, String disease, final ISubscribeDocCallback callback) {
        String url = ApiUtils.Config.getDimen() + ApiUtils.SUBSCRIBE_PERSON_INFO_URL + EncryptUtil.getMD5("Doctorsubscribe_send"
                + TimeUtil.date2String(TimeUtil.getNowDate(), TimeUtil.YEAR_MONTH_DAY)
                + ApiUtils.clientSecret);
        OkGo.<String>post(url)
                .params("token", token)
                .params("uid", uid)
                .params("doc_id", docId)
                .params("name", name)
                .params("phone", phone)
                .params("disease", disease)
                .execute(new AppStringCallback(callback) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        callback.submitSubPersonResult(response.body());
                    }
                });
    }
}
