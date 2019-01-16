package com.zrdb.app.ui.me;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zrdb.app.R;
import com.zrdb.app.ui.BaseActivity;
import com.zrdb.app.ui.bean.LoginBean;
import com.zrdb.app.ui.presenter.BespokeDetailPresenter;
import com.zrdb.app.ui.viewImpl.IBespokeDetailView;
import com.zrdb.app.util.LogUtil;
import com.zrdb.app.util.ParamUtils;
import com.zrdb.app.util.SpUtil;

import butterknife.BindView;

public class BespokeDetailActivity extends BaseActivity<BespokeDetailPresenter> implements IBespokeDetailView {

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
    @BindView(R.id.tvBespokeMoney)
    TextView tvBespokeMoney;
    @BindView(R.id.tvBespokeTime)
    TextView tvBespokeTime;
    @BindView(R.id.tvBespokeDoc)
    TextView tvBespokeDoc;
    @BindView(R.id.tvBespokeHos)
    TextView tvBespokeHos;
    private LoginBean account;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bespoke_detail;
    }

    @Override
    protected void initPresenter() {
        presenter = new BespokeDetailPresenter(this);
    }

    private void initToolbar() {
        tvActTitle.setText("我的预约");
        tvActTitle.setVisibility(View.VISIBLE);
        setBackRes(R.drawable.ic_back);
        setBackVisibility(View.VISIBLE);
        //ivToolbarRight.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initData() {
        account = (LoginBean) SpUtil.get(SpUtil.ACCOUNT, LoginBean.class);
        String subId = getIntent().getStringExtra(ParamUtils.SUB_ID);
        String type = getIntent().getStringExtra(ParamUtils.TYPE);
        initToolbar();
        sendNet(subId, type);
    }

    private void sendNet(String subId, String type) {
        presenter.sendNet(account.token, account.uid, subId, type);
    }

    @Override
    public void bespokeDetailSuccess(String result) {
        LogUtil.LogI("详情：" + result);

    }

    @Override
    public void showDataErrInfo(String result) {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void innerListener(View v) {

    }

}
