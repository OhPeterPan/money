package com.zrdb.app.popup;

import android.content.Context;
import android.graphics.Rect;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zrdb.app.R;
import com.zrdb.app.ui.bean.TecListBean;

import java.util.List;

public class TecJobPopupWindow extends BaseWrapListPopupWindow<TecListBean> {

    private OnChooseDocJobListener listener;

    public TecJobPopupWindow(Context context, List<TecListBean> data, int layoutId) {
        super(context, data, layoutId);
    }

    @Override
    protected void innerListener(TecListBean tecListBean, View view, int position) {
        if (listener != null) listener.onDocJobListener(tecListBean);
        dismiss();
    }

    @Override
    protected void binderAdapter(BaseViewHolder helper, TecListBean item) {
        helper.setText(R.id.tvAdapterTecJob, item.name);
        helper.addOnClickListener(R.id.tvAdapterTecJob);
    }

    @Override
    protected void onItemListener(TecListBean tecListBean, int position) {

    }

    public void setOnChooseDocJobListener(OnChooseDocJobListener listener) {
        this.listener = listener;
    }

    public interface OnChooseDocJobListener {
        void onDocJobListener(TecListBean tecListBean);
    }

    public void show(TextView parent) {
        Rect rect = new Rect();
        parent.getGlobalVisibleRect(rect);
        setWidth(parent.getWidth());
        setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        showAtLocation(parent, Gravity.START | Gravity.TOP, ScreenUtils.getScreenWidth()
                - parent.getWidth(), rect.bottom + SizeUtils.dp2px(1));
    }
}
