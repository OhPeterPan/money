package com.zrdb.app.custom_view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Scroller;

import com.blankj.utilcode.util.LogUtils;
import com.zrdb.app.R;

public class ScrollLinearLayout extends LinearLayout implements NestedScrollingParent {
    private Context mContext;
    private LinearLayout llHead;
    private LinearLayout llHosHeadFilter;
    private NestedScrollingParentHelper parentHelper;
    private int maxScrollMeasure = 0;
    private FrameLayout flTest;
    private Scroller scroller;
    private ValueAnimator mOffsetAnimator;

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

    private int TOP_CHILD_FLING_THRESHOLD = 3;

    @Override
    public boolean onNestedFling(@NonNull View target, float velocityX, float velocityY, boolean consumed) {
        //如果是recyclerView 根据判断第一个元素是哪个位置可以判断是否消耗
        //这里判断如果第一个元素的位置是大于TOP_CHILD_FLING_THRESHOLD的
        //认为已经被消耗，在animateScroll里不会对velocityY<0时做处理
        if (target instanceof RecyclerView && velocityY < 0) {
            final RecyclerView recyclerView = (RecyclerView) target;
            final View firstChild = recyclerView.getChildAt(0);
            final int childAdapterPosition = recyclerView.getChildAdapterPosition(firstChild);
            consumed = childAdapterPosition > TOP_CHILD_FLING_THRESHOLD;
        }
        // Log.i(TAG, "onNestedFling" + velocityY+":::"+consumed);
        if (!consumed) {
            animateScroll(velocityY, computeDuration(0), consumed);
        } else {
            animateScroll(velocityY, computeDuration(velocityY), consumed);
        }
        return true;
    }

    /**
     * 根据速度计算滚动动画持续时间
     *
     * @param velocityY
     * @return
     */
    private int computeDuration(float velocityY) {
        final int distance;
        if (velocityY > 0) {
            distance = Math.abs(llHead.getHeight() - getScrollY());
        } else {
            distance = Math.abs(llHead.getHeight() - (llHead.getHeight() - getScrollY()));
        }

        final int duration;
        velocityY = Math.abs(velocityY);
        if (velocityY > 0) {
            duration = 3 * Math.round(1000 * (distance / velocityY));
        } else {
            final float distanceRatio = (float) distance / getHeight();
            duration = (int) ((distanceRatio + 1) * 150);
        }

        return duration;
    }

    private void animateScroll(float velocityY, final int duration, boolean consumed) {
        final int currentOffset = getScrollY();
        final int topHeight = llHead.getHeight();
        if (mOffsetAnimator == null) {
            mOffsetAnimator = new ValueAnimator();
            // mOffsetAnimator.setInterpolator(mInterpolator);
            mOffsetAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    if (animation.getAnimatedValue() instanceof Integer) {
                        scrollTo(0, (Integer) animation.getAnimatedValue());
                    }
                }
            });
        } else {
            mOffsetAnimator.cancel();
        }
        mOffsetAnimator.setDuration(Math.min(duration, 600));

        if (velocityY >= 0) {
            mOffsetAnimator.setIntValues(currentOffset, topHeight);
            mOffsetAnimator.start();
        } else {
            //如果子View没有消耗down事件 那么就让自身滑倒0位置
            if (!consumed) {
                mOffsetAnimator.setIntValues(currentOffset, 0);
                mOffsetAnimator.start();
            }
        }
    }

    @Override
    public boolean onNestedPreFling(@NonNull View target, float velocityX, float velocityY) {

        LogUtils.iTag("scroll", "PreFling");
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
