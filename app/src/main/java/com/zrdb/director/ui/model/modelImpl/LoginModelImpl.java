package com.zrdb.director.ui.model.modelImpl;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.zrdb.director.ui.callback.ILoginCallback;
import com.zrdb.director.ui.model.ILoginModel;
import com.zrdb.director.util.ApiUtils;
import com.zrdb.director.util.EncryptUtil;
import com.zrdb.director.util.TimeUtil;

public class LoginModelImpl implements ILoginModel {
    @Override
    public void sendNetLogin(String phone, String pwd, final ILoginCallback callback) {
        String url = ApiUtils.Config.getDimen() + ApiUtils.LOGIN_URL + EncryptUtil.getMD5("Loginlogin"
                + TimeUtil.date2String(TimeUtil.getNowDate(), TimeUtil.YEAR_MONTH_DAY)
                + ApiUtils.clientSecret);
        OkGo.<String>post(url)
                .params("phone", phone)
                .params("password", pwd)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        // LogUtil.LogI(ApiUtils.LOGIN_URL);
                        callback.getLoginResult(response.body());
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        callback.error(response.getException());
                    }
                });
    }
}
