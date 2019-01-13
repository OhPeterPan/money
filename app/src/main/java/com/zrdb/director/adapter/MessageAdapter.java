package com.zrdb.director.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zrdb.director.R;
import com.zrdb.director.ui.bean.MainMessageBean;

public class MessageAdapter extends BaseQuickAdapter<MainMessageBean, BaseViewHolder> {
    public MessageAdapter() {
        super(R.layout.adapter_main_message, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, MainMessageBean item) {
        ImageView ivAdapterMessagePic = helper.getView(R.id.ivAdapterMessagePic);
        helper.setText(R.id.tvAdapterMessageDetail, item.data);
        if (item.type == 1) {//医生
            helper.setText(R.id.tvAdapterMessageInfo, "【可在我的预约中查看详情信息】");
            ivAdapterMessagePic.setImageResource(R.drawable.ic_bespoke_message);
        } else if (item.type == 2) {//医院
            helper.setText(R.id.tvAdapterMessageInfo, "【可在我的预约中查看详情信息】");
            ivAdapterMessagePic.setImageResource(R.drawable.ic_bespoke_message);
        } else {//保障卡
            helper.setText(R.id.tvAdapterMessageInfo, "【海量专家级主任医师尽在主任当班】");
            ivAdapterMessagePic.setImageResource(R.drawable.ic_order_message);
        }
    }
}
