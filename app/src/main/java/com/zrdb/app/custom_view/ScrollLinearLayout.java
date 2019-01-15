package com.zrdb.app.custom_view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Scroller;

import com.zrdb.app.R;

public class ScrollLinearLayout extends LinearLayout implements NestedScrollingParent {
    private Context mContext;
    private LinearLayout llHead;
    private LinearLayout llHosHeadFilter;
    private NestedScrollingParentHelper parentHelper;
    private int maxScrollMeasure = 0;
    private FrameLayout flTest;
    private Scroller scroller;

    public ScrollLinearLayout(Context context) {
        this(context, null);
    }

    public ScrollLinearLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        llHead = findViewById(R.id.llHead);
        llHosHeadFilter = findViewById(R.id.llHosHeadFilter);
        //flTest = findViewById(R.id.flTest);
    }

    private void init() {
        parentHelper = new NestedScrollingParentHelper(this);
        scroller = new Scroller(mContext);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int headHeight = llHead.getMeasuredHeight();
        // int bottomHeight = flTest.getMeasuredHeight();
        maxScrollMeasure = headHeight;
        int filterHeight = llHosHeadFilter.getMeasuredHeight();
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(getMeasuredHeight() + headHeight + 0, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    public boolean onStartNestedScroll(@NonNull View child, @NonNull View target, int axes) {
        return true;
    }

    @Override
    public void onNestedScrollAccepted(@NonNull View child, @NonNull View target, int axes) {
        parentHelper.onNestedScrollAccepted(child, target, axes);
    }

    @Override
    public void onStopNestedScroll(@NonNull View target) {
        parentHelper.onStopNestedScroll(target);
    }

    @Override
    public void onNestedScroll(@NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {

    }

    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed) {
        int supposeY = maxScrollMeasure - getScrollY();
        if (dy > 0) {//向上滑动
            if (supposeY > dy) {
                offSet(dy, consumed);
            } else {
                offSet(maxScrollMeasure - getScrollY(), consumed);
            }
        }
        if (dy < 0) {//向下滑动
            if (!target.canScrollVertically(dy)) {
                // offSet(dy, consumed);
                if (getScrollY() < Math.abs(dy)) {
                    offSet(-getScrollY(), consumed);
                } else {
                    offSet(dy, consumed);
                }
            }
        }
    }

    @Override
    public void scrollTo(int x, int y) {
        if (y >= maxScrollMeasure)
            y = maxScrollMeasure;

        if (y <= 0)
            y = 0;
        super.scrollTo(x, y);
    }

    private void offSet(int dy, int[] consumed) {
        consumed[1] = dy;
        scrollBy(0, dy);
    }

    @Override
    public boolean onNestedFling(@NonNull View target, float velocityX, float velocityY, boolean consumed) {
        return false;
    }

    @Override
    public boolean onNestedPreFling(@NonNull View target, float velocityX, float velocityY) {
    /*    if (getScrollY() >= maxScrollMeasure) return false;
        fling((int) velocityY);*/
        return false;
    }

    private void fling(int velocityY) {
        scroller.fling(0, 0, 0, velocityY, 0, getScrollY(), 0, maxScrollMeasure);
        invalidate();
    }


    @Override
    public void computeScroll() {
        super.computeScroll();
        if (scroller.computeScrollOffset()) {
            scrollTo(0, scroller.getCurrY());
            invalidate();
        }
    }

    @Override
    public int getNestedScrollAxes() {
        return parentHelper.getNestedScrollAxes();
    }
}
