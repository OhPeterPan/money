package com.zrdb.director.ui.main;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gyf.barlibrary.ImmersionBar;
import com.zrdb.director.R;
import com.zrdb.director.adapter.FollowUpAdapter;
import com.zrdb.director.decorate.MainGridDecorate;
import com.zrdb.director.ui.BaseActivity;
import com.zrdb.director.ui.bean.LoginBean;
import com.zrdb.director.util.SpUtil;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private LoginBean account;
    private MainAdapter adapter;
    private RecyclerView horizontalRecyclerView;
    private View headView;

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
        return R.layout.activity_app_index;
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initData() {
        account = (LoginBean) SpUtil.get(SpUtil.ACCOUNT, LoginBean.class);
        initAdapter();
        initHeadView();
    }

    private void initHeadView() {
        headView = LayoutInflater.from(this).inflate(R.layout.view_main_head, recyclerView, false);
        horizontalRecyclerView = headView.findViewById(R.id.horizontalRecyclerView);
        FollowUpAdapter followUpAdapter = new FollowUpAdapter();
        horizontalRecyclerView.setHasFixedSize(true);
        horizontalRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        horizontalRecyclerView.setAdapter(followUpAdapter);
        followUpAdapter.setNewData(testList);

        adapter.addHeaderView(headView);
    }

    private void initAdapter() {
        adapter = new MainAdapter();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.addItemDecoration(new MainGridDecorate());
        adapter.setHeaderAndEmpty(true);
        recyclerView.setAdapter(adapter);
        adapter.setNewData(testList);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void innerListener(View v) {

    }

    private class MainAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public MainAdapter() {
            super(R.layout.adapter_main, null);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {

        }
    }
}
