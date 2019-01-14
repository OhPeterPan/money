package com.zrdb.app.custom_view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

public class CenterTextView extends TextView {//只适合width为wrap的

    public CenterTextView(Context context) {
        super(context);
    }

    public CenterTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CenterTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable[] drawables = getCompoundDrawables();

        if (null != drawables && drawables[0] != null) {
            float textWidth = getPaint().measureText(getText().toString());
            float contentWidth = textWidth + getCompoundDrawablePadding() + drawables[0].getIntrinsicWidth();
            float dx = (getWidth() - contentWidth) / 2;
            canvas.save();
            canvas.translate(dx, 0);
        }
        super.onDraw(canvas);
    }
}
