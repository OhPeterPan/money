package com.zrdb.app.ui.card;

import android.content.Intent;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.zrdb.app.R;
import com.zrdb.app.rxbus.RxBus;
import com.zrdb.app.ui.BaseActivity;
import com.zrdb.app.ui.bean.CardInfoBean;
import com.zrdb.app.ui.bean.LoginBean;
import com.zrdb.app.ui.order.OrderBuyActivity;
import com.zrdb.app.ui.presenter.BuyCardPresenter;
import com.zrdb.app.ui.response.CardInfoResponse;
import com.zrdb.app.ui.viewImpl.IBuyCardView;
import com.zrdb.app.util.Convert;
import com.zrdb.app.util.LogUtil;
import com.zrdb.app.util.ParamUtils;
import com.zrdb.app.util.SpUtil;

import butterknife.BindView;
import io.reactivex.functions.Function;

public class BuyCardActivity extends BaseActivity<BuyCardPresenter> implements IBuyCardView {
    @BindView(R.id.tvActTitle)
    TextView tvActTitle;
    @BindView(R.id.tvActRightTitle)
    TextView tvActRightTitle;
    @BindView(R.id.ivToolbarRight)
    ImageView ivToolbarRight;
    @BindView(R.id.tvCardTitle)
    TextView tvCardTitle;
    @BindView(R.id.tvCardGroom)
    TextView tvCardGroom;
    @BindView(R.id.tvCardEffect)
    TextView tvCardEffect;
    @BindView(R.id.ivCardVipOne)
    ImageView ivCardVipOne;
    @BindView(R.id.tvCardVipOneTitle)
    TextView tvCardVipOneTitle;
    @BindView(R.id.tvCardVipOneDetail)
    TextView tvCardVipOneDetail;
    @BindView(R.id.tvCardVipOneWarning)
    TextView tvCardVipOneWarning;
    @BindView(R.id.ivCardVipTwo)
    ImageView ivCardVipTwo;
    @BindView(R.id.tvCardVipTwoTitle)
    TextView tvCardVipTwoTitle;
    @BindView(R.id.tvCardVipTwoDetail)
    TextView tvCardVipTwoDetail;
    @BindView(R.id.tvCardVipThreeTitle)
    TextView tvCardVipThreeTitle;
    @BindView(R.id.tvDocCard)
    TextView tvDocCard;
    @BindView(R.id.tvDocCardApply)
    TextView tvDocCardApply;
    @BindView(R.id.scCard)
    ScrollView scCard;
    @BindView(R.id.tvCardMoney)
    TextView tvCardMoney;
    private LoginBean account;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_buy_card;
    }

    @Override
    protected void initPresenter() {
        presenter = new BuyCardPresenter(this);
    }

    private void initToolbar() {
        tvActTitle.setText("保障卡");
        tvActTitle.setVisibility(View.VISIBLE);
        setBackRes(R.drawable.ic_back);
        setBackVisibility(View.VISIBLE);
        //ivToolbarRight.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initData() {
        account = (LoginBean) SpUtil.get(SpUtil.ACCOUNT, LoginBean.class);
        initToolbar();
        initPage();
        presenter.sendNetBuyEnsureCard(account.token, account.uid);
    }

    private void initPage() {
        tvCardVipOneDetail.setText("1.具体医疗顾问具有多年医疗行业经验，对疾病、各大医院科室、医生得擅长都非常熟悉。\n" +
                "2.由专属医疗顾问，对症推荐专家；\n" +
                "3.疑难病症，有专业医疗顾问，讨论后推荐；");
    }

    @Override
    protected void initListener() {
        tvDocCard.setOnClickListener(this);
        tvDocCardApply.setOnClickListener(this);
    }

    @Override
    protected void innerListener(View v) {
        switch (v.getId()) {
            case R.id.tvDocCard://咨询客服

                break;
            case R.id.tvDocCardApply://立即购买  去购买界面
                payEnsurePage();
                break;
        }
    }

    private void payEnsurePage() {
        startIntentActivity(new Intent().putExtra(ParamUtils.TYPE, "3")
                        .putExtra(ParamUtils.FLAG, 1)
                , OrderBuyActivity.class);
        RxBus.getInstance().chainProcess(new Function() {
            @Override
            public Object apply(Object o) throws Exception {
                return "支付关闭";
            }
        });
        finish();
    }

    @Override
    public void getEnsureCardOrderSuccess(String result) {
        LogUtil.LogI("购买：" + result);
        scCard.setVisibility(View.VISIBLE);
        CardInfoResponse response = Convert.fromJson(result, CardInfoResponse.class);
        CardInfoBean cardInfo = response.data;
        tvCardTitle.setText(cardInfo.name);
        tvCardMoney.setText(cardInfo.money);
        initPercent(cardInfo.percent);
    }

    private void initPercent(String percent) {
        SpannableString sp = new SpannableString("全站预约服务" + percent + "折");
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#4e9afa"));
        sp.setSpan(colorSpan, 5, sp.length() - 1, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvCardVipTwoTitle.setText(sp);
    }

    @Override
    public void showDataErrInfo(String result) {

    }
}
