package com.zrdb.app.popup;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;

import com.chad.library.adapter.base.BaseViewHolder;
import com.zrdb.app.R;
import com.zrdb.app.ui.bean.TecListBean;

import java.util.List;

public class HosTecPopupWindow extends BaseListPopupWindow<TecListBean> {
    private IOnChooseTecListener listener;

    public HosTecPopupWindow(Context context, List<TecListBean> data, int layoutId) {
        super(context, data, layoutId);
    }

    @Override
    protected void binderAdapter(BaseViewHolder helper, TecListBean item) {
        helper.setText(R.id.tvAdapterHosLev, item.name);
    }

    @Override
    protected void onItemListener(TecListBean secListBean, int position) {
        if (listener != null) listener.chooseHosTecListener(secListBean, position);
        dismiss();
    }

    public void setOnChooseTecListener(IOnChooseTecListener listener) {
        this.listener = listener;
    }

    public interface IOnChooseTecListener {
        void chooseHosTecListener(TecListBean bean, int position);
    }

    public void show(View parent, int gravity, int x, int y) {
 /*       Rect rect = new Rect();
        parent.getGlobalVisibleRect(rect);
        setHeight(ScreenUtils.getScreenHeight() - rect.bottom);
        showAtLocation(parent, gravity, x, rect.bottom + y);*/
        Rect rect = new Rect();
        parent.getGlobalVisibleRect(rect);
        int h = parent.getResources().getDisplayMetrics().heightPixels - rect.bottom;
        setHeight(h);

        super.showAsDropDown(parent, x, y);
    }
}
