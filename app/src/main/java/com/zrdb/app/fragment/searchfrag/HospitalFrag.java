package com.zrdb.app.fragment.searchfrag;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zrdb.app.R;
import com.zrdb.app.adapter.SearchHosAdapter;
import com.zrdb.app.dialog.LoadDialog;
import com.zrdb.app.fragment.LazyFragment;
import com.zrdb.app.popup.AddressPopupWindow;
import com.zrdb.app.popup.DocFilterPopupWindow;
import com.zrdb.app.ui.bean.CateListBean;
import com.zrdb.app.ui.bean.CityBean;
import com.zrdb.app.ui.bean.DocFilterBean;
import com.zrdb.app.ui.bean.LevelListBean;
import com.zrdb.app.ui.bean.LoginBean;
import com.zrdb.app.ui.bean.MultipleHosBean;
import com.zrdb.app.ui.bean.ProvinceBean;
import com.zrdb.app.ui.bean.TecListBean;
import com.zrdb.app.ui.presenter.SearchHosPresenter;
import com.zrdb.app.ui.response.DocFilterResponse;
import com.zrdb.app.ui.response.SearchHosResponse;
import com.zrdb.app.ui.viewImpl.ISearchHosView;
import com.zrdb.app.util.Convert;
import com.zrdb.app.util.LogUtil;
import com.zrdb.app.util.ParamUtils;
import com.zrdb.app.util.SpUtil;
import com.zrdb.app.util.UIUtil;

import java.util.List;

import butterknife.BindView;

