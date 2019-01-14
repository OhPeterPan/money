package com.zrdb.app.app;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.multidex.MultiDex;

import com.blankj.utilcode.util.Utils;
import com.zrdb.app.image_loader.ImageLoader;
import com.zrdb.app.service.InitService;
import com.zrdb.app.util.UIUtil;

public class AppApplication extends Application {
    private static AppApplication sApplication;

    public static AppApplication getInstance() {

        return sApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sApplication = this;
        Utils.init(this);
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
