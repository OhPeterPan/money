package com.zrdb.app.ui.model.modelImpl;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.zrdb.app.netcallback.AppStringCallback;
import com.zrdb.app.ui.callback.IOrderCallback;
import com.zrdb.app.ui.model.IOrderModel;
import com.zrdb.app.util.ApiUtils;
import com.zrdb.app.util.EncryptUtil;
import com.zrdb.app.util.TimeUtil;

public class OrderModelImpl implements IOrderModel {
    @Override
    public void sendNetGetOrder(String token, String uid, String status, final IOrderCallback callback) {
        String url = ApiUtils.Config.getDimen() + ApiUtils.ORDER_LIST_URL + EncryptUtil.getMD5("Orderindex"
                + TimeUtil.date2String(TimeUtil.getNowDate(), TimeUtil.YEAR_MONTH_DAY)
                + ApiUtils.clientSecret);
        OkGo.<String>post(url)
                .params("token", token)
                .params("uid", uid)
                .params("status", status)
                .execute(new AppStringCallback(callback) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        callback.getOrder(response.body());
                    }
                });
    }

    @Override
    public void sendNetGetOrder(String token, String uid, String type, String subId, final IOrderCallback callback) {
        String url = ApiUtils.Config.getDimen() + ApiUtils.ORDER_DEL_URL + EncryptUtil.getMD5("Orderdelete"
                + TimeUtil.date2String(TimeUtil.getNowDate(), TimeUtil.YEAR_MONTH_DAY)
                + ApiUtils.clientSecret);
        OkGo.<String>post(url)
                .params("token", token)
                .params("uid", uid)
                .params("type", type)
                .params("sub_id", subId)
                .execute(new AppStringCallback(callback) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        callback.orderDelete(response.body());
                    }
                });
    }
}
