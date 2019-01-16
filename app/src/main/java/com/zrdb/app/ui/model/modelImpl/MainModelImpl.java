package com.zrdb.app.ui.model.modelImpl;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.zrdb.app.netcallback.AppStringCallback;
import com.zrdb.app.ui.callback.IMainCallback;
import com.zrdb.app.ui.model.IMainModel;
import com.zrdb.app.util.ApiUtils;
import com.zrdb.app.util.EncryptUtil;
import com.zrdb.app.util.TimeUtil;

public class MainModelImpl implements IMainModel {
    @Override
    public void sendNetGetMainInfo(String token, String u_id, final IMainCallback callback) {
        String url = ApiUtils.Config.getDimen() + ApiUtils.MAIN_INDEX_URL + EncryptUtil.getMD5("Indexindex"
                + TimeUtil.date2String(TimeUtil.getNowDate(), TimeUtil.YEAR_MONTH_DAY)
                + ApiUtils.clientSecret);
        OkGo.<String>get(url)
                .params("token", token)
                .params("uid", u_id)
                .execute(new AppStringCallback(callback) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        callback.getMainInfo(response.body());
                    }
                });
    }

    @Override
    public void sendNetCardState(String token, String uid, final IMainCallback callback) {
        String url = ApiUtils.Config.getDimen() + ApiUtils.ENSURE_STATE_URL + EncryptUtil.getMD5("Cardstatus"
                + TimeUtil.date2String(TimeUtil.getNowDate(), TimeUtil.YEAR_MONTH_DAY)
                + ApiUtils.clientSecret);
        OkGo.<String>post(url)
                .params("token", token)
                .params("uid", uid)
                .execute(new AppStringCallback(callback) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        callback.getCardState(response.body());
                    }
                });
    }
}
