package com.zrdb.app.util;

import android.content.Context;

import com.zrdb.app.ui.account.LoginActivity;

public class SystemUtil {
    public static void exitApp(Context ctx) {
        SpUtil.clearAll();
        LoginActivity.launchNewFlag(ctx);
    }
}
