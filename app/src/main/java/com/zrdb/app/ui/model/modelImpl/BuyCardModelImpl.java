package com.zrdb.app.ui.model.modelImpl;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.zrdb.app.netcallback.AppStringCallback;
import com.zrdb.app.ui.callback.IBuyCardCallback;
import com.zrdb.app.ui.model.IBuyCardModel;
import com.zrdb.app.util.ApiUtils;
import com.zrdb.app.util.EncryptUtil;
import com.zrdb.app.util.TimeUtil;

public class BuyCardModelImpl implements IBuyCardModel {
    @Override
    public void sendNetBuyEnsureCard(String token, String uid, final IBuyCardCallback callback) {
        String url = ApiUtils.Config.getDimen() + ApiUtils.BUY_ENSURE_CARD_URL + EncryptUtil.getMD5("Cardbuy"
                + TimeUtil.date2String(TimeUtil.getNowDate(), TimeUtil.YEAR_MONTH_DAY)
                + ApiUtils.clientSecret);
        OkGo.<String>post(url)
                .params("token", token)
                .params("uid", uid)
                .execute(new AppStringCallback(callback) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        callback.getEnsureCardOrder(response.body());
                    }
                });
    }
}
