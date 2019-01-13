package com.zrdb.director.util;

import android.text.TextUtils;

import java.security.MessageDigest;

public class EncryptUtil {
    public static String getMD5(String msg) {
        if (TextUtils.isEmpty(msg)) {
            return "";
        }
        try {
            byte[] btInput = msg.getBytes("utf-8");
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            mdInst.update(btInput);
            byte[] md = mdInst.digest();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < md.length; i++) {
                int val = ((int) md[i]) & 0xff;
                if (val < 16) {
                    sb.append("0");
                }
                sb.append(Integer.toHexString(val));
            }
            return sb.toString();
        } catch (Exception e) {
            return null;
        }
    }
}
