package com.zrdb.app.ui.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public class MessageOnLineBean implements MultiItemEntity {

    public String fromid;
    public String fromavatar;
    public String content;
    public int type;//1平台消息  2个人消息

    @Override
    public int getItemType() {
        return type;
    }
}
