package com.zrdb.app.util;

import android.util.Log;

import com.blankj.utilcode.util.LogUtils;
import com.zrdb.app.R;

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

    public static void logResult(String msg, String result) {
        if (ApiUtils.Config.DEBUG) {
            LogUtils.iTag(TAG, String.format(UIUtil.getString(R.string.printf), msg, result));
        }
    }
}

