package com.zrdb.app.popup;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;

import com.blankj.utilcode.util.ScreenUtils;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zrdb.app.R;
import com.zrdb.app.ui.bean.SecListBean;

import java.util.List;

public class HosSecPopupWindow extends BaseListPopupWindow<SecListBean> {
    private IOnChooseSecListener listener;

    public HosSecPopupWindow(Context context, List<SecListBean> data, int layoutId) {
        super(context, data, layoutId);
    }

    @Override
    protected void binderAdapter(BaseViewHolder helper, SecListBean item) {
        helper.setText(R.id.tvAdapterHosLev, item.name);
    }

    @Override
    protected void onItemListener(SecListBean secListBean, int position) {
        if (listener != null) listener.chooseHosSecListener(secListBean, position);
        dismiss();
    }

    public void setOnChooseSecListener(IOnChooseSecListener listener) {
        this.listener = listener;
    }

    public interface IOnChooseSecListener {
        void chooseHosSecListener(SecListBean bean, int position);
    }

    public void show(View parent, int gravity, int x, int y) {
        Rect rect = new Rect();
        parent.getGlobalVisibleRect(rect);
        setHeight(ScreenUtils.getScreenHeight() - rect.bottom);
        showAtLocation(parent, gravity, x, rect.bottom + y);
    }
}
