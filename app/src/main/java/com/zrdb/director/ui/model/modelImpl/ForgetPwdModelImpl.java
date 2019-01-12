package com.zrdb.director.ui.model.modelImpl;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.zrdb.director.netcallback.AppStringCallback;
import com.zrdb.director.ui.callback.IForgetPwdCallback;
import com.zrdb.director.ui.model.IForgetPwdModel;
import com.zrdb.director.util.ApiUtils;
import com.zrdb.director.util.EncryptUtil;
import com.zrdb.director.util.TimeUtil;

public class ForgetPwdModelImpl implements IForgetPwdModel {
    @Override
    public void sendNetGetVerify(String phone, final IForgetPwdCallback callback) {
        String url = ApiUtils.Config.getDimen() + ApiUtils.GET_VERIFY_URL + EncryptUtil.getMD5("Smssendsms"
                + TimeUtil.date2String(TimeUtil.getNowDate(), TimeUtil.YEAR_MONTH_DAY)
                + ApiUtils.clientSecret);
        OkGo.<String>post(url)
                .params("phone", phone)
                .execute(new AppStringCallback(callback) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        callback.getVerifyResult(response.body());
                    }
                });
    }

    @Override
    public void sendNetChangePwd(String phone, String pwd, String code, final IForgetPwdCallback callback) {
        String url = ApiUtils.Config.getDimen() + ApiUtils.CHANGE_PWD_URL + EncryptUtil.getMD5("Loginforgot"
                + TimeUtil.date2String(TimeUtil.getNowDate(), TimeUtil.YEAR_MONTH_DAY)
                + ApiUtils.clientSecret);
        OkGo.<String>post(url)
                .params("phone", phone)
                .params("password", pwd)
                .params("code", code)
                .execute(new AppStringCallback(callback) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        callback.changePwdResult(response.body());
                    }
                });
    }
}
