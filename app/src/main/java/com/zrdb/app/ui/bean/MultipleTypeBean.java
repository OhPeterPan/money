package com.zrdb.app.ui.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public class MultipleTypeBean implements MultiItemEntity {
    public int type;//0代表头部  1代表医生的列表 2代表医院的列表
    public String name;
    public MultipleHosBean hos;
    public MultipleDocBean doc;

    @Override
    public int getItemType() {
        return type;
    }
}
