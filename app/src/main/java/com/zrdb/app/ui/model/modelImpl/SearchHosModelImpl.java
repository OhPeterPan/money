package com.zrdb.app.ui.model.modelImpl;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.zrdb.app.netcallback.AppStringCallback;
import com.zrdb.app.ui.callback.ISearchHosCallback;
import com.zrdb.app.ui.model.ISearchHosModel;
import com.zrdb.app.util.ApiUtils;
import com.zrdb.app.util.EncryptUtil;
import com.zrdb.app.util.TimeUtil;

public class SearchHosModelImpl implements ISearchHosModel {
    @Override
    public void sendNetGetHos(String token, String uid, String keyword, String cityId, String cateId, String curPage, String page, final ISearchHosCallback callback) {
        String url = ApiUtils.Config.getDimen() + ApiUtils.FILTER_HOS_URL + EncryptUtil.getMD5("Indexsearch_hospital"
                + TimeUtil.date2String(TimeUtil.getNowDate(), TimeUtil.YEAR_MONTH_DAY)
                + ApiUtils.clientSecret);
        OkGo.<String>post(url)
                .params("token", token)
                .params("uid", uid)
                .params("keywords", keyword)
                .params("city_id", cityId)
                .params("cate_id", cateId)
                .params("page", curPage)
                .params("count", page)
                .execute(new AppStringCallback(callback) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        callback.getHosResult(response.body());
                    }
                });
    }

    @Override
    public void sendNetFilterHos(String token, String uid, final ISearchHosCallback callback) {
        String url = ApiUtils.Config.getDimen() + ApiUtils.FILTER_HOS_INFO_URL + EncryptUtil.getMD5("Indexsearch_hospital_filter"
                + TimeUtil.date2String(TimeUtil.getNowDate(), TimeUtil.YEAR_MONTH_DAY)
                + ApiUtils.clientSecret);
        OkGo.<String>post(url)
                .params("token", token)
                .params("uid", uid)
                .execute(new AppStringCallback(callback) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        callback.getFilterResult(response.body());
                    }
                });
    }
}
