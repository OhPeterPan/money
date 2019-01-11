package com.zrdb.director.custom_view;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class InnerSwipeRefreshLayout extends SwipeRefreshLayout {
    public InnerSwipeRefreshLayout(Context context) {
        super(context);
    }

    public InnerSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (isRefreshing()) {
            return true;
        }
        return super.onInterceptTouchEvent(ev);
    }
}
