package com.zrdb.director.util;

import android.util.Log;

import com.blankj.utilcode.util.LogUtils;

public class LogUtil {
    private static final String TAG = "wak";

    public static void LogI(String msg) {
        if (ApiUtils.Config.DEBUG) {
            LogUtils.iTag(TAG, msg);
        }
    }

    public static void LogE(Throwable e) {
        if (ApiUtils.Config.DEBUG) {
            Log.e(TAG, "", e);
        }
    }
}

