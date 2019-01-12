package com.zrdb.director.util;

public class ApiUtils {
    public static String clientId = "zrdbm40p1zz1vd";
    public static String clientSecret = "N6ptHgS5TCXvdtGbfHVbklSImAPRTK9h";
    public static final String LOGIN_URL = "login/login?client_id=" + clientId + "&client_token=";
    public static final String GET_VERIFY_URL = "Sms/sendsms?client_id=" + clientId + "&client_token=";
    public static final String REGISTER_URL = "Login/reg?client_id=" + clientId + "&client_token=";
    public static final String CHANGE_PWD_URL = "Login/forgot?client_id=" + clientId + "&client_token=";
    public static final String MAIN_INDEX_URL = "Index/index?client_id=" + clientId + "&client_token=";
    public static final String SEARCH_INFO_URL = "Index/search_log?client_id=" + clientId + "&client_token=";

    public static class Config {
        public static final boolean DEBUG = true;
        public static final String WX_APP_ID = "wx769f48a543df8774";
        private static final String DEBUG_URL = "http://zrdb.vipwfx.cn/api.php/";
        private static final String IMAGE_BASE_URL = "http://zrdb.vipwfx.cn";
        private static final String RELEASE_URL = "http://zrdb.vipwfx.cn/api.php/";
        private static final String DEBUG_PHONE = "15638505561";
        private static final String DEBUG_PWD = "123456";
        private static String dimen = RELEASE_URL;
        private static boolean isDebug = true;

        public static String getDimen() {
            dimen = isDebug ? DEBUG_URL : RELEASE_URL;
            return dimen;
        }

        public static String getImageDimen() {
            return IMAGE_BASE_URL;
        }

        public static void setDebug(boolean debug) {
            isDebug = debug;
        }

        public static String getPhone() {
            return DEBUG_PHONE;
        }

        public static String getPwd() {
            return DEBUG_PWD;
        }

    }
}
