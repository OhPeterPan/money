package com.zrdb.director.ui.model.modelImpl;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.zrdb.director.netcallback.AppStringCallback;
import com.zrdb.director.ui.callback.IMainSearchCallback;
import com.zrdb.director.ui.model.IMainSearchModel;
import com.zrdb.director.util.ApiUtils;
import com.zrdb.director.util.EncryptUtil;
import com.zrdb.director.util.TimeUtil;

public class MainSearchModelImpl implements IMainSearchModel {
    @Override
    public void sendNetSearchInfo(String token, String u_id, final IMainSearchCallback callback) {
        String url = ApiUtils.Config.getDimen() + ApiUtils.SEARCH_INFO_URL + EncryptUtil.getMD5("Indexsearch_log"
                + TimeUtil.date2String(TimeUtil.getNowDate(), TimeUtil.YEAR_MONTH_DAY)
                + ApiUtils.clientSecret);
        OkGo.<String>get(url)
                .params("token", token)
                .params("uid", u_id)
                .execute(new AppStringCallback(callback) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        callback.getSearchPageInfo(response.body());
                    }
                });
    }
}
