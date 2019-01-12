package com.zrdb.director.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zrdb.director.R;
import com.zrdb.director.ui.bean.KeywordBean;

import java.util.List;

public class HistoryFlowAdapter extends TagAdapter<KeywordBean> {
    private LayoutInflater mInflater;

    public HistoryFlowAdapter(List<KeywordBean> datas, Context mContext) {
        super(datas);
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public View getView(FlowLayout parent, int position, KeywordBean bean) {
        TextView tv = (TextView) mInflater.inflate(R.layout.layout_history_tag, parent, false);
        tv.setText(bean.keywords);
        return tv;
    }
}
