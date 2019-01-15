package com.zrdb.app.ui.hospital;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zrdb.app.R;
import com.zrdb.app.ui.BaseActivity;
import com.zrdb.app.ui.bean.LoginBean;
import com.zrdb.app.ui.presenter.LookHosIndexPresenter;
import com.zrdb.app.ui.viewImpl.ILookHosIndexView;
import com.zrdb.app.util.SpUtil;

import butterknife.BindView;

public class LookHosIndexActivity extends BaseActivity<LookHosIndexPresenter> implements ILookHosIndexView {

    @BindView(R.id.tvActTitle)
    TextView tvActTitle;
    @BindView(R.id.tvActRightTitle)
    TextView tvActRightTitle;
    @BindView(R.id.tvHosSearch)
    TextView tvHosSearch;
    @BindView(R.id.tvHosAddress)
    TextView tvHosAddress;
    @BindView(R.id.ivHosAddress)
    ImageView ivHosAddress;
    @BindView(R.id.llHosAddress)
    LinearLayout llHosAddress;
    @BindView(R.id.tvHosLev)
    TextView tvHosLev;
    @BindView(R.id.ivHosLev)
    ImageView ivHosLev;
    @BindView(R.id.llHosLev)
    LinearLayout llHosLev;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private boolean isRefresh = true;
    private int curPage = 1;
    private String areaId = "0";
    private String levId = "0";
    private LoginBean account;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_index_hos;
    }

    @Override
    protected void initPresenter() {
        presenter = new LookHosIndexPresenter(this);
    }

    private void initToolbar() {
        tvActTitle.setText("找医院");
        tvActTitle.setVisibility(View.VISIBLE);
        setBackRes(R.drawable.ic_back);
        setBackVisibility(View.VISIBLE);
        //ivToolbarRight.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initData() {
        account = (LoginBean) SpUtil.get(SpUtil.ACCOUNT, LoginBean.class);
        initToolbar();
        sendNet(true);
    }

    private void sendNet(boolean showDialog) {
        if (showDialog) {
            curPage = 1;
            isRefresh = true;
        }
        presenter.sendNetHosInfo(account.token,account.uid,areaId,levId,curPage,showDialog);
    }

    @Override
    public void getHosListResultSuccess(String result) {

    }

    @Override
    public void hosFilterResultSuccess(String result) {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void innerListener(View v) {

    }

    @Override
    public void showDataErrInfo(String result) {

    }
}
