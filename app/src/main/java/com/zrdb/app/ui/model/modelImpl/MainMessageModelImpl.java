package com.zrdb.app.ui.model.modelImpl;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.zrdb.app.netcallback.AppStringCallback;
import com.zrdb.app.ui.callback.IMainMessageCallback;
import com.zrdb.app.ui.model.IMainMessageModel;
import com.zrdb.app.util.ApiUtils;
import com.zrdb.app.util.EncryptUtil;
import com.zrdb.app.util.TimeUtil;

public class MainMessageModelImpl implements IMainMessageModel {
    @Override
    public void sendNetMessage(String token, String uid, final IMainMessageCallback callback) {
        String url = ApiUtils.Config.getDimen() + ApiUtils.MESSAGE_URL + EncryptUtil.getMD5("Indexmessage"
                + TimeUtil.date2String(TimeUtil.getNowDate(), TimeUtil.YEAR_MONTH_DAY)
                + ApiUtils.clientSecret);
        OkGo.<String>post(url)
                .params("token", token)
                .params("uid", uid)
                .execute(new AppStringCallback(callback) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        callback.getMessageList(response.body());
                    }
                });
    }

    @Override
    public void sendNetEnsureState(String token, String uid, final IMainMessageCallback callback) {
        String url = ApiUtils.Config.getDimen() + ApiUtils.ENSURE_STATE_URL + EncryptUtil.getMD5("Cardstatus"
                + TimeUtil.date2String(TimeUtil.getNowDate(), TimeUtil.YEAR_MONTH_DAY)
                + ApiUtils.clientSecret);
        OkGo.<String>post(url)
                .params("token", token)
                .params("uid", uid)
                .execute(new AppStringCallback(callback) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        callback.ensureState(response.body());
                    }
                });
    }
}
