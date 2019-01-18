package com.zrdb.app.popup;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zrdb.app.R;

import java.util.List;

public abstract class BaseWrapListPopupWindow<T> extends PopupWindow implements BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener {

    private Context mContext;
    private int layoutId;
    private List<T> mData;
    private RecyclerView recyclerView;
    private PopupAdapter adapter;

    public BaseWrapListPopupWindow(Context context, List<T> data, int layoutId) {
        super(context);
        this.mData = data;
        this.layoutId = layoutId;
        this.mContext = context;
        init(context);
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public PopupAdapter getAdapter() {
        return adapter;
    }

    private void init(Context context) {
        View v = LayoutInflater.from(context).inflate(R.layout.layout_popup_wrap_recycle, null);
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setFocusable(true);
        setContentView(v);
        setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        //setAnimationStyle(R.style.popupAnimation);
        recyclerView = v.findViewById(R.id.recyclerView);
        initAdapter();
    }

    private void initAdapter() {
        adapter = new PopupAdapter(layoutId, mData);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
       // adapter.setOnItemClickListener(this);
        adapter.setOnItemChildClickListener(this);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        onItemListener(mData.get(position), position);
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        innerListener(mData.get(position), view, position);
    }

    protected abstract void innerListener(T t, View view, int position);

    class PopupAdapter extends BaseQuickAdapter<T, BaseViewHolder> {

        public PopupAdapter(int layoutResId, @Nullable List<T> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, T item) {
            binderAdapter(helper, item);
        }
    }

    protected abstract void binderAdapter(BaseViewHolder helper, T item);

    protected abstract void onItemListener(T t, int position);
}
