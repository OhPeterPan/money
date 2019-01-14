package com.zrdb.app.ui.director;

import android.content.Intent;
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
import com.zrdb.app.adapter.DirectorAdapter;
import com.zrdb.app.custom_view.InnerSwipeRefreshLayout;
import com.zrdb.app.popup.AddressPopupWindow;
import com.zrdb.app.popup.DocFilterPopupWindow;
import com.zrdb.app.popup.TecPopupWindow;
import com.zrdb.app.ui.BaseActivity;
import com.zrdb.app.ui.bean.CateListBean;
import com.zrdb.app.ui.bean.CityBean;
import com.zrdb.app.ui.bean.DirectorBean;
import com.zrdb.app.ui.bean.DiseaseBean;
import com.zrdb.app.ui.bean.DocFilterBean;
import com.zrdb.app.ui.bean.LevelListBean;
import com.zrdb.app.ui.bean.LoginBean;
import com.zrdb.app.ui.bean.ProvinceBean;
import com.zrdb.app.ui.bean.SecListBean;
import com.zrdb.app.ui.bean.TecListBean;
import com.zrdb.app.ui.presenter.DirectorDetailPresenter;
import com.zrdb.app.ui.response.DirectorResponse;
import com.zrdb.app.ui.response.DocFilterResponse;
import com.zrdb.app.ui.viewImpl.IDirectorDetailView;
import com.zrdb.app.util.Convert;
import com.zrdb.app.util.LogUtil;
import com.zrdb.app.util.ParamUtils;
import com.zrdb.app.util.SpUtil;
import com.zrdb.app.util.UIUtil;

import java.util.List;

import butterknife.BindView;

