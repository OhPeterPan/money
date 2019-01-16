package com.zrdb.app.app;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.multidex.MultiDex;

import com.blankj.utilcode.util.Utils;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zrdb.app.image_loader.ImageLoader;
import com.zrdb.app.service.InitService;
import com.zrdb.app.util.ApiUtils;
import com.zrdb.app.util.UIUtil;

public class AppApplication extends Application {
    private static AppApplication sApplication;
    public static IWXAPI api;

    public static AppApplication getInstance() {

        return sApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sApplication = this;
        Utils.init(this);
        api = WXAPIFactory.createWXAPI(this, ApiUtils.Config.WX_APP_ID, true);
        api.registerApp(ApiUtils.Config.WX_APP_ID);
        ImageLoader.init(UIUtil.getContext());
        Intent intent = new Intent(getApplicationContext(), InitService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent);
        } else {
            startService(intent);
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
