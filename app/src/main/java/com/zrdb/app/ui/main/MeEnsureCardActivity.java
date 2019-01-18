package com.zrdb.app.ui.main;

import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;

import com.zrdb.app.R;
import com.zrdb.app.ui.BaseActivity;
import com.zrdb.app.ui.bean.CardInfoBean;
import com.zrdb.app.ui.bean.LoginBean;
import com.zrdb.app.ui.presenter.MeEnsureCardPresenter;
import com.zrdb.app.ui.response.CardInfoResponse;
import com.zrdb.app.ui.viewImpl.IMeEnsureCardView;
import com.zrdb.app.util.Convert;
import com.zrdb.app.util.SpUtil;

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

    }

    @Override
    protected void innerListener(View v) {

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
        tvCardVipOneDetail.setText("1.具体医疗顾问具有多年医疗行业经验，对疾病、各大医院科室、医生得擅长都非常熟悉。\n" +
                "2.由专属医疗顾问，对症推荐专家；\n" +
                "3.疑难病症，有专业医疗顾问，讨论后推荐；");
    }

    @Override
    public void showDataErrInfo(String result) {

    }
}
