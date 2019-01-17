package com.zrdb.app.ui.me;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zrdb.app.R;
import com.zrdb.app.adapter.FragmentAdapter;
import com.zrdb.app.fragment.order.OrderFragment;
import com.zrdb.app.ui.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MeOrderActivity extends BaseActivity {
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
    @BindView(R.id.tabOrderLayout)
    TabLayout tabOrderLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    private List<Fragment> fragmentList = new ArrayList<>();
    private String[] titles = new String[]{"全部", "待付款"};

    @Override
    protected int getLayoutId() {
        return R.layout.actvity_order;
    }

    @Override
    protected void initPresenter() {

    }

    private void initToolbar() {
        tvActTitle.setText("我的订单");
        tvActTitle.setVisibility(View.VISIBLE);
        setBackRes(R.drawable.ic_back);
        setBackVisibility(View.VISIBLE);
        //ivToolbarRight.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initData() {
        initToolbar();
        initFragList();
    }

    private void initFragList() {
        fragmentList.add(OrderFragment.newInstance(""));
        fragmentList.add(OrderFragment.newInstance("0"));
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager(), fragmentList, titles);
        viewPager.setAdapter(adapter);
        tabOrderLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void innerListener(View v) {

    }
}
