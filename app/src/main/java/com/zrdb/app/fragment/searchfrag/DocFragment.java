package com.zrdb.app.fragment.searchfrag;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.zrdb.app.adapter.SearchDocAdapter;
import com.zrdb.app.custom_view.InnerSwipeRefreshLayout;
import com.zrdb.app.dialog.LoadDialog;
import com.zrdb.app.fragment.LazyFragment;
import com.zrdb.app.popup.AddressPopupWindow;
import com.zrdb.app.popup.DocFilterPopupWindow;
import com.zrdb.app.ui.bean.CateListBean;
import com.zrdb.app.ui.bean.CityBean;
import com.zrdb.app.ui.bean.DocFilterBean;
import com.zrdb.app.ui.bean.LevelListBean;
import com.zrdb.app.ui.bean.LoginBean;
import com.zrdb.app.ui.bean.MultipleDocBean;
import com.zrdb.app.ui.bean.ProvinceBean;
import com.zrdb.app.ui.bean.TecListBean;
import com.zrdb.app.ui.presenter.SearchDocPresenter;
import com.zrdb.app.ui.response.DocFilterResponse;
import com.zrdb.app.ui.response.SearchDocResponse;
import com.zrdb.app.ui.viewImpl.ISearchDocView;
import com.zrdb.app.util.Convert;
import com.zrdb.app.util.LogUtil;
import com.zrdb.app.util.ParamUtils;
import com.zrdb.app.util.SpUtil;
import com.zrdb.app.util.UIUtil;

import java.util.List;

import butterknife.BindView;

public class DocFragment extends LazyFragment<SearchDocPresenter> implements View.OnClickListener, ISearchDocView, BaseQuickAdapter.RequestLoadMoreListener, AddressPopupWindow.OnChooseAddressListener, DocFilterPopupWindow.OnChooseFilterInfoListener {

