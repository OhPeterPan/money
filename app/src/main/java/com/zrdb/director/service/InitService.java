package com.zrdb.director.service;

import android.app.IntentService;
import android.app.Notification;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.Nullable;

import com.blankj.utilcode.util.Utils;
import com.lzy.okgo.OkGo;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumConfig;
import com.zrdb.director.app.AppApplication;
import com.zrdb.director.util.TimeUtil;
import com.zrdb.director.util.UIUtil;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class InitService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public InitService(String name) {
        super(name);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForeground(Integer.MAX_VALUE, new Notification());
        }
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        Album.initialize(
                AlbumConfig.newBuilder(UIUtil.getContext())
                        .setLocale(Locale.getDefault())
                        .build()
        );

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(TimeUtil.CONNECTION_DEFAULT_TIME, TimeUnit.SECONDS)
                .writeTimeout(TimeUtil.WRITE_READ_DEFAULT_TIME, TimeUnit.SECONDS)
                .readTimeout(TimeUtil.WRITE_READ_DEFAULT_TIME, TimeUnit.SECONDS);

        OkGo.getInstance().init(AppApplication.getInstance()).setOkHttpClient(builder.build()).setRetryCount(2);
    }
}
