package com.zrdb.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zrdb.app.R;
import com.zrdb.app.ui.bean.DiseaseBean;

import java.util.List;

/**
 * Created by Administrator on 2018/5/14 0014.
 */

public class DiseaseAdapter extends BaseListAdapter<DiseaseBean> {
    public DiseaseAdapter(Context context, List<DiseaseBean> list) {
        super(context, list);
    }

    @Override
    public View initView(DiseaseBean data, View convertView, ViewGroup parent, int position) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_city, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvAdapterClassRight.setText(data.name);
        return convertView;
    }

    public void notifyData(List<DiseaseBean> classDetailList) {
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
