package com.zrdb.director.util;

import android.content.Context;

import com.zrdb.director.ui.account.LoginActivity;

public class SystemUtil {
    public static void exitApp(Context ctx) {
        SpUtil.clearAll();
        LoginActivity.launchNewFlag(ctx);
    }
}
