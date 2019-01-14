package com.zrdb.app.ui.model.modelImpl;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.zrdb.app.netcallback.AppStringCallback;
import com.zrdb.app.ui.callback.IIntelligentCallback;
import com.zrdb.app.ui.model.IIntelligentModel;
import com.zrdb.app.util.ApiUtils;
import com.zrdb.app.util.EncryptUtil;
import com.zrdb.app.util.TimeUtil;

import java.util.List;

public class IntelligentModelImpl implements IIntelligentModel {
    @Override
    public void sendNetUploadPic(String token, String uid, List<HttpParams.FileWrapper> file, final IIntelligentCallback callback) {
        String url = ApiUtils.Config.getDimen() + ApiUtils.UPLOAD_PIC + EncryptUtil.getMD5("Uploadupload"
                + TimeUtil.date2String(TimeUtil.getNowDate(), TimeUtil.YEAR_MONTH_DAY)
                + ApiUtils.clientSecret);
        OkGo.<String>post(url)
                .isMultipart(true)
                .params("token", token)
                .params("uid", uid)
                .addFileWrapperParams("picture", file)
                .execute(new AppStringCallback(callback) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        callback.uploadPic(response.body());
                    }
                });
    }

    @Override
    public void sendNetSubmitInfo(String token, String uid, String name,
                                  String phone, String city, String human,
                                  String tag, String details, List<String> pictures,
                                  final IIntelligentCallback callback) {
        String url = ApiUtils.Config.getDimen() + ApiUtils.SUBMIT_INTEL_INFO + EncryptUtil.getMD5("Indexintelligent"
                + TimeUtil.date2String(TimeUtil.getNowDate(), TimeUtil.YEAR_MONTH_DAY)
                + ApiUtils.clientSecret);
        OkGo.<String>post(url)
                .params("token", token)
                .params("uid", uid)
                .params("name", name)
                .params("phone", phone)
                .params("city", city)
                .params("human", human)
                .params("tag", tag)
                .params("details", details)
                .addUrlParams("picture", pictures)
                .execute(new AppStringCallback(callback) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        callback.submitPageResult(response.body());
                    }
                });
    }
}
