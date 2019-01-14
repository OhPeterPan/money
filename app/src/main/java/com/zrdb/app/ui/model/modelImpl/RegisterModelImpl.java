package com.zrdb.app.ui.model.modelImpl;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.zrdb.app.netcallback.AppStringCallback;
import com.zrdb.app.ui.callback.IRegisterCallback;
import com.zrdb.app.ui.model.IRegisterModel;
import com.zrdb.app.util.ApiUtils;
import com.zrdb.app.util.EncryptUtil;
import com.zrdb.app.util.TimeUtil;

public class RegisterModelImpl implements IRegisterModel {
    @Override
    public void sendNetGetVerify(String phone, final IRegisterCallback callback) {
        String url = ApiUtils.Config.getDimen() + ApiUtils.GET_VERIFY_URL + EncryptUtil.getMD5("Smssendsms"
                + TimeUtil.date2String(TimeUtil.getNowDate(), TimeUtil.YEAR_MONTH_DAY)
                + ApiUtils.clientSecret);
        OkGo.<String>post(url)
                .params("phone", phone)
                .execute(new AppStringCallback(callback) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        callback.getVerify(response.body());
                    }
                });
    }

    @Override
    public void sendNetRegister(String phone, String pwd, String nextPwd, String verify, final IRegisterCallback callback) {
        String url = ApiUtils.Config.getDimen() + ApiUtils.REGISTER_URL + EncryptUtil.getMD5("Loginreg"
                + TimeUtil.date2String(TimeUtil.getNowDate(), TimeUtil.YEAR_MONTH_DAY)
                + ApiUtils.clientSecret);
        OkGo.<String>post(url)
                .params("phone", phone)
                .params("password", pwd)
                .params("repass", nextPwd)
                .params("code", verify)
                .execute(new AppStringCallback(callback) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        callback.register(response.body());
                    }
                });
    }
}
