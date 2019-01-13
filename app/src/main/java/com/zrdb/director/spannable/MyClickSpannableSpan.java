package com.zrdb.director.spannable;

import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Toast;

import com.zrdb.director.util.ToastUtil;

public class MyClickSpannableSpan extends ClickableSpan {
    private String content;

    public MyClickSpannableSpan(String content) {
        this.content = content;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setUnderlineText(false);
    }

    @Override
    public void onClick(View widget) {
        ToastUtil.showMessage("你来吗？", Toast.LENGTH_SHORT);
    }
}
