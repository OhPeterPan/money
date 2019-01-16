package com.zrdb.app.spannable;

import android.text.TextPaint;
import android.text.style.ClickableSpan;

public abstract class MyClickSpannableSpan extends ClickableSpan {

    public MyClickSpannableSpan() {

    }

    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setUnderlineText(false);
    }

}
