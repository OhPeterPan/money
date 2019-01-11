package com.zrdb.director.util;

import com.blankj.utilcode.util.TimeUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {
    public static final long WRITE_READ_DEFAULT_TIME = 30;
    public static final long CONNECTION_DEFAULT_TIME = 15;
    public static final DateFormat YEAR_MONTH_DAY = new SimpleDateFormat("yyyy-MM-dd");
    public static final DateFormat YEAR_MONTH_DAY_SIMPLE = new SimpleDateFormat("yyyy-M-dd");

    public static String date2String(Date date, DateFormat format) {

        return TimeUtils.date2String(date, format);
    }

    public static Date getNowDate() {
        return TimeUtils.getNowDate();
    }
}
