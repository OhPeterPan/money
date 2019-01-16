package com.zrdb.app.ui.hospital;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zrdb.app.R;
import com.zrdb.app.adapter.SearchHosAdapter;
import com.zrdb.app.popup.AddressPopupWindow;
import com.zrdb.app.popup.HosLevPopupWindow;
import com.zrdb.app.rxbus.RxBus;
import com.zrdb.app.ui.BaseActivity;
import com.zrdb.app.ui.bean.CityBean;
import com.zrdb.app.ui.bean.DocFilterBean;
import com.zrdb.app.ui.bean.LevelListBean;
import com.zrdb.app.ui.bean.LoginBean;
import com.zrdb.app.ui.bean.MultipleHosBean;
import com.zrdb.app.ui.bean.ProvinceBean;
import com.zrdb.app.ui.presenter.LookHosIndexPresenter;
import com.zrdb.app.ui.response.DocFilterResponse;
import com.zrdb.app.ui.response.SearchHosResponse;
import com.zrdb.app.ui.viewImpl.ILookHosIndexView;
import com.zrdb.app.util.Convert;
import com.zrdb.app.util.ParamUtils;
import com.zrdb.app.util.SpUtil;
import com.zrdb.app.util.UIUtil;

import java.util.List;

import butterknife.BindView;

public class LookHosIndexActivity extends BaseActivity<LookHosIndexPresenter> implements ILookHosIndexView, BaseQuickAdapter.RequestLoadMoreListener, AddressPopupWindow.OnChooseAddressListener, HosLevPopupWindow.IOnChooseLevListener, BaseQuickAdapter.OnItemClickListener {

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
    private SearchHosAdapter adapter;
    private boolean hasMore;
    private AddressPopupWindow popupWindow;
    private List<ProvinceBean> provinceList;
    private int tag = 0;//0地区选择 1筛选
    private List<LevelListBean> levelList;
    private HosLevPopupWindow levPopupWindow;

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
        RxBus.getInstance().register(presenter);
        account = (LoginBean) SpUtil.get(SpUtil.ACCOUNT, LoginBean.class);
        initToolbar();
        initAdapter();
        sendNet(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().remove(presenter);
    }

    @Override
    public void finishView() {
        finish();
    }

    private void initAdapter() {
        adapter = new SearchHosAdapter();
        adapter.setOnLoadMoreListener(this, recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(BaseQuickAdapter ada, View v, int position) {
        MultipleHosBean hosBean = adapter.getItem(position);
        if (hosBean == null) return;
        startIntentActivity(new Intent().putExtra(ParamUtils.HOS_ID, hosBean.hos_id), HosDetailActivity.class);
    }

    private void sendNet(boolean showDialog) {
        if (showDialog) {
            curPage = 1;
            isRefresh = true;
        }
        presenter.sendNetHosInfo(account.token, account.uid, areaId, levId, curPage, showDialog);
    }

    @Override
    public void getHosListResultSuccess(String result) {
        //LogUtil.logResult("医院", result);
        initPage(result);
    }

    @Override
    public void showDataErrInfo(String result) {
        initPage(result);
    }

    private void initPage(String result) {
        curPage++;
        SearchHosResponse response = Convert.fromJson(result, SearchHosResponse.class);
        List<MultipleHosBean> hosList = response.data;
        if (hosList == null || hosList.size() == 0) {
            hasMore = false;
        } else {
            hasMore = true;
        }
        if (isRefresh) {
            adapter.setNewData(hosList);
            adapter.setEmptyView(getEmpty("没有搜到相关数据~"));
        } else {
            if (hosList != null)
                adapter.addData(hosList);
        }
        if (hasMore) isRefresh = false;

        if (adapter.isLoading() && hasMore) {
            adapter.loadMoreComplete();
        }
        if (!hasMore) {
            adapter.loadMoreEnd();
        }
    }

    @Override
    public void showErrInfo(Throwable e) {
        super.showErrInfo(e);
        if (adapter != null && adapter.isLoading()) {
            adapter.loadMoreFail();
        }
    }

    @Override
    public void hosFilterResultSuccess(String result) {
        //LogUtil.LogI("医院筛选:" + result);
        DocFilterResponse response = Convert.fromJson(result, DocFilterResponse.class);
        DocFilterBean filterBean = response.data;
        List<ProvinceBean> provinceList = filterBean.province;
        List<LevelListBean> levelList = filterBean.level_list;
        this.provinceList = provinceList;
        this.levelList = levelList;
        if (tag == 0) {
            if (provinceList != null && provinceList.size() != 0) {
                showAddressPopupWindow();
            }
        } else {
            if (levelList != null && levelList.size() != 0) {
                showFilterPopupWindow();
            }
        }
    }

    @Override
    protected void initListener() {
        llHosAddress.setOnClickListener(this);
        llHosLev.setOnClickListener(this);
    }

    @Override
    protected void innerListener(View v) {
        switch (v.getId()) {
            case R.id.llHosAddress://地址
                if (provinceList == null || provinceList.size() == 0) {
                    tag = 0;
                    presenter.sendNetDocFilter(account.token, account.uid);
                } else {
                    showAddressPopupWindow();
                }
                break;
            case R.id.llHosLev://医院级别
                if (levelList == null || levelList.size() == 0) {
                    tag = 1;
                    presenter.sendNetDocFilter(account.token, account.uid);
                } else {
                    showFilterPopupWindow();
                }
                break;
        }
    }

    private void showAddressPopupWindow() {
        if (popupWindow == null) {
            popupWindow = new AddressPopupWindow(this, provinceList);
            popupWindow.setOnChooseAddressListener(this);
            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    tvHosAddress.setTextColor(UIUtil.getColor(R.color.standardTextColor));
                    ivHosAddress.setImageResource(R.drawable.ic_gray_choose_normal);
                }
            });
        }
        if (!popupWindow.isShowing()) {
            tvHosAddress.setTextColor(UIUtil.getColor(R.color.colorPrimary));
            ivHosAddress.setImageResource(R.drawable.ic_blue_choose_normal);
        }
        popupWindow.show(llHosAddress, Gravity.LEFT | Gravity.TOP, 0, 0);
    }

    @Override
    public void getAddressInfo(CityBean city) {
        areaId = city.areaId;
        tvHosAddress.setText(city.areaName);
        sendNet(true);
    }

    private void showFilterPopupWindow() {
        if (levPopupWindow == null) {
            levPopupWindow = new HosLevPopupWindow(this, levelList, R.layout.adapter_hos_lev);
            levPopupWindow.setOnChooseLevListener(this);
            levPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    tvHosLev.setTextColor(UIUtil.getColor(R.color.standardTextColor));
                    ivHosLev.setImageResource(R.drawable.ic_gray_choose_normal);
                }
            });
        }
        if (!levPopupWindow.isShowing()) {
            tvHosLev.setTextColor(UIUtil.getColor(R.color.colorPrimary));
            ivHosLev.setImageResource(R.drawable.ic_blue_choose_normal);
        }
        levPopupWindow.show(llHosLev, Gravity.LEFT | Gravity.TOP, 0, 0);
    }

    @Override
    public void chooseHosLevListener(LevelListBean levelListBean, int position) {
        tvHosLev.setText(levelListBean.name);
        levId = levelListBean.lev_id;
        sendNet(true);
    }

    @Override
    public void onLoadMoreRequested() {
        sendNet(false);
    }
}
