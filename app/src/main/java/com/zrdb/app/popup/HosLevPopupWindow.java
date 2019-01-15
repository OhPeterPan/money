package com.zrdb.app.popup;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;

import com.blankj.utilcode.util.ScreenUtils;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zrdb.app.R;
import com.zrdb.app.ui.bean.LevelListBean;

import java.util.List;

public class HosLevPopupWindow extends BaseListPopupWindow<LevelListBean> {
    private IOnChooseLevListener listener;

    public HosLevPopupWindow(Context context, List<LevelListBean> data, int layoutId) {
        super(context, data, layoutId);
    }

    @Override
    protected void binderAdapter(BaseViewHolder helper, LevelListBean item) {
        helper.setText(R.id.tvAdapterHosLev, item.name);
        helper.setChecked(R.id.cbAdapterHosLev, item.isChoose);
    }

    @Override
    protected void onItemListener(LevelListBean levelListBean, int position) {
        setChoosePosition(position);
        getAdapter().notifyDataSetChanged();
        if (listener != null) listener.chooseHosLevListener(levelListBean, position);
        dismiss();
    }

    private void setChoosePosition(int position) {
        List<LevelListBean> mData = getAdapter().getData();
        if (mData == null) return;
        for (int i = 0; i < mData.size(); i++) {
            if (position == i) {
                mData.get(i).isChoose = true;
            } else {
                mData.get(i).isChoose = false;
            }
        }
    }

    public void setOnChooseLevListener(IOnChooseLevListener listener) {
        this.listener = listener;
    }

    public interface IOnChooseLevListener {
        void chooseHosLevListener(LevelListBean levelListBean, int position);
    }

    public void show(View parent, int gravity, int x, int y) {
        Rect rect = new Rect();
        parent.getGlobalVisibleRect(rect);
        setHeight(ScreenUtils.getScreenHeight() - rect.bottom);
        showAtLocation(parent, gravity, x, rect.bottom + y);
    }

}
