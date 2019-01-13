package com.zrdb.director.ui.model.modelImpl;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.zrdb.director.netcallback.AppStringCallback;
import com.zrdb.director.ui.callback.ISearchDocCallback;
import com.zrdb.director.ui.model.ISearchDocModel;
import com.zrdb.director.util.ApiUtils;
import com.zrdb.director.util.EncryptUtil;
import com.zrdb.director.util.TimeUtil;

public class SearchDocModelImpl implements ISearchDocModel {
    @Override
    public void sendNetGetDoc(String token, String uid,
                              String keyword, String cityId,
                              String tecId, String levId, String cateId, String curPage,
                              String page, final ISearchDocCallback callback) {
        String url = ApiUtils.Config.getDimen() + ApiUtils.FILTER_DOC_URL + EncryptUtil.getMD5("Indexsearch_doctor"
                + TimeUtil.date2String(TimeUtil.getNowDate(), TimeUtil.YEAR_MONTH_DAY)
                + ApiUtils.clientSecret);
        OkGo.<String>post(url)
                .params("token", token)
                .params("uid", uid)
                .params("keywords", keyword)
                .params("city_id", cityId)
                .params("tec_id", tecId)
                .params("lev_id", levId)
                .params("cate_id", cateId)
                .params("page", curPage)
                .params("count", page)
                .execute(new AppStringCallback(callback) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        callback.getDocResult(response.body());
                    }
                });
    }

    @Override
    public void sendNetFilterDoc(String token, String uid, final ISearchDocCallback callback) {
        String url = ApiUtils.Config.getDimen() + ApiUtils.FILTER_DOC_INFO_URL + EncryptUtil.getMD5("Indexsearch_doctor_filter"
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