    @BindView(R.id.tvFragDocAddress)
    TextView tvFragDocAddress;
    @BindView(R.id.ivFragDocAddress)
    ImageView ivFragDocAddress;
    @BindView(R.id.llFragDocAddress)
    LinearLayout llFragDocAddress;
    @BindView(R.id.tvFragDocRank)
    TextView tvFragDocRank;
    @BindView(R.id.ivFragDocRank)
    ImageView ivFragDocRank;
    @BindView(R.id.llFragDocRank)
    LinearLayout llFragDocRank;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefresh)
    InnerSwipeRefreshLayout swipeRefresh;
    private String keyword;
    private String cityId = "0";
    private String tecId = "0";
    private String levId = "0";
    private String cateId = "0";
    private LoadDialog loadDialog;
    private LoginBean account;
    private SearchDocAdapter adapter;
    private List<ProvinceBean> provinceList;
    private List<TecListBean> tecList;
    private int tag = 0;//0地区选择 1筛选
    private AddressPopupWindow popupWindow;
    private String docFilterInfo;
    private DocFilterPopupWindow docFilterPopupWindow;
    private String oldKeyword;

    @Override
    protected int getLayoutId() {
        return R.layout.frag_doc;
    }

    @Override
    protected void initPresenter() {
        presenter = new SearchDocPresenter(this);
    }

    @Override
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

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
            sendNet(true);
            oldKeyword = keyword;
        }
    }

    private void sendNet(boolean showDialog) {
        setRefresh(showDialog);
        innerRefresh();
    }

    private void initAdapter() {
        adapter = new SearchDocAdapter();
        adapter.setOnLoadMoreListener(this, recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
    }

    private void initListener() {
        llFragDocAddress.setOnClickListener(this);
        llFragDocRank.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llFragDocAddress://选择地址
                if (provinceList == null || provinceList.size() == 0) {
                    tag = 0;
                    initLoadDialog();
                    presenter.sendNetDocFilter(account.token, account.uid);
                } else {
                    showAddressPopupWindow();
                }
                break;
            case R.id.llFragDocRank://筛选
                if (tecList == null || tecList.size() == 0) {
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
                    tvFragDocRank.setTextColor(UIUtil.getColor(R.color.standardTextColor));
                    ivFragDocRank.setImageResource(R.drawable.ic_gray_choose_normal);
                }
            });
        }
        if (!docFilterPopupWindow.isShowing()) {
            tvFragDocRank.setTextColor(UIUtil.getColor(R.color.colorPrimary));
            ivFragDocRank.setImageResource(R.drawable.ic_blue_choose_normal);
        }
        docFilterPopupWindow.show(llFragDocRank, Gravity.LEFT | Gravity.TOP, 0, 0);
    }

    @Override
    public void chooseFilterInfo(String tecId, String levId, String cateId) {
        this.tecId = tecId;
        this.levId = levId;
        this.cateId = cateId;
        sendNet(true);
    }

    private void showAddressPopupWindow() {
        LogUtil.LogI("长度：" + provinceList.size());
        if (popupWindow == null) {
            popupWindow = new AddressPopupWindow(getActivity(), provinceList);
            popupWindow.setOnChooseAddressListener(this);
            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    tvFragDocAddress.setTextColor(UIUtil.getColor(R.color.standardTextColor));
                    ivFragDocAddress.setImageResource(R.drawable.ic_gray_choose_normal);
                }
            });
        }
        if (!popupWindow.isShowing()) {
            tvFragDocAddress.setTextColor(UIUtil.getColor(R.color.colorPrimary));
            ivFragDocAddress.setImageResource(R.drawable.ic_blue_choose_normal);
        }
        popupWindow.show(llFragDocAddress, Gravity.LEFT | Gravity.TOP, 0, 0);
    }

    @Override
    public void getAddressInfo(CityBean city) {
        cityId = city.areaId;
        tvFragDocAddress.setText(city.areaName);
        sendNet(true);
    }

    private void initLoadDialog() {
        if (loadDialog == null)
            loadDialog = new LoadDialog(getActivity());
        if (!loadDialog.isShowing())
            loadDialog.show();
    }

    @Override
    protected void innerRefresh() {
        LogUtil.LogI("医生:" + keyword + "::" + tecId + "::" + levId + "::" + cateId);
        page = 1;
        isRefresh = true;
        presenter.sendNetDocInfo(account.token, account.uid, keyword, cityId, tecId, levId, cateId, page, row, true);
    }

    @Override
    public void onLoadMoreRequested() {
        presenter.sendNetDocInfo(account.token, account.uid, keyword, cityId, tecId, levId, cateId, page, row, false);
    }

    @Override
    public void getDocResultSuccess(String result) {
        LogUtil.LogI("医生列表:" + result);
        initPage(result);
    }

    private void initPage(String result) {
        page++;
        SearchDocResponse response = Convert.fromJson(result, SearchDocResponse.class);
        List<MultipleDocBean> docList = response.data;
        if (docList == null || docList.size() == 0) {
            hasMore = false;
        } else {
            hasMore = true;
        }
        if (isRefresh) {
            adapter.setNewData(docList);
            adapter.setEmptyView(getEmpty("没有搜到相关数据~"));
        } else {
            if (docList != null)
                adapter.addData(docList);
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
    public void hideLoading() {
        super.hideLoading();
        if (loadDialog != null && loadDialog.isShowing())
            loadDialog.dismiss();
    }

    @Override
    public void getFilterResultSuccess(String result) {
        LogUtil.LogI("医生筛选:" + result);
        this.docFilterInfo = result;
        DocFilterResponse response = Convert.fromJson(result, DocFilterResponse.class);
        DocFilterBean filterBean = response.data;
        List<ProvinceBean> provinceList = filterBean.province;
        List<TecListBean> tecList = filterBean.tec_list;
        List<LevelListBean> levelList = filterBean.level_list;
        List<CateListBean> cateList = filterBean.cate_list;
        this.provinceList = provinceList;
        this.tecList = tecList;
/*        this.levelList = levelList;
        this.cateList = cateList;*/
        if (tag == 0) {
            if (provinceList != null && provinceList.size() != 0) {
                showAddressPopupWindow();
            }
        } else {
            if (tecList != null && tecList.size() != 0) {
                showFilterPopupWindow();
            }
        }
    }

    @Override
    public void showDataErrInfo(String result) {
        LogUtil.LogI("无更多医生:" + result);
        initPage(result);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0x100 && resultCode == 0x200) {
            //  LogUtil.LogI("医生");
            keyword = data.getStringExtra(ParamUtils.KEYWORD);
            setRefresh(true);
            innerRefresh();
        }
    }

    public static Fragment newInstance(String keyword) {
        Fragment fragment = new DocFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ParamUtils.KEYWORD, keyword);
        fragment.setArguments(bundle);
        return fragment;
    }
}
