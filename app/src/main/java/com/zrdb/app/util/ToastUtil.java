package com.zrdb.app.util;

import android.widget.Toast;

public class ToastUtil {
    private static Toast toast;

    public static void showMessage(String msg, int duration) {
        if (toast == null)
            toast = Toast.makeText(UIUtil.getContext(), msg, duration);

        toast.setText(msg);
        toast.show();
    }

    public static void showError(String result) {
        if (toast == null)
            toast = Toast.makeText(UIUtil.getContext(), result, Toast.LENGTH_SHORT);

        toast.setText(result);
        toast.show();
    }
}
