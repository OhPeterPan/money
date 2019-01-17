package com.zrdb.app.ui.me;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zrdb.app.R;
import com.zrdb.app.adapter.BespokeAdapter;
import com.zrdb.app.ui.BaseActivity;
import com.zrdb.app.ui.bean.BespokeBean;
import com.zrdb.app.ui.bean.BespokeInfoBean;
import com.zrdb.app.ui.bean.LoginBean;
import com.zrdb.app.ui.presenter.MeBespokePresenter;
import com.zrdb.app.ui.response.BespokeResponse;
import com.zrdb.app.ui.viewImpl.IMeBespokeView;
import com.zrdb.app.util.Convert;
import com.zrdb.app.util.LogUtil;
import com.zrdb.app.util.ParamUtils;
import com.zrdb.app.util.SpUtil;

import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;

public class MeBespokeActivity extends BaseActivity<MeBespokePresenter> implements IMeBespokeView, BaseQuickAdapter.OnItemClickListener {
    @BindView(R.id.tvActTitle)
    TextView tvActTitle;
    @BindView(R.id.ivToolbarRight)
    ImageView ivToolbarRight;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private LoginBean account;
    private List<BespokeBean> mData;
    private BespokeAdapter adapter;

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
        BespokeResponse response = Convert.fromJson(result, BespokeResponse.class);
        BespokeInfoBean bespokeInfoBean = response.data;
        initAdapter(bespokeInfoBean.sub_doctor, bespokeInfoBean.sub_hospital);
    }

    private void initAdapter(List<BespokeBean> subDoctor, List<BespokeBean> subHospital) {
        if (mData == null)
            mData = new LinkedList<>();
        else
            mData.clear();

        if (subDoctor != null)
            mData.addAll(subDoctor);
        if (subDoctor != null)
            mData.addAll(subHospital);

        adapter = new BespokeAdapter(mData);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setEmptyView(getEmpty("感谢您关注主任当班！您还没有预约哦！"));
        adapter.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(BaseQuickAdapter ada, View v, int position) {
        if (adapter == null) return;
        BespokeBean bespokeBean = adapter.getItem(position);
        if (bespokeBean == null) return;
        String type = "1";//1 医生预约详情 2医院预约详情
        if (StringUtils.isEmpty(bespokeBean.doctor_name)) {
            type = "2";
        } else {
            type = "1";
        }
        startIntentActivity(new Intent()
                        .putExtra(ParamUtils.SUB_ID, bespokeBean.sub_id)
                        .putExtra(ParamUtils.TYPE, type)
                , BespokeDetailActivity.class);
    }

    @Override
    public void showDataErrInfo(String result) {
        initAdapter(null, null);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void innerListener(View v) {

    }


}
