package com.zrdb.app.ui.me;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zrdb.app.R;
import com.zrdb.app.ui.BaseActivity;
import com.zrdb.app.ui.bean.LoginBean;
import com.zrdb.app.ui.presenter.MeBespokePresenter;
import com.zrdb.app.ui.viewImpl.IMeBespokeView;
import com.zrdb.app.util.LogUtil;
import com.zrdb.app.util.SpUtil;

import butterknife.BindView;

public class MeBespokeActivity extends BaseActivity<MeBespokePresenter> implements IMeBespokeView {
    @BindView(R.id.tvActTitle)
    TextView tvActTitle;
    @BindView(R.id.ivToolbarRight)
    ImageView ivToolbarRight;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private LoginBean account;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_me_bespoke;
    }

    @Override
    protected void initPresenter() {
        presenter = new MeBespokePresenter(this);
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
        initToolbar();
        sendNet();
    }

    private void sendNet() {
        presenter.sendNet(account.token, account.uid);
    }

    @Override
    public void getBespokeListSuccess(String result) {
        LogUtil.LogI("预约" + result);

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
