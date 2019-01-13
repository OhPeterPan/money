package com.zrdb.director.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zrdb.director.R;
import com.zrdb.director.ui.bean.CityBean;

import java.util.List;

/**
 * Created by Administrator on 2018/5/14 0014.
 */

public class CityAdapter extends BaseListAdapter<CityBean> {
    public CityAdapter(Context context, List<CityBean> list) {
        super(context, list);
    }

    @Override
    public View initView(CityBean data, View convertView, ViewGroup parent, int position) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_city, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvAdapterClassRight.setText(data.areaName);
        return convertView;
    }

    public void notifyData(List<CityBean> classDetailList) {
        this.list = classDetailList;
        notifyDataSetChanged();
    }

    private class ViewHolder {

        public TextView tvAdapterClassRight;

        public ViewHolder(View view) {
            tvAdapterClassRight = view.findViewById(R.id.tvAdapterClassRight);
        }
    }
}
