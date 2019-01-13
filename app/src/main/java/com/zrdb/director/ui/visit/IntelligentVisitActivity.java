package com.zrdb.director.ui.visit;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.zhy.view.flowlayout.TagFlowLayout;
import com.zrdb.director.R;
import com.zrdb.director.spannable.MyClickSpannableSpan;
import com.zrdb.director.ui.BaseActivity;

import butterknife.BindView;

public class IntelligentVisitActivity extends BaseActivity {
    @BindView(R.id.tvActTitle)
    TextView tvActTitle;
    @BindView(R.id.ivToolbarRight)
    ImageView ivToolbarRight;
    @BindView(R.id.etIntelInputName)
    EditText etIntelInputName;
    @BindView(R.id.etIntelInputPhone)
    EditText etIntelInputPhone;
    @BindView(R.id.tvIntelChooseAddress)
    TextView tvIntelChooseAddress;
    @BindView(R.id.tagIntelFlow)
    TagFlowLayout tagIntelFlow;
    @BindView(R.id.tagIntelHerdFlow)
    TagFlowLayout tagIntelHerdFlow;
    @BindView(R.id.etInputHerdDetail)
    EditText etInputHerdDetail;
    @BindView(R.id.ivIntelChooseHerdPic)
    ImageView ivIntelChooseHerdPic;
    @BindView(R.id.cbIntelServiceScheme)
    CheckBox cbIntelServiceScheme;
    @BindView(R.id.btnSubmitHerd)
    Button btnSubmitHerd;

    @Override
    protected void initStatusBar() {
        ImmersionBar.with(this)
                .fitsSystemWindows(true)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true)
                .init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_intelligent_visit;
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initData() {
        initToolbar();
        initServiceScheme();
    }

    private void initServiceScheme() {
        String text = "您已同意主任当班服务协议";
        SpannableString sp = new SpannableString(text);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#4e9afa"));
        ClickableSpan clickableSpan = new MyClickSpannableSpan("");
        sp.setSpan(colorSpan, 4, text.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        sp.setSpan(clickableSpan, 4, text.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        cbIntelServiceScheme.setMovementMethod(LinkMovementMethod.getInstance());
        cbIntelServiceScheme.setText(sp);
    }

    private void initToolbar() {
        tvActTitle.setText("智能就诊");
        tvActTitle.setVisibility(View.VISIBLE);
        setBackRes(R.drawable.ic_back);
        setBackVisibility(View.VISIBLE);
        ivToolbarRight.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void innerListener(View v) {

    }
}
