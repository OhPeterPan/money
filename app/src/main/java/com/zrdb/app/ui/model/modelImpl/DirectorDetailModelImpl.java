package com.zrdb.app.ui.model.modelImpl;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.zrdb.app.netcallback.AppStringCallback;
import com.zrdb.app.ui.callback.IDirectorDetailCallback;
import com.zrdb.app.ui.model.IDirectorDetailModel;
import com.zrdb.app.util.ApiUtils;
import com.zrdb.app.util.EncryptUtil;
import com.zrdb.app.util.TimeUtil;

public class DirectorDetailModelImpl implements IDirectorDetailModel {
    @Override
    public void sendNetGetDocList(String token, String uid, String secId,
                                  String disId, String areaId, String tecId, String cateId,
                                  String levId, String curPage, final IDirectorDetailCallback callback) {
        String url = ApiUtils.Config.getDimen() + ApiUtils.GET_DOC_LIST_URL + EncryptUtil.getMD5("Doctorgetlist"
                + TimeUtil.date2String(TimeUtil.getNowDate(), TimeUtil.YEAR_MONTH_DAY)
                + ApiUtils.clientSecret);
        OkGo.<String>post(url)
                .params("token", token)
                .params("uid", uid)
                .params("sec_id", secId)
                .params("dis_id", disId)
                .params("areaId", areaId)
                .params("tec_id", tecId)
                .params("cate_id", cateId)
                .params("lev_id", levId)
                .params("page", curPage)
                .params("count", "10")
                .execute(new AppStringCallback(callback) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        callback.getDocListResult(response.body());
                    }
                });
    }

    @Override
    public void sendNetFilter(String token, String uid, String secId, final IDirectorDetailCallback callback) {
        String url = ApiUtils.Config.getDimen() + ApiUtils.GET_DOC_FILTER_URL + EncryptUtil.getMD5("Doctorfilter"
                + TimeUtil.date2String(TimeUtil.getNowDate(), TimeUtil.YEAR_MONTH_DAY)
                + ApiUtils.clientSecret);
        OkGo.<String>get(url)
                .params("token", token)
                .params("uid", uid)
                .params("sec_id", secId)
                .execute(new AppStringCallback(callback) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        callback.getDocFilter(response.body());
                    }
                });
    }
}
