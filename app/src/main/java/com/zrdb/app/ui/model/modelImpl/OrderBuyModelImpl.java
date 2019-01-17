package com.zrdb.app.ui.model.modelImpl;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.zrdb.app.netcallback.AppStringCallback;
import com.zrdb.app.ui.callback.IOrderBuyCallback;
import com.zrdb.app.ui.model.IOrderBuyModel;
import com.zrdb.app.util.ApiUtils;
import com.zrdb.app.util.EncryptUtil;
import com.zrdb.app.util.TimeUtil;

public class OrderBuyModelImpl implements IOrderBuyModel {
    @Override
    public void sendNetGetEnsureInfo(String token, String uid, final IOrderBuyCallback callback) {
        String url = ApiUtils.Config.getDimen() + ApiUtils.PAY_ENSURE_INFO_URL + EncryptUtil.getMD5("Paycard_pay"
                + TimeUtil.date2String(TimeUtil.getNowDate(), TimeUtil.YEAR_MONTH_DAY)
                + ApiUtils.clientSecret);
        OkGo.<String>post(url)
                .params("token", token)
                .params("uid", uid)
                .execute(new AppStringCallback(callback) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        callback.getEnsureInfo(response.body());
                    }
                });
    }

    @Override
    public void sendNetGetPayInfo(String token, String uid, String type, String orderId, final IOrderBuyCallback callback) {
        String url = ApiUtils.Config.getDimen() + ApiUtils.PAY_INFO_URL + EncryptUtil.getMD5("Payindex"
                + TimeUtil.date2String(TimeUtil.getNowDate(), TimeUtil.YEAR_MONTH_DAY)
                + ApiUtils.clientSecret);
        OkGo.<String>post(url)
                .params("token", token)
                .params("uid", uid)
                .params("type", type)
                .params("order_id", orderId)
                .execute(new AppStringCallback(callback) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        callback.getPayInfo(response.body());
                    }
                });
    }
}
