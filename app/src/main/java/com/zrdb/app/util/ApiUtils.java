package com.zrdb.app.util;

public class ApiUtils {
    public static String clientId = "zrdbm40p1zz1vd";
    public static String clientSecret = "N6ptHgS5TCXvdtGbfHVbklSImAPRTK9h";
    public static final String LOGIN_URL = "login/login?client_id=" + clientId + "&client_token=";
    public static final String GET_VERIFY_URL = "Sms/sendsms?client_id=" + clientId + "&client_token=";
    public static final String REGISTER_URL = "Login/reg?client_id=" + clientId + "&client_token=";
    public static final String CHANGE_PWD_URL = "Login/forgot?client_id=" + clientId + "&client_token=";
    public static final String MAIN_INDEX_URL = "Index/index?client_id=" + clientId + "&client_token=";
    public static final String SEARCH_INFO_URL = "Index/search_log?client_id=" + clientId + "&client_token=";
    public static final String MULTIPLE_RESULT_URL = "index/search?client_id=" + clientId + "&client_token=";
    public static final String FILTER_DOC_URL = "index/search_doctor?client_id=" + clientId + "&client_token=";
    public static final String FILTER_DOC_INFO_URL = "index/search_doctor_filter?client_id=" + clientId + "&client_token=";
    public static final String FILTER_HOS_URL = "index/search_hospital?client_id=" + clientId + "&client_token=";
    public static final String FILTER_HOS_INFO_URL = "index/search_hospital_filter?client_id=" + clientId + "&client_token=";
    public static final String MESSAGE_URL = "Index/message?client_id=" + clientId + "&client_token=";
    public static final String ENSURE_STATE_URL = "Card/status?client_id=" + clientId + "&client_token=";
    public static final String SUBMIT_INTEL_INFO = "index/intelligent?client_id=" + clientId + "&client_token=";
    public static final String UPLOAD_PIC = "Upload/upload?client_id=" + clientId + "&client_token=";
    public static final String GET_DOC_LIST_URL = "Doctor/getlist?client_id=" + clientId + "&client_token=";
    public static final String GET_DOC_FILTER_URL = "Doctor/filter?client_id=" + clientId + "&client_token=";
    public static final String GET_DISEASE_URL = "Doctor/getdisease?client_id=" + clientId + "&client_token=";
    public static final String LOOK_DOC_DETAIL_URL = "Doctor/details?client_id=" + clientId + "&client_token=";
    public static final String SUBSCRIBE_DOC_INFO_URL = "Doctor/subscribe?client_id=" + clientId + "&client_token=";
    public static final String SUBSCRIBE_PERSON_INFO_URL = "Doctor/subscribe_send?client_id=" + clientId + "&client_token=";
    public static final String LOOK_HOS_URL = "Hospital/getlist?client_id=" + clientId + "&client_token=";
    public static final String LOOK_HOS_FILTER_URL = "Hospital/filter?client_id=" + clientId + "&client_token=";
    public static final String GET_HOS_DETAIL_URL = "Hospital/details?client_id=" + clientId + "&client_token=";
    public static final String GET_HOS_DOC_URL = "Hospital/doctorlist?client_id=" + clientId + "&client_token=";
    public static final String SUBSCRIBE_HOS_INFO_URL = "Hospital/subscribe?client_id=" + clientId + "&client_token=";
    public static final String SUBSCRIBE_HOS_PERSON_INFO_URL = "Hospital/subscribe_send?client_id=" + clientId + "&client_token=";
    public static final String PERSON_INFO_INDEX_URL = "User/index?client_id=" + clientId + "&client_token=";
    public static final String USER_INFO_URL = "User/userinfo?client_id=" + clientId + "&client_token=";
    public static final String CHANGE_USER_INFO_URL = "User/editinfo?client_id=" + clientId + "&client_token=";
    public static final String BESPOKE_LIST_URL = "Subscribelist/index?client_id=" + clientId + "&client_token=";

    public static class Config {
        public static final boolean DEBUG = true;
        public static final String WX_APP_ID = "wx769f48a543df8774";
        private static final String DEBUG_URL = "http://zrdb.vipwfx.cn/api.php/";
        private static final String IMAGE_BASE_URL = "http://zrdb.vipwfx.cn";
        private static final String RELEASE_URL = "http://zrdb.vipwfx.cn/api.php/";
        private static final String DEBUG_PHONE = "15638505561";
        private static final String DEBUG_PWD = "123456";
        public static final String SERVICE_SCHEME_URL = "http://zrdb.vipwfx.cn/index/contract?id=1";
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
