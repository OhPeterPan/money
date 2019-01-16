package com.zrdb.app.util;

import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.StringUtils;

public class InfoUtil {
    /**
     * 得到中间带星号的电话号码
     */
    public static String getNumber(String data) {
        if (!StringUtils.isEmpty(data) && RegexUtils.isMobileExact(data)) {
            String sub = data.substring(0, 3) + "****"
                    + data.substring(7, data.length());

            return sub;
        }
        return "";
    }
}
