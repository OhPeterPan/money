package com.zrdb.app.ui.model.modelImpl;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.zrdb.app.netcallback.AppStringCallback;
import com.zrdb.app.ui.callback.IMeEnsureCardCallback;
import com.zrdb.app.ui.model.IMeEnsureCardModel;
import com.zrdb.app.util.ApiUtils;
import com.zrdb.app.util.EncryptUtil;
import com.zrdb.app.util.TimeUtil;

public class MeEnsureCardModelImpl implements IMeEnsureCardModel {
    @Override
    public void sendNetGetEnsureCard(String token, String uid, final IMeEnsureCardCallback callback) {
        String url = ApiUtils.Config.getDimen() + ApiUtils.ME_ENSURE_CARD_URL + EncryptUtil.getMD5("Cardmycard"
                + TimeUtil.date2String(TimeUtil.getNowDate(), TimeUtil.YEAR_MONTH_DAY)
                + ApiUtils.clientSecret);
        OkGo.<String>post(url)
                .params("token", token)
                .params("uid", uid)
                .execute(new AppStringCallback(callback) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        callback.getMyEnsureCard(response.body());
                    }
                });
    }
}
