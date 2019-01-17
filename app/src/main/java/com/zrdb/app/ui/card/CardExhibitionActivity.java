package com.zrdb.app.ui.card;

import android.content.Intent;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.zrdb.app.R;
import com.zrdb.app.annotation.Register;
import com.zrdb.app.rxbus.RxBus;
import com.zrdb.app.spannable.MyClickSpannableSpan;
import com.zrdb.app.ui.BaseActivity;

import butterknife.BindView;

public class CardExhibitionActivity extends BaseActivity {
    @BindView(R.id.tvCardNoBuy)
    TextView tvCardNoBuy;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_card_exhibition;
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initData() {
        RxBus.getInstance().register(this);
        SpannableString sp = new SpannableString("您还未购买保障卡\n是否 前去购买");
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#4e9afa"));
        MyClickSpannableSpan clickSpan = new MyClickSpannableSpan() {
            @Override
            public void onClick(View widget) {
                startIntentActivity(new Intent(), BuyCardActivity.class);//去购买界面
            }
        };
        sp.setSpan(colorSpan, 12, sp.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        sp.setSpan(clickSpan, 12, sp.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        tvCardNoBuy.setMovementMethod(LinkMovementMethod.getInstance());
        tvCardNoBuy.setText(sp);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().remove(this);
    }

    @Register
    private void finishMsg(String msg) {
        if (TextUtils.equals("支付关闭", msg)) {
            finish();
        }
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void innerListener(View v) {

    }
}
