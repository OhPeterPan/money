package com.zrdb.app.ui.model.modelImpl;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.zrdb.app.netcallback.AppStringCallback;
import com.zrdb.app.ui.callback.ILookHosIndexCallback;
import com.zrdb.app.ui.model.ILookHosIndexModel;
import com.zrdb.app.util.ApiUtils;
import com.zrdb.app.util.EncryptUtil;
import com.zrdb.app.util.TimeUtil;

public class LookHosIndexModelImpl implements ILookHosIndexModel {
    @Override
    public void sendNetGetHosListResult(String token, String uid, String areaId, String levId, String page, final ILookHosIndexCallback callback) {
        String url = ApiUtils.Config.getDimen() + ApiUtils.LOOK_HOS_URL + EncryptUtil.getMD5("Hospitalgetlist"
                + TimeUtil.date2String(TimeUtil.getNowDate(), TimeUtil.YEAR_MONTH_DAY)
                + ApiUtils.clientSecret);
        OkGo.<String>post(url)
                .params("token", token)
                .params("uid", uid)
                .params("areaId", areaId)
                .params("lev_id", levId)
                .params("page", page)
                .params("count", "10")
                .execute(new AppStringCallback(callback) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        callback.getHosListResult(response.body());
                    }
                });
    }

    @Override
    public void sendNetHosFilter(String token, String uid, final ILookHosIndexCallback callback) {
        String url = ApiUtils.Config.getDimen() + ApiUtils.LOOK_HOS_FILTER_URL + EncryptUtil.getMD5("Hospitalfilter"
                + TimeUtil.date2String(TimeUtil.getNowDate(), TimeUtil.YEAR_MONTH_DAY)
                + ApiUtils.clientSecret);
        OkGo.<String>post(url)
                .params("token", token)
                .params("uid", uid)
                .execute(new AppStringCallback(callback) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        callback.hosFilterResult(response.body());
                    }
                });
    }
}
