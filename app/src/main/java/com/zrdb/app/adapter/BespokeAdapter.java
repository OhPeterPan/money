package com.zrdb.app.adapter;

import android.support.annotation.Nullable;

import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zrdb.app.R;
import com.zrdb.app.ui.bean.BespokeBean;

import java.util.List;

public class BespokeAdapter extends BaseQuickAdapter<BespokeBean, BaseViewHolder> {
    public BespokeAdapter(@Nullable List<BespokeBean> data) {
        super(R.layout.adapter_bespoke, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BespokeBean item) {
        helper.setText(R.id.tvAdapterBespokeMoney, item.money + " 元");
        helper.setText(R.id.tvAdapterBespokeTime, item.create_time);
        if (StringUtils.isEmpty(item.doctor_name)) {
            helper.setGone(R.id.tvAdapterBespokeDoc, false);
            helper.setGone(R.id.tvAdapterBespokePersonDisease, true);
            helper.setText(R.id.tvAdapterBespokeDoc, "");
            helper.setText(R.id.tvAdapterBespokePersonDisease, String.format("患者疾病：%s", item.disease));
        } else {
            helper.setGone(R.id.tvAdapterBespokeDoc, true);
            helper.setGone(R.id.tvAdapterBespokePersonDisease, false);
            helper.setText(R.id.tvAdapterBespokeDoc, String.format("问诊医生：%s", item.doctor_name));
            helper.setText(R.id.tvAdapterBespokePersonDisease, "");
        }

        if (StringUtils.isEmpty(item.hos_name)) {
            helper.setText(R.id.tvAdapterBespokeHos, String.format("问诊医院：%s", item.hname + " " + item.sec_name));
        } else {
            helper.setText(R.id.tvAdapterBespokeHos, String.format("问诊医院：%s", item.hos_name + " " + item.sec_name));
        }
        helper.setText(R.id.tvAdapterBespokePersonName, String.format("患者姓名：%s", item.name));//
    }
}
