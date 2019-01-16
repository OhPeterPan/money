package com.zrdb.app.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.zrdb.app.R;
import com.zrdb.app.app.AppApplication;
import com.zrdb.app.util.LogUtil;

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
                finish();
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                LogUtil.LogI("onResp: 用户取消");
                finish();
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                LogUtil.LogI("onResp: 发送请求被拒绝");
                finish();
                break;
            case BaseResp.ErrCode.ERR_BAN:
                LogUtil.LogI("onResp: 应用签名错误");
                //finish();
                break;
        }
    }
}
