package com.zrdb.app.ui.main;

import android.content.Intent;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zrdb.app.R;
import com.zrdb.app.ui.BaseActivity;
import com.zrdb.app.ui.bean.CardInfoBean;
import com.zrdb.app.ui.bean.LoginBean;
import com.zrdb.app.ui.presenter.MeEnsureCardPresenter;
import com.zrdb.app.ui.response.CardInfoResponse;
import com.zrdb.app.ui.viewImpl.IMeEnsureCardView;
import com.zrdb.app.util.Convert;
import com.zrdb.app.util.SpUtil;
import com.zrdb.app.util.ToastUtil;

import butterknife.BindView;

public class MeEnsureCardActivity extends BaseActivity<MeEnsureCardPresenter> implements IMeEnsureCardView {
    @BindView(R.id.tvActTitle)
    TextView tvActTitle;
    @BindView(R.id.ivToolbarRight)
    ImageView ivToolbarRight;
    @BindView(R.id.viewStub)
    ViewStub viewStub;
    @BindView(R.id.tvMeDocCard)
    TextView tvMeDocCard;
    @BindView(R.id.tvMeDocCardApply)
    TextView tvMeDocCardApply;
    private LoginBean account;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ensure_info;
    }

    @Override
    protected void initPresenter() {
        presenter = new MeEnsureCardPresenter(this);
    }

    private void initToolbar() {
        tvActTitle.setText("我的医疗保障卡");
        tvActTitle.setVisibility(View.VISIBLE);
        setBackRes(R.drawable.ic_back);
        setBackVisibility(View.VISIBLE);
        //ivToolbarRight.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initData() {
        account = (LoginBean) SpUtil.get(SpUtil.ACCOUNT, LoginBean.class);
        initToolbar();
        presenter.sendNetMyEnsureCard(account.token, account.uid);
    }

    @Override
    protected void initListener() {
        tvMeDocCard.setOnClickListener(this);
        tvMeDocCardApply.setOnClickListener(this);
    }

    @Override
    protected void innerListener(View v) {
        switch (v.getId()) {
            case R.id.tvMeDocCard://在线聊天
                startIntentActivity(new Intent(), MessageOnLineActivity.class);
                break;
            case R.id.tvMeDocCardApply://退款
                ToastUtil.showMessage("暂未开放,敬请期待~", Toast.LENGTH_SHORT);
                break;
        }
    }

    @Override
    public void getMyEnsureCardSuccess(String result) {
        CardInfoResponse response = Convert.fromJson(result, CardInfoResponse.class);
        CardInfoBean cardInfo = response.data;
        initStubView(cardInfo);
    }

    private void initStubView(CardInfoBean cardInfo) {
        viewStub.setLayoutResource(R.layout.view_stub_me_ensure);
        View view = viewStub.inflate();
        TextView tvCardVipOneDetail = view.findViewById(R.id.tvCardVipOneDetail);
        TextView tvMeCardTitle = view.findViewById(R.id.tvMeCardTitle);
        TextView tvCardVipTwoTitle = view.findViewById(R.id.tvCardVipTwoTitle);
        tvCardVipOneDetail.setText("1.具体医疗顾问具有多年医疗行业经验，对疾病、各大医院科室、医生得擅长都非常熟悉。\n" +
                "2.由专属医疗顾问，对症推荐专家；\n" +
                "3.疑难病症，有专业医疗顾问，讨论后推荐；");
        tvMeCardTitle.setText(cardInfo.name);
        initPercent(tvCardVipTwoTitle, cardInfo.percent);
    }

    private void initPercent(TextView tvCardVipTwoTitle, String percent) {
        SpannableString sp = new SpannableString("全站预约服务" + percent + "折");
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#4e9afa"));
        sp.setSpan(colorSpan, 6, sp.length() - 1, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvCardVipTwoTitle.setText(sp);
    }

    @Override
    public void showDataErrInfo(String result) {

    }
}
