package com.zrdb.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.KeyboardUtils;
import com.gyf.barlibrary.ImmersionBar;
import com.zrdb.app.R;
import com.zrdb.app.dialog.LoadDialog;
import com.zrdb.app.ui.presenter.BasePresenter;
import com.zrdb.app.util.LogUtil;
import com.zrdb.app.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

@SuppressWarnings(value = "unchecked")
public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity
        implements View.OnClickListener,
        SwipeRefreshLayout.OnRefreshListener {
    public T presenter;
    private View back;
    private SwipeRefreshLayout swipeRefreshLayout;
    public List<String> testList = new ArrayList<>();
    private LoadDialog loadDialog;
    private View emptyView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        //AndroidBug5497Workaround.assistActivity(this);
        initStatusBar();
        ButterKnife.bind(this);
        initDialog();
        initPresenter();
        initView();
        initData();
        initListener();
    }

    private void initDialog() {
        loadDialog = new LoadDialog(this);
    }

    protected void initStatusBar() {
        ImmersionBar.with(this)
                .fitsSystemWindows(true)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true)
                .init();
    }

    public void showLoading() {
        if (loadDialog != null && !loadDialog.isShowing()) loadDialog.show();
    }

    public void hideLoading() {
        if (loadDialog != null && loadDialog.isShowing()) loadDialog.dismiss();
        if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing())
            swipeRefreshLayout.setRefreshing(false);
    }

    public void showErrInfo(Throwable e) {
        try {
            LogUtil.LogE(e);
            if (e != null) {
                ToastUtil.showMessage(e.getMessage(), Toast.LENGTH_SHORT);
                LogUtil.LogI(e.getClass().getSimpleName() + "::::" + e.getMessage());
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    public View getEmpty(String message) {
        if (emptyView == null) {
            emptyView = LayoutInflater.from(this).inflate(R.layout.layout_page_state, null);
            TextView tvEmpty = emptyView.findViewById(R.id.tvEmpty);
            tvEmpty.setText(message);
        }

        return emptyView;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        KeyboardUtils.hideSoftInput(this);
        return super.dispatchTouchEvent(ev);
    }

    protected abstract int getLayoutId();

    protected abstract void initPresenter();

    protected abstract void initData();

    public void setBackVisibility(int visibility) {
        if (null != back)
            back.setVisibility(visibility);
    }

    public void setBackRes(int res) {
        if (null != back) {
            if (back instanceof ImageView) {
                ((ImageView) back).setImageResource(res);
            }
        }
    }

    protected void initView() {
        back = findViewById(R.id.back);
        swipeRefreshLayout = findViewById(R.id.swipeRefresh);
        if (back != null) back.setOnClickListener(this);
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
            swipeRefreshLayout.setOnRefreshListener(this);
        }

        for (int i = 0; i < 20; i++) {
            testList.add("别紧张，这只是一个测试而已");
        }
    }

    protected void setSwipeRefresh(boolean enable) {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setEnabled(enable);
        }
    }

    protected abstract void initListener();

    protected abstract void innerListener(View v);

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.back) {
            onBackPressed();
        }
        innerListener(v);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null)
            presenter.destroy();
        ImmersionBar.with(this).destroy(); //必须调用该方法，防止内存泄漏
    }

    @Override
    public void onBackPressed() {
        if (null != swipeRefreshLayout && swipeRefreshLayout.isRefreshing())
            swipeRefreshLayout.setRefreshing(false);
        else
            super.onBackPressed();
    }


    public void inspectPermission(String... storage) {

    }

    public void permissionFail() {//略奇葩

    }

    public void permissionSuccess() {

    }

    public void startIntentActivity(Intent intent, Class<? extends AppCompatActivity> clazz) {
        intent.setClass(this, clazz);
        startActivity(intent);
    }

    @Override
    public void onRefresh() {
        innerRefresh();
    }

    protected void innerRefresh() {

    }
}
