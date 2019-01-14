package com.zrdb.app.ui.main;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.barlibrary.ImmersionBar;
import com.zrdb.app.R;
import com.zrdb.app.adapter.MessageAdapter;
import com.zrdb.app.ui.BaseActivity;
import com.zrdb.app.ui.bean.LoginBean;
import com.zrdb.app.ui.bean.MainMessageBean;
import com.zrdb.app.ui.presenter.MainMessagePresenter;
import com.zrdb.app.ui.response.MainMessageResponse;
import com.zrdb.app.ui.viewImpl.IMainMessageView;
import com.zrdb.app.util.Convert;
import com.zrdb.app.util.LogUtil;
import com.zrdb.app.util.SpUtil;

import butterknife.BindView;

public class MessageActivity extends BaseActivity<MainMessagePresenter> implements IMainMessageView, BaseQuickAdapter.OnItemClickListener {
    @BindView(R.id.tvActTitle)
    TextView tvActTitle;
    @BindView(R.id.ivToolbarRight)
    ImageView ivToolbarRight;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private LoginBean account;
    private MessageAdapter adapter;

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
        return R.layout.activity_message;
    }

    @Override
    protected void initPresenter() {
        presenter = new MainMessagePresenter(this);
    }

    @Override
    protected void initData() {
        initToolbar();
        account = (LoginBean) SpUtil.get(SpUtil.ACCOUNT, LoginBean.class);
        setSwipeRefresh(false);
        initAdapter();
        sendNet();
    }

    private void initAdapter() {
        adapter = new MessageAdapter();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(BaseQuickAdapter ada, View view, int position) {
        MainMessageBean bean = adapter.getItem(position);
        if (bean == null) return;
        if (bean.type == 3) {//查看保障卡
            presenter.sendNetEnsureState(account.token, account.uid);
        }
    }

    private void sendNet() {
        presenter.sendNetMessage(account.token, account.uid);
    }

    private void initToolbar() {
        tvActTitle.setText("消息");
        tvActTitle.setVisibility(View.VISIBLE);
        setBackRes(R.drawable.ic_back);
        setBackVisibility(View.VISIBLE);
        ivToolbarRight.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void innerListener(View v) {

    }

    @Override
    public void getMessageList(String result) {
        LogUtil.logResult("消息：", result);
        MainMessageResponse response = Convert.fromJson(result, MainMessageResponse.class);
        adapter.setNewData(response.data);
        adapter.setEmptyView(getEmpty("感谢您关注主任当班！您还没有任何消息！"));
    }

    // TODO: 2019/1/13 需要根据返回的保障卡状态去不同的界面
    @Override
    public void ensureState(String result) {
        LogUtil.logResult("保障卡：", result);
    }

    @Override
    public void showDataErrInfo(String result) {

    }
}
