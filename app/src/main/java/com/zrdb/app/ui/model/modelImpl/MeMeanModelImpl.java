package com.zrdb.app.ui.model.modelImpl;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.zrdb.app.netcallback.AppStringCallback;
import com.zrdb.app.ui.callback.IMeMeanCallback;
import com.zrdb.app.ui.model.IMeMeanModel;
import com.zrdb.app.util.ApiUtils;
import com.zrdb.app.util.EncryptUtil;
import com.zrdb.app.util.TimeUtil;

public class MeMeanModelImpl implements IMeMeanModel {
    @Override
    public void sendNetPersonInfo(String token, String uid, final IMeMeanCallback callback) {
        String url = ApiUtils.Config.getDimen() + ApiUtils.PERSON_INFO_INDEX_URL + EncryptUtil.getMD5("Userindex"
                + TimeUtil.date2String(TimeUtil.getNowDate(), TimeUtil.YEAR_MONTH_DAY)
                + ApiUtils.clientSecret);
        OkGo.<String>post(url)
                .params("token", token)
                .params("uid", uid)
                .execute(new AppStringCallback(callback) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        callback.getMeInfo(response.body());
                    }
                });
    }
}
