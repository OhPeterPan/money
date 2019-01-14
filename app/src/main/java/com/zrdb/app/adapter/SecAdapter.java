package com.zrdb.app.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zrdb.app.R;
import com.zrdb.app.ui.bean.SecListBean;

import java.util.List;

public class SecAdapter extends BaseListAdapter<SecListBean> {
    private int mPosition = 0;

    public SecAdapter(Context context, List<SecListBean> list) {
        super(context, list);
    }

    @Override
    public View initView(SecListBean bean, View convertView, ViewGroup parent, int position) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_class_left, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvAdapterClassLeft.setText(list.get(position).name);
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
