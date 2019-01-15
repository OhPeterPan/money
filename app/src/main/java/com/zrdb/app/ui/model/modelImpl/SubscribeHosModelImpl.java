package com.zrdb.app.ui.model.modelImpl;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.zrdb.app.netcallback.AppStringCallback;
import com.zrdb.app.ui.callback.ISubscribeHosCallback;
import com.zrdb.app.ui.model.ISubscribeHosModel;
import com.zrdb.app.util.ApiUtils;
import com.zrdb.app.util.EncryptUtil;
import com.zrdb.app.util.TimeUtil;

public class SubscribeHosModelImpl implements ISubscribeHosModel {
    @Override
    public void sendNetGetSunHosInfo(String token, String uid, String hosId, final ISubscribeHosCallback callback) {
        String url = ApiUtils.Config.getDimen() + ApiUtils.SUBSCRIBE_HOS_INFO_URL + EncryptUtil.getMD5("Hospitalsubscribe"
                + TimeUtil.date2String(TimeUtil.getNowDate(), TimeUtil.YEAR_MONTH_DAY)
                + ApiUtils.clientSecret);
        OkGo.<String>post(url)
                .params("token", token)
                .params("uid", uid)
                .params("hos_id", hosId)
                .execute(new AppStringCallback(callback) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        callback.getSubHosInfo(response.body());
                    }
                });
    }

    @Override
    public void sendNetSubmitPersonInfo(String token, String uid, String hosId, String name,
                                        String phone, String disease, String secName,
                                        final ISubscribeHosCallback callback) {
        String url = ApiUtils.Config.getDimen() + ApiUtils.SUBSCRIBE_HOS_PERSON_INFO_URL + EncryptUtil.getMD5("Hospitalsubscribe_send"
                + TimeUtil.date2String(TimeUtil.getNowDate(), TimeUtil.YEAR_MONTH_DAY)
                + ApiUtils.clientSecret);
        OkGo.<String>post(url)
                .params("token", token)
                .params("uid", uid)
                .params("hos_id", hosId)
                .params("name", name)
                .params("phone", phone)
                .params("disease", disease)
                .params("sec_name", secName)
                .execute(new AppStringCallback(callback) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        callback.SubmitPersonInfo(response.body());
                    }
                });
    }
}
