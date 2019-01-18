package com.zrdb.app.ui.model.modelImpl;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.zrdb.app.netcallback.AppStringCallback;
import com.zrdb.app.ui.callback.IMessageOnLineCallback;
import com.zrdb.app.ui.model.IMessageOnLineModel;
import com.zrdb.app.util.ApiUtils;
import com.zrdb.app.util.EncryptUtil;
import com.zrdb.app.util.TimeUtil;

public class MessageOnLineModelImpl implements IMessageOnLineModel {
    @Override
    public void sendNetGetMessage(String token, String uid, final IMessageOnLineCallback callback) {
        String url = ApiUtils.Config.getDimen() + ApiUtils.MESSAGE_ON_LINE_URL + EncryptUtil.getMD5("Chatindex"
                + TimeUtil.date2String(TimeUtil.getNowDate(), TimeUtil.YEAR_MONTH_DAY)
                + ApiUtils.clientSecret);
        OkGo.<String>post(url)
                .params("token", token)
                .params("uid", uid)
                .execute(new AppStringCallback(callback) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        callback.getMessageResult(response.body());
                    }
                });
    }

    @Override
    public void sendNetSendMessage(String token, String uid, String content, final IMessageOnLineCallback callback) {
        String url = ApiUtils.Config.getDimen() + ApiUtils.SEND_MESSAGE_URL + EncryptUtil.getMD5("Chatsendmessage"
                + TimeUtil.date2String(TimeUtil.getNowDate(), TimeUtil.YEAR_MONTH_DAY)
                + ApiUtils.clientSecret);
        OkGo.<String>post(url)
                .params("token", token)
                .params("uid", uid)
                .params("content", content)
                .execute(new AppStringCallback(callback) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        callback.sendMessage(response.body());
                    }
                });
    }
}
