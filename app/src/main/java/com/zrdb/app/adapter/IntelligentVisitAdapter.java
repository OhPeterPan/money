package com.zrdb.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zrdb.app.R;

import java.util.List;

public abstract class IntelligentVisitAdapter extends TagAdapter<String> {

    private LayoutInflater mInflater;

    public IntelligentVisitAdapter(List<String> datas, Context context) {
        super(datas);
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(FlowLayout parent, int position, String str) {
        TextView tv = (TextView) mInflater.inflate(R.layout.layout_filter_tag, parent, false);
        tv.setText(str);
        setLayoutParams(tv);
        return tv;
    }

    protected abstract void setLayoutParams(TextView tv);
}
