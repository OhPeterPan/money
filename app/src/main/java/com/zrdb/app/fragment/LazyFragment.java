package com.zrdb.app.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.zrdb.app.R;
import com.zrdb.app.image_loader.ImageLoader;
import com.zrdb.app.ui.presenter.BasePresenter;
import com.zrdb.app.util.LogUtil;
import com.zrdb.app.util.ToastUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class LazyFragment<T extends BasePresenter> extends Fragment
        implements SwipeRefreshLayout.OnRefreshListener {
    private boolean isInitViewPage = false;//是否初始化过界面
    private boolean isVisibility = false;//是否处于可见状态
    private boolean isInitData = false;//是否已经加载过数据
    public View view;
    public T presenter;
    private Unbinder unbinder;
    private SwipeRefreshLayout swipeRefreshLayout;
    public int page = 1;
    public String row = "10";
    public boolean hasMore;
    public boolean isRefresh = true;

 /*   @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // LogUtil.LogI(getClass().getSimpleName() + ".onCreate()");
    }*/

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // LogUtil.LogI(getClass().getSimpleName() + ".onCreateView()" + view);
        if (view == null) {
            view = inflater.inflate(getLayoutId(), container, false);
            unbinder = ButterKnife.bind(this, view);
            swipeRefreshLayout = view.findViewById(R.id.swipeRefresh);
            initSwipeRefresh();
            initPageShowState();
            isInitViewPage = true;
        }
        lazyData();
        return view;
    }

    //初始化view的时候为view界面填充数据
    protected void initPageShowState() {

    }

    private void initSwipeRefresh() {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
            swipeRefreshLayout.setOnRefreshListener(this);
        }
    }


    public void showLoading() {
        if (swipeRefreshLayout != null && !swipeRefreshLayout.isRefreshing()) {
            setRefresh(true);
        }
    }


    public void hideLoading() {
        if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()) {
            setRefresh(false);
        }
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

    protected abstract int getLayoutId();

    public void setRefresh(boolean refresh) {
        if (swipeRefreshLayout != null)
            swipeRefreshLayout.setRefreshing(refresh);
    }

    public View getEmpty(String message) {

        View emptyView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_page_state, null);
        TextView tvEmpty = emptyView.findViewById(R.id.tvEmpty);
        tvEmpty.setText(message);

        return emptyView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //LogUtil.LogI(getClass().getSimpleName() + ".setUserVisibleHint()" + ":" + isVisibleToUser);
        isVisibility = isVisibleToUser;
        if (isVisibleToUser) {
            lazyData();
        }
    }

    private void lazyData() {

        if (isInitViewPage && isVisibility) {
            if (!isInitData) {
                initPresenter();
                fetchData();
                isInitData = true;
            } else {
                resetFetchData();
            }
        }
    }

    //数据已经填充后的加载
    protected void resetFetchData() {
        LogUtil.LogI("再来?");
    }

    @Override
    public void onDestroyView() {
        //LogUtil.LogI(getClass().getSimpleName() + ".onDestroyView()");
        if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        LogUtil.LogI(getClass().getSimpleName() + ".onDestroy()");
        if (unbinder != null) unbinder.unbind();
        if (presenter != null) presenter.destroy();
        isInitViewPage = false;
        isInitData = false;
        view = null;
        ImageLoader.onDestroy();
        super.onDestroy();
    }

    public Context getFragContext() {
        return getActivity();
    }

    protected abstract void initPresenter();

    protected abstract void fetchData();

    @Override
    public void onRefresh() {
        innerRefresh();
    }

    protected abstract void innerRefresh();

    public void setKeyword(String keyword) {

    }
}
