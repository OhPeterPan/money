package com.zrdb.app.ui.model.modelImpl;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.zrdb.app.netcallback.AppStringCallback;
import com.zrdb.app.ui.callback.IHosDetailCallback;
import com.zrdb.app.ui.model.IHosDetailModel;
import com.zrdb.app.util.ApiUtils;
import com.zrdb.app.util.EncryptUtil;
import com.zrdb.app.util.TimeUtil;

public class HosDetailModelImpl implements IHosDetailModel {
    @Override
    public void sendNetHosDetail(String token, String uid, String hosId, final IHosDetailCallback callback) {
        String url = ApiUtils.Config.getDimen() + ApiUtils.GET_HOS_DETAIL_URL + EncryptUtil.getMD5("Hospitaldetails"
                + TimeUtil.date2String(TimeUtil.getNowDate(), TimeUtil.YEAR_MONTH_DAY)
                + ApiUtils.clientSecret);
        OkGo.<String>post(url)
                .params("token", token)
                .params("uid", uid)
                .params("hos_id", hosId)
                .execute(new AppStringCallback(callback) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        callback.getHosDetail(response.body());
                    }
                });

    }

    @Override
    public void sendNetHosDetail(String token, String uid, String hosId, String tecId, String secId, String cuPage, final IHosDetailCallback callback) {
        String url = ApiUtils.Config.getDimen() + ApiUtils.GET_HOS_DOC_URL + EncryptUtil.getMD5("Hospitaldoctorlist"
                + TimeUtil.date2String(TimeUtil.getNowDate(), TimeUtil.YEAR_MONTH_DAY)
                + ApiUtils.clientSecret);
        OkGo.<String>post(url)
                .params("token", token)
                .params("uid", uid)
                .params("hos_id", hosId)
                .params("tec_id", tecId)
                .params("sec_id", secId)
                .params("page", cuPage)
                .params("count", "10")
                .execute(new AppStringCallback(callback) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        callback.getHosDocResult(response.body());
                    }
                });
    }

    @Override
    public void sendNetHosFilter(String token, String uid, IHosDetailCallback callback) {

    }
}
