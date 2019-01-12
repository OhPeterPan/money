package com.zrdb.director.ui.model.modelImpl;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.zrdb.director.netcallback.AppStringCallback;
import com.zrdb.director.ui.callback.IMainCallback;
import com.zrdb.director.ui.model.IMainModel;
import com.zrdb.director.util.ApiUtils;
import com.zrdb.director.util.EncryptUtil;
import com.zrdb.director.util.TimeUtil;

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
}
