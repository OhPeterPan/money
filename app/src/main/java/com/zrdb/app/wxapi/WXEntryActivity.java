package com.zrdb.app.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.zrdb.app.R;
import com.zrdb.app.app.AppApplication;
import com.zrdb.app.event.EventBusUtil;
import com.zrdb.app.event.MsgEvent;
import com.zrdb.app.util.ApiUtils;
import com.zrdb.app.util.LogUtil;
import com.zrdb.app.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;

public class WXEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {
    public static String code;
    public static BaseResp resp = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wxentry);
        boolean handleIntent = AppApplication.api.handleIntent(getIntent(), this);
        //下面代码是判断微信分享后返回WXEnteryActivity的，如果handleIntent==false,说明没有调用IWXAPIEventHandler，则需要在这里销毁这个透明的Activity;
        if (handleIntent == false) {
            LogUtil.LogI("onCreate: " + handleIntent);
            finish();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        AppApplication.api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {
        LogUtil.LogI("onReq");
        finish();
    }

    @Override
    public void onResp(BaseResp baseResp) {
        if (baseResp != null) {
            resp = baseResp;
            code = ((SendAuth.Resp) baseResp).code; //即为所需的code
        }
        LogUtil.LogI("code:" + baseResp.errCode);
        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                LogUtil.LogI("onResp: 成功");

                getAccessToken(resp, code);
                // finish();
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                //LogUtil.LogI("onResp: 用户取消");
                ToastUtil.showMessage("用户已取消", Toast.LENGTH_SHORT);
                finish();
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                LogUtil.LogI("onResp: 发送请求被拒绝");
                finish();
                break;
            case BaseResp.ErrCode.ERR_BAN:
                LogUtil.LogI("onResp: 应用签名错误");
                finish();
                break;
        }
    }

    private void getAccessToken(BaseResp resp, String code) {
        OkGo.<String>get("https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + ApiUtils.Config.WX_APP_ID + "&secret=" + ApiUtils.Config.WX_APP_SECRET)
                .params("code", code)
                .params("grant_type", "authorization_code")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        LogUtil.LogI("微信返回:" + response.body());
                        parseJson(response.body());
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastUtil.showMessage("微信授权失败", Toast.LENGTH_SHORT);
                    }
                });

    }

    private void parseJson(String result) {
        EventBus.getDefault().post(new MsgEvent(EventBusUtil.LOGIN_CODE, result));//0x0010 传递给
        finish();

    }
}
