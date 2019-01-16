package com.zrdb.app.ui.model.modelImpl;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.zrdb.app.netcallback.AppStringCallback;
import com.zrdb.app.ui.callback.IBespokeDetailCallback;
import com.zrdb.app.ui.model.IBespokeDetailModel;
import com.zrdb.app.util.ApiUtils;
import com.zrdb.app.util.EncryptUtil;
import com.zrdb.app.util.TimeUtil;

public class BespokeDetailModelImpl implements IBespokeDetailModel {
    @Override
    public void sendNetBespokeDetail(String token, String uid, String subId, String type, final IBespokeDetailCallback callback) {
        String url = ApiUtils.Config.getDimen() + ApiUtils.GET_BESPOKE_DETAIL_URL + EncryptUtil.getMD5("Subscribelistget_details"
                + TimeUtil.date2String(TimeUtil.getNowDate(), TimeUtil.YEAR_MONTH_DAY)
                + ApiUtils.clientSecret);
        OkGo.<String>post(url)
                .params("token", token)
                .params("uid", uid)
                .params("sub_id", subId)
                .params("type", type)
                .execute(new AppStringCallback(callback) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        callback.bespokeDetail(response.body());
                    }
                });
    }
}
