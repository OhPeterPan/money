package com.zrdb.app.ui.bean;

import com.google.gson.annotations.SerializedName;

public class PayInfoBean {


    public String prepayid;
    public String appid;
    public String partnerid;
    @SerializedName("package")
    public String packageX;
    public String noncestr;
    public String timestamp;
    public String sign;

}
