package com.zrdb.director.util;

import android.content.Context;

public class SystemUtil {
    public static void exitApp(Context ctx) {
        SpUtil.clearAll();
        //LoginActivity.launchNewFlag(ctx);
    }
}
