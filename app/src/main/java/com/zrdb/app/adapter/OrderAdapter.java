package com.zrdb.app.adapter;

import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zrdb.app.R;
import com.zrdb.app.ui.bean.OrderDetailBean;

public class OrderAdapter extends BaseQuickAdapter<OrderDetailBean, BaseViewHolder> {

    public OrderAdapter() {
        super(R.layout.adapter_order, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderDetailBean item) {
        helper.setText(R.id.tvAdapterOrderMoney, String.format("需支付金额¥%s元", item.money));
        if (StringUtils.equals("1", item.type)) {
            helper.setImageResource(R.id.ivAdapterOrderState, R.drawable.ic_order_doc_pic);
            helper.setText(R.id.tvAdapterOrderType, "预约医生");
        } else if (StringUtils.equals("2", item.type)) {
            helper.setImageResource(R.id.ivAdapterOrderState, R.drawable.ic_order_hos_pic);
            helper.setText(R.id.tvAdapterOrderType, "预约医院");//预约医院
        } else {
            helper.setImageResource(R.id.ivAdapterOrderState, R.drawable.ic_order_card);
            helper.setText(R.id.tvAdapterOrderType, "医疗保障卡");
        }
        if (StringUtils.equals("0", item.status)) {
            helper.setText(R.id.tvAdapterOrderState, "待支付");
            helper.setGone(R.id.tvAdapterGoPay, true);
        } else {
            helper.setText(R.id.tvAdapterOrderState, "支付成功");
            helper.setGone(R.id.tvAdapterGoPay, false);
        }
        helper.addOnClickListener(R.id.tvAdapterGoPay);
        helper.addOnClickListener(R.id.tvAdapterDelOrder);
    }
}
