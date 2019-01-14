package com.zrdb.app.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zrdb.app.R;
import com.zrdb.app.ui.bean.ProvinceBean;

import java.util.List;

public class ProvinceAdapter extends BaseListAdapter<ProvinceBean> {
    private int mPosition = 0;

    public ProvinceAdapter(Context context, List<ProvinceBean> list) {
        super(context, list);
    }

    @Override
    public View initView(ProvinceBean provinceBean, View convertView, ViewGroup parent, int position) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_class_left, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvAdapterClassLeft.setText(list.get(position).areaName);
        if (position == mPosition) {
            viewHolder.tvAdapterClassLeft.setBackgroundColor(Color.WHITE);
        } else {
            viewHolder.tvAdapterClassLeft.setBackgroundColor(0xFFF6F6F6);
        }
        return convertView;
    }

    public void setPosition(int position) {
        this.mPosition = position;
    }

    private class ViewHolder {

        public TextView tvAdapterClassLeft;

        public ViewHolder(View view) {
            tvAdapterClassLeft = view.findViewById(R.id.tvAdapterClassLeft);
        }
    }
}