public class DirectorDetailActivity extends BaseActivity<DirectorDetailPresenter> implements IDirectorDetailView, BaseQuickAdapter.RequestLoadMoreListener, DocFilterPopupWindow.OnChooseFilterInfoListener, AddressPopupWindow.OnChooseAddressListener, TecPopupWindow.OnChooseSecListener, BaseQuickAdapter.OnItemClickListener {
    @BindView(R.id.tvActTitle)
    TextView tvActTitle;
    @BindView(R.id.ivToolbarRight)
    ImageView ivToolbarRight;
    @BindView(R.id.tvAddress)
    TextView tvAddress;
    @BindView(R.id.ivAddress)
    ImageView ivAddress;
    @BindView(R.id.llAddress)
    LinearLayout llAddress;
    @BindView(R.id.tvTechnical)
    TextView tvTechnical;
    @BindView(R.id.ivTechnical)
    ImageView ivTechnical;
    @BindView(R.id.llTechnical)
    LinearLayout llTechnical;
    @BindView(R.id.tvRank)
    TextView tvRank;
    @BindView(R.id.ivRank)
    ImageView ivRank;
    @BindView(R.id.llRank)
    LinearLayout llRank;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefresh)
    InnerSwipeRefreshLayout swipeRefresh;
    private String secId = "0";
    private String disId = "0";
    private String areaId = "0";
    private String tecId = "0";
    private String cateId = "0";
    private String levId = "0";
    private boolean hasMore = true;
    private int curPage = 1;
    private boolean isRefresh = true;
    private LoginBean account;
    private DirectorAdapter adapter;
    private List<ProvinceBean> provinceList;
    private List<TecListBean> tecList;
    private int tag = 0;//0地区选择 1筛选 2科室
    private AddressPopupWindow popupWindow;
    private String docFilterInfo;
    private DocFilterPopupWindow docFilterPopupWindow;
    private String oldSecId = "0";
    private List<SecListBean> secList;
    private TecPopupWindow tecPopupWindow;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_director;
    }

    @Override
    protected void initPresenter() {
        presenter = new DirectorDetailPresenter(this);
    }

    @Override
    protected void initData() {
        account = (LoginBean) SpUtil.get(SpUtil.ACCOUNT, LoginBean.class);
        secId = getIntent().getStringExtra(ParamUtils.SEC_ID);
        oldSecId = secId;
        String secName = getIntent().getStringExtra(ParamUtils.SEC_NAME);
        initToolbar(secName);
        initAdapter();
        sendNet(true);
    }

    private void initAdapter() {
        adapter = new DirectorAdapter();
        adapter.setOnLoadMoreListener(this, recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(BaseQuickAdapter ada, View view, int position) {
        DirectorBean directorBean = adapter.getItem(position);
        if (directorBean != null)
            startIntentActivity(new Intent().putExtra(ParamUtils.DOC_ID, directorBean.doc_id)
                    .putExtra(ParamUtils.SEC_NAME, tvActTitle.getText()), DirectorInfoActivity.class);
    }

    private void sendNet(boolean showDialog) {
        if (showDialog) {
            curPage = 1;
            isRefresh = true;
        }
        LogUtil.LogI("参数："
                + secId + "::" + disId + "::" + areaId + "::" + tecId + "::" + cateId + "::" + levId);
        presenter.sendNetGetDoc(account.token, account.uid, secId, disId, areaId, tecId, cateId, levId, curPage, showDialog);
    }

    @Override
    public void getDocListResultSuccess(String result) {
        LogUtil.logResult("找主任", result);
        if (curPage == 1)
            recyclerView.smoothScrollToPosition(0);
        initPage(result);
    }

    private void initPage(String result) {
        curPage++;
        DirectorResponse response = Convert.fromJson(result, DirectorResponse.class);
        List<DirectorBean> docList = response.data;
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
    public void showDataErrInfo(String result) {
        initPage(result);
    }

    private void initToolbar(String title) {
        setToolbarTitle(title);
        tvActTitle.setVisibility(View.VISIBLE);
        setBackRes(R.drawable.ic_back);
        setBackVisibility(View.VISIBLE);
        //ivToolbarRight.setVisibility(View.VISIBLE);
    }

    private void setToolbarTitle(String title) {
        tvActTitle.setText(title);
    }

    @Override
    protected void initListener() {
        llAddress.setOnClickListener(this);
        llTechnical.setOnClickListener(this);
        llRank.setOnClickListener(this);
    }

    @Override
    protected void innerListener(View v) {
        switch (v.getId()) {
            case R.id.llAddress://地址
                tag = 0;
                if (provinceList == null || provinceList.size() == 0 || !StringUtils.equals(oldSecId, secId)) {

                    presenter.sendNetDocFilter(account.token, account.uid, secId);
                } else {
                    showAddressPopupWindow();
                }

                break;
            case R.id.llTechnical://科室
                tag = 2;
                if (secList == null || secList.size() == 0) {
                    presenter.sendNetDocFilter(account.token, account.uid, secId);//互不干扰?
                } else {
                    showTechnicalPopupWindow();
                }

                break;
            case R.id.llRank://筛选
                tag = 1;
                if (tecList == null || tecList.size() == 0 || !StringUtils.equals(oldSecId, secId)) {
                    presenter.sendNetDocFilter(account.token, account.uid, secId);
                } else {
                    showFilterPopupWindow();
                }
                break;
        }
    }


    @Override
    public void getDocFilterSuccess(String result) {
        LogUtil.logResult("过滤", result);
        this.docFilterInfo = result;
        DocFilterResponse response = Convert.fromJson(result, DocFilterResponse.class);
        DocFilterBean filterBean = response.data;
        List<ProvinceBean> provinceList = filterBean.province;
        List<TecListBean> tecList = filterBean.tec_list;
        List<LevelListBean> levelList = filterBean.level_list;
        List<CateListBean> cateList = filterBean.cate_list;
        List<SecListBean> secList = filterBean.sec_list;
        if (provinceList != null && provinceList.size() != 0)
            this.provinceList = provinceList;
        if (tecList != null && tecList.size() != 0)
            this.tecList = tecList;
        if (secList != null && secList.size() != 0)
            this.secList = secList;
/*        this.levelList = levelList;
        this.cateList = cateList;*/
        if (!StringUtils.equals(oldSecId, secId)) {
            popupWindow = null;
            docFilterPopupWindow = null;
            tecPopupWindow = null;
        }
        if (tag == 0) {
            if (provinceList != null && provinceList.size() != 0) {
                showAddressPopupWindow();
            }
        } else if (tag == 2) {
            if (secList != null && secList.size() != 0) {
                showTechnicalPopupWindow();
            }
        } else {
            if (tecList != null && tecList.size() != 0) {

                showFilterPopupWindow();
            }
        }
        oldSecId = secId;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (tecPopupWindow != null) {
            tecPopupWindow.destroy();
        }
    }

    // TODO: 2019/1/14 操作逻辑不明
    private void showTechnicalPopupWindow() {
        if (tecPopupWindow == null) {
            tecPopupWindow = new TecPopupWindow(this, secList, secId);
            tecPopupWindow.setOnChooseSecListener(this);
            tecPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    tvTechnical.setTextColor(UIUtil.getColor(R.color.standardTextColor));
                    ivTechnical.setImageResource(R.drawable.ic_gray_choose_normal);
                }
            });
        }

        if (!tecPopupWindow.isShowing()) {
            tvTechnical.setTextColor(UIUtil.getColor(R.color.colorPrimary));
            ivTechnical.setImageResource(R.drawable.ic_blue_choose_normal);
        }

        tecPopupWindow.show(llTechnical, Gravity.LEFT | Gravity.TOP, 0, 0);
    }

    @Override
    public void getSecInfo(SecListBean secBean, DiseaseBean bean) {
        this.secId = secBean.sec_id;
        this.disId = bean.dis_id;

        tvTechnical.setText(secBean.name);
        sendNet(true);
    }

    private void showFilterPopupWindow() {
        if (docFilterPopupWindow == null) {
            docFilterPopupWindow = new DocFilterPopupWindow(this, docFilterInfo);
            docFilterPopupWindow.setOnChooseFilterInfoListener(this);
            docFilterPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    tvRank.setTextColor(UIUtil.getColor(R.color.standardTextColor));
                    ivRank.setImageResource(R.drawable.ic_gray_choose_normal);
                }
            });
        }
        if (!docFilterPopupWindow.isShowing()) {
            tvRank.setTextColor(UIUtil.getColor(R.color.colorPrimary));
            ivRank.setImageResource(R.drawable.ic_blue_choose_normal);
        }
        docFilterPopupWindow.show(llRank, Gravity.LEFT | Gravity.TOP, 0, 0);
    }

    @Override
    public void chooseFilterInfo(String tecId, String levId, String cateId) {
        this.tecId = tecId;
        this.levId = levId;
        this.cateId = cateId;
        sendNet(true);
    }

    private void showAddressPopupWindow() {
        //  LogUtil.LogI("长度：" + provinceList.size());
        if (popupWindow == null) {
            popupWindow = new AddressPopupWindow(this, provinceList);
            popupWindow.setOnChooseAddressListener(this);
            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    tvAddress.setTextColor(UIUtil.getColor(R.color.standardTextColor));
                    ivAddress.setImageResource(R.drawable.ic_gray_choose_normal);
                }
            });
        }
        if (!popupWindow.isShowing()) {
            tvAddress.setTextColor(UIUtil.getColor(R.color.colorPrimary));
            ivAddress.setImageResource(R.drawable.ic_blue_choose_normal);
        }
        popupWindow.show(llAddress, Gravity.LEFT | Gravity.TOP, 0, 0);
    }

    @Override
    public void getAddressInfo(CityBean city) {
        areaId = city.areaId;
        tvAddress.setText(city.areaName);
        sendNet(true);
    }

    @Override
    protected void innerRefresh() {
        super.innerRefresh();
        curPage = 1;
        isRefresh = true;
        sendNet(false);
    }

    @Override
    public void onLoadMoreRequested() {
        isRefresh = false;
        sendNet(false);
    }
}
