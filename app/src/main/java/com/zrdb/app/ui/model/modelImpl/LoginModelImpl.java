package com.zrdb.app.ui.model.modelImpl;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.zrdb.app.ui.callback.ILoginCallback;
import com.zrdb.app.ui.model.ILoginModel;
import com.zrdb.app.util.ApiUtils;
import com.zrdb.app.util.EncryptUtil;
import com.zrdb.app.util.TimeUtil;

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

    @Override
    public void sendNetLogin(String access_token, String expires_in, String openid, String refresh_token, String scope, final ILoginCallback callback) {
        String url = ApiUtils.Config.getDimen() + ApiUtils.LOGIN_WX_URL + EncryptUtil.getMD5("Loginwechat_login"
                + TimeUtil.date2String(TimeUtil.getNowDate(), TimeUtil.YEAR_MONTH_DAY)
                + ApiUtils.clientSecret);
        OkGo.<String>post(url)
                .params("access_token", access_token)
                .params("expires_in", expires_in)
                .params("openid", openid)
                .params("refresh_token", refresh_token)
                .params("scope", scope)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
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
