package com.zrdb.director.popup;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

public abstract class BasePopupWindow<T> extends PopupWindow {
    private final T mData;
    private Context mContext;

    public Context getContext() {
        return mContext;
    }

    public T getData() {
        return mData;
    }

    public BasePopupWindow(Context context, T data) {
        super(context);
        this.mContext = context;
        this.mData = data;
        initPopupWindow();
    }

    private void initPopupWindow() {
        View view = LayoutInflater.from(mContext).inflate(getInflateRes(), null);
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setFocusable(true);
        setContentView(view);
        setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        // setAnimationStyle(R.style.popupAnimation);
        initView(view);
        initListener();
    }

    protected abstract void initView(View view);

    protected abstract void initListener();

    protected abstract int getInflateRes();
}
