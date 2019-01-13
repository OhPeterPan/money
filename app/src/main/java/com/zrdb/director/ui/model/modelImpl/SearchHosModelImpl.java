package com.zrdb.director.ui.model.modelImpl;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.zrdb.director.netcallback.AppStringCallback;
import com.zrdb.director.ui.callback.ISearchHosCallback;
import com.zrdb.director.ui.model.ISearchHosModel;
import com.zrdb.director.util.ApiUtils;
import com.zrdb.director.util.EncryptUtil;
import com.zrdb.director.util.TimeUtil;

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
