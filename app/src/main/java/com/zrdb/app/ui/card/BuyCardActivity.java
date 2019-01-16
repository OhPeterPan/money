package com.zrdb.app.ui.card;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zrdb.app.R;
import com.zrdb.app.ui.BaseActivity;

import butterknife.BindView;

public class BuyCardActivity extends BaseActivity {
    @BindView(R.id.tvActTitle)
    TextView tvActTitle;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.tvActRightTitle)
    TextView tvActRightTitle;
    @BindView(R.id.ivToolbarRight)
    ImageView ivToolbarRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
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

    @Override
    protected int getLayoutId() {
        return R.layout.activity_buy_card;
    }

    @Override
    protected void initPresenter() {

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
        initToolbar();
        initPage();
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

                break;
        }
    }
}