public class HospitalFrag extends LazyFragment<SearchHosPresenter> implements ISearchHosView,
        BaseQuickAdapter.RequestLoadMoreListener, View.OnClickListener,
        AddressPopupWindow.OnChooseAddressListener, DocFilterPopupWindow.OnChooseFilterInfoListener {

    @BindView(R.id.tvFragHosAddress)
    TextView tvFragHosAddress;
    @BindView(R.id.ivFragHosAddress)
    ImageView ivFragHosAddress;
    @BindView(R.id.llFragHosAddress)
    LinearLayout llFragHosAddress;
    @BindView(R.id.tvFragHosRank)
    TextView tvFragHosRank;
    @BindView(R.id.ivFragHosRank)
    ImageView ivFragHosRank;
    @BindView(R.id.llFragHosRank)
    LinearLayout llFragHosRank;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private String keyword;
    private String cityId = "0";
    private String cateId = "0";
    private LoginBean account;
    private SearchHosAdapter adapter;
    private List<ProvinceBean> provinceList;
    /*    private List<TecListBean> tecList;
        private List<LevelListBean> levelList;*/
    private List<CateListBean> cateList;
    private int tag = 0;//0地区选择 1筛选
    private LoadDialog loadDialog;
    private AddressPopupWindow popupWindow;
    private String docFilterInfo;
    private DocFilterPopupWindow docFilterPopupWindow;
    private String oldKeyword;

    @Override
    protected int getLayoutId() {
        return R.layout.frag_hospital;
    }

    @Override
    protected void initPresenter() {
        presenter = new SearchHosPresenter(this);
    }

    @Override
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

/*    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }*/

    @Override
    protected void fetchData() {
        Bundle bundle = getArguments();
        keyword = bundle.getString(ParamUtils.KEYWORD);
        oldKeyword = keyword;
        account = (LoginBean) SpUtil.get(SpUtil.ACCOUNT, LoginBean.class);
        initListener();
        initAdapter();
        sendNet(true);
    }

    @Override
    protected void resetFetchData() {
        super.resetFetchData();
        if (!StringUtils.equals(oldKeyword, keyword)) {
            setRefresh(true);
            innerRefresh();
            oldKeyword = keyword;
        }
    }

    private void initListener() {
        llFragHosAddress.setOnClickListener(this);
        llFragHosRank.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llFragHosAddress://选择地址
                if (provinceList == null || provinceList.size() == 0) {
                    tag = 0;
                    initLoadDialog();
                    presenter.sendNetDocFilter(account.token, account.uid);
                } else {
                    showAddressPopupWindow();
                }
                break;
            case R.id.llFragHosRank://筛选
                if (cateList == null || cateList.size() == 0) {
                    tag = 1;
                    initLoadDialog();
                    presenter.sendNetDocFilter(account.token, account.uid);
                } else {
                    showFilterPopupWindow();
                }
                break;
        }
    }

    private void showFilterPopupWindow() {
        if (docFilterPopupWindow == null) {
            docFilterPopupWindow = new DocFilterPopupWindow(getActivity(), docFilterInfo);
            docFilterPopupWindow.setOnChooseFilterInfoListener(this);
            docFilterPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    tvFragHosRank.setTextColor(UIUtil.getColor(R.color.standardTextColor));
                    ivFragHosRank.setImageResource(R.drawable.ic_gray_choose_normal);
                }
            });
        }
        if (!docFilterPopupWindow.isShowing()) {
            tvFragHosRank.setTextColor(UIUtil.getColor(R.color.colorPrimary));
            ivFragHosRank.setImageResource(R.drawable.ic_blue_choose_normal);
        }
        docFilterPopupWindow.show(llFragHosRank, Gravity.LEFT | Gravity.TOP, 0, 0);
    }

    @Override
    public void chooseFilterInfo(String tecId, String levId, String cateId) {
        this.cateId = cateId;
        sendNet(true);
    }

    private void showAddressPopupWindow() {
        //LogUtil.LogI("长度：" + provinceList.size());
        if (popupWindow == null) {
            popupWindow = new AddressPopupWindow(getActivity(), provinceList);
            popupWindow.setOnChooseAddressListener(this);
            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    tvFragHosAddress.setTextColor(UIUtil.getColor(R.color.standardTextColor));
                    ivFragHosAddress.setImageResource(R.drawable.ic_gray_choose_normal);
                }
            });
        }
        if (!popupWindow.isShowing()) {
            tvFragHosAddress.setTextColor(UIUtil.getColor(R.color.colorPrimary));
            ivFragHosAddress.setImageResource(R.drawable.ic_blue_choose_normal);
        }
        popupWindow.show(llFragHosAddress, Gravity.LEFT | Gravity.TOP, 0, 0);
    }


    @Override
    public void getAddressInfo(CityBean city) {
        cityId = city.areaId;
        tvFragHosAddress.setText(city.areaName);
        sendNet(true);
    }

    private void initLoadDialog() {
        if (loadDialog == null)
            loadDialog = new LoadDialog(getActivity());
        if (!loadDialog.isShowing())
            loadDialog.show();
    }

    private void initAdapter() {
        adapter = new SearchHosAdapter();
        adapter.setOnLoadMoreListener(this, recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
    }

    private void sendNet(boolean showDialog) {
        setRefresh(true);
        innerRefresh();
    }

    @Override
    protected void innerRefresh() {
        LogUtil.LogI("医院:" + keyword);
        page = 1;
        isRefresh = true;
        presenter.sendNetHosInfo(account.token, account.uid, keyword, cityId, cateId, page, row, true);
    }

    @Override
    public void getHosResultSuccess(String result) {
        LogUtil.LogI("医院:" + result);
        initPage(result);
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        if (loadDialog != null && loadDialog.isShowing())
            loadDialog.dismiss();
    }

    @Override
    public void getFilterResultSuccess(String result) {
        LogUtil.LogI("医院筛选:" + result);
        this.docFilterInfo = result;
        DocFilterResponse response = Convert.fromJson(result, DocFilterResponse.class);
        DocFilterBean filterBean = response.data;
        List<ProvinceBean> provinceList = filterBean.province;
        List<TecListBean> tecList = filterBean.tec_list;
        List<LevelListBean> levelList = filterBean.level_list;
        List<CateListBean> cateList = filterBean.cate_list;
        this.provinceList = provinceList;
   /*     this.tecList = tecList;
        this.levelList = levelList;*/
        this.cateList = cateList;
        if (tag == 0) {
            if (provinceList != null && provinceList.size() != 0) {
                showAddressPopupWindow();
            }
        } else {
            if (cateList != null && cateList.size() != 0) {
                showFilterPopupWindow();
            }
        }
    }

    @Override
    public void showDataErrInfo(String result) {
        LogUtil.LogI("无更多医院:" + result);
        initPage(result);
    }

    private void initPage(String result) {
        page++;
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0x100 && resultCode == 0x200) {
            // LogUtil.LogI("医院");
            keyword = data.getStringExtra(ParamUtils.KEYWORD);
            setRefresh(true);
            innerRefresh();
        }
    }

    public static Fragment newInstance(String keyword) {
        Fragment fragment = new HospitalFrag();
        Bundle bundle = new Bundle();
        bundle.putString(ParamUtils.KEYWORD, keyword);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onLoadMoreRequested() {
        presenter.sendNetHosInfo(account.token, account.uid, keyword, cityId, cateId, page, row, false);
    }
}
