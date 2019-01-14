package com.zrdb.app.ui.model.modelImpl;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.zrdb.app.netcallback.AppStringCallback;
import com.zrdb.app.ui.callback.IDirectorInfoCallback;
import com.zrdb.app.ui.model.IDirectorInfoModel;
import com.zrdb.app.util.ApiUtils;
import com.zrdb.app.util.EncryptUtil;
import com.zrdb.app.util.TimeUtil;

public class DirectorInfoModelImpl implements IDirectorInfoModel {
    @Override
    public void sendNetDocInfo(String token, String uid, String docId, final IDirectorInfoCallback callback) {
        String url = ApiUtils.Config.getDimen() + ApiUtils.LOOK_DOC_DETAIL_URL + EncryptUtil.getMD5("Doctordetails"
                + TimeUtil.date2String(TimeUtil.getNowDate(), TimeUtil.YEAR_MONTH_DAY)
                + ApiUtils.clientSecret);
        OkGo.<String>get(url)
                .params("token", token)
                .params("uid", uid)
                .params("doc_id", docId)
                .execute(new AppStringCallback(callback) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        callback.getDocInfo(response.body());
                    }
                });
    }
}
