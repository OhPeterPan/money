package com.zrdb.app.ui.hospital;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.zrdb.app.R;
import com.zrdb.app.adapter.SearchDocAdapter;
import com.zrdb.app.image_loader.ImageLoader;
import com.zrdb.app.popup.HosSecPopupWindow;
import com.zrdb.app.popup.HosTecPopupWindow;
import com.zrdb.app.ui.BaseActivity;
import com.zrdb.app.ui.bean.HosDetailBean;
import com.zrdb.app.ui.bean.HospitalInfoBean;
import com.zrdb.app.ui.bean.LoginBean;
import com.zrdb.app.ui.bean.MultipleDocBean;
import com.zrdb.app.ui.bean.SecListBean;
import com.zrdb.app.ui.bean.TecListBean;
import com.zrdb.app.ui.presenter.HosDetailPresenter;
import com.zrdb.app.ui.response.DocDetailResponse;
import com.zrdb.app.ui.response.SearchDocResponse;
import com.zrdb.app.ui.viewImpl.IHosDetailView;
import com.zrdb.app.util.ApiUtils;
import com.zrdb.app.util.Convert;
import com.zrdb.app.util.LogUtil;
import com.zrdb.app.util.ParamUtils;
import com.zrdb.app.util.SpUtil;
import com.zrdb.app.util.ToastUtil;
import com.zrdb.app.util.UIUtil;

import java.util.List;

import butterknife.BindView;

public class HosDetailActivity extends BaseActivity<HosDetailPresenter> implements IHosDetailView, BaseQuickAdapter.RequestLoadMoreListener, BaseQuickAdapter.OnItemClickListener, HosSecPopupWindow.IOnChooseSecListener, HosTecPopupWindow.IOnChooseTecListener {
    @BindView(R.id.tvActTitle)
    TextView tvActTitle;
    @BindView(R.id.ivToolbarRight)
    ImageView ivToolbarRight;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.tvActRightTitle)
    TextView tvActRightTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ivHosDetailPic)
    ImageView ivHosDetailPic;
    @BindView(R.id.tvHosDetailName)
    TextView tvHosDetailName;
    @BindView(R.id.tvHosDetailLev)
    TextView tvHosDetailLev;
    @BindView(R.id.tvHosDetailCate)
    TextView tvHosDetailCate;
    @BindView(R.id.tvHosDetailAddress)
    TextView tvHosDetailAddress;
    @BindView(R.id.expandable_text)
    TextView expandableText;
    @BindView(R.id.expand_collapse)
    ImageButton expandCollapse;
    @BindView(R.id.expand_text_view)
    ExpandableTextView expandTextView;
    @BindView(R.id.tvHosDocSec)
    TextView tvHosDocSec;
    @BindView(R.id.tvHosApply)
    TextView tvHosApply;
    @BindView(R.id.ivHosDocSec)
    ImageView ivHosDocSec;
    @BindView(R.id.llHosDocSec)
    LinearLayout llHosDocSec;
    @BindView(R.id.tvHosDocTec)
    TextView tvHosDocTec;
    @BindView(R.id.ivHosDocTec)
    ImageView ivHosDocTec;
    @BindView(R.id.llHosDocTec)
    LinearLayout llHosDocTec;
    @BindView(R.id.llHosHeadFilter)
    LinearLayout llHosHeadFilter;
    private String hosId;
    private String tecId = "0";//等级
    private String secId = "0";//科室
    private int curPage = 1;
    private boolean isRefresh = true;
    private boolean hasMore;
    private LoginBean account;
    private SearchDocAdapter adapter;
    private View headView;
    private List<SecListBean> secList;//科室
    private List<TecListBean> tecList;//等级
    private HosSecPopupWindow secPopupWindow;
    private HosTecPopupWindow tecPopupWindow;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_hos_detail;
    }

    @Override
    protected void initPresenter() {
        presenter = new HosDetailPresenter(this);
    }

    @Override
    protected void initData() {
        hosId = getIntent().getStringExtra(ParamUtils.HOS_ID);
        account = (LoginBean) SpUtil.get(SpUtil.ACCOUNT, LoginBean.class);
        initToolbar();
        sendNetHosDetail();
    }

    private void initAdapter() {
        adapter = new SearchDocAdapter();
        adapter.setOnLoadMoreListener(this, recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setHeaderAndEmpty(true);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {//去医生详情
        //ToastUtil.showMessage("可以吗", Toast.LENGTH_SHORT);
    }

    private void sendNetHosDetail() {
        presenter.sendNetHosDetail(account.token, account.uid, hosId);
    }

    @Override
    public void getHosDetailSuccess(String result) {
        LogUtil.logResult("医院详情", result);
        initAdapter();
        //initHeadView();
        DocDetailResponse response = Convert.fromJson(result, DocDetailResponse.class);
        HosDetailBean hosInfo = response.data;
        secList = hosInfo.sec_list;
        tecList = hosInfo.tec_list;
        HospitalInfoBean hospitalInfo = hosInfo.hospital_info;
        ImageLoader.with(this)
                .load(String.format("%1$s%2$s", ApiUtils.Config.getImageDimen(), hospitalInfo.picture))
                .into(ivHosDetailPic);
        tvHosDetailName.setText(String.valueOf(hospitalInfo.name));
        tvHosDetailLev.setText(String.valueOf(hospitalInfo.lev_name));
        tvHosDetailCate.setText(String.valueOf(hospitalInfo.cate_name));
        tvHosDetailAddress.setText(String.valueOf(hospitalInfo.address));
        expandTextView.setText(Html.fromHtml(hospitalInfo.introduce));
        sendNetDoc(true);
    }

    private void sendNetDoc(boolean showDialog) {
        if (showDialog) {
            curPage = 1;
            isRefresh = true;
        }
        presenter.sendNetHosDoc(account.token, account.uid, hosId, tecId, secId, curPage, showDialog);
    }

    @Override
    public void getHosDocResultSuccess(String result) {
        LogUtil.logResult("医生", result);
        initPage(result);
    }

    @Override
    public void showDataErrInfo(String result) {
        initPage(result);
    }

    private void initPage(String result) {
        if (recyclerView != null && curPage == 0) recyclerView.smoothScrollToPosition(0);
        curPage++;
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
    public void showErrInfo(Throwable e) {
        super.showErrInfo(e);
        if (adapter != null && adapter.isLoading()) {
            adapter.loadMoreFail();
        }
    }

    private void initToolbar() {
        tvActTitle.setText("找医院");
        tvActTitle.setVisibility(View.VISIBLE);
        setBackRes(R.drawable.ic_back);
        setBackVisibility(View.VISIBLE);
    }

    @Override
    protected void initListener() {
        llHosDocSec.setOnClickListener(this);
        llHosDocTec.setOnClickListener(this);
        tvHosApply.setOnClickListener(this);
    }

    @Override
    protected void innerListener(View v) {
        switch (v.getId()) {
            case R.id.llHosDocSec://全部科室
                if (secList != null && secList.size() != 0) {
                    showSecPopupWindow();
                } else {
                    ToastUtil.showMessage("无科室数据！", Toast.LENGTH_SHORT);
                }
                break;
            case R.id.llHosDocTec://医生等级
                if (tecList != null && tecList.size() != 0) {
                    showTecPopupWindow();
                } else {
                    ToastUtil.showMessage("无医生等级！", Toast.LENGTH_SHORT);
                }
                break;
            case R.id.tvHosApply://立即预约
                startIntentActivity(new Intent().putExtra(ParamUtils.HOS_ID, hosId), SubscribeHosActivity.class);

                break;
        }
    }

    private void showSecPopupWindow() {

        if (secPopupWindow == null) {
            secPopupWindow = new HosSecPopupWindow(this, secList, R.layout.adapter_hos_lev);
            secPopupWindow.setOnChooseSecListener(this);
            secPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    tvHosDocSec.setTextColor(UIUtil.getColor(R.color.standardTextColor));
                    ivHosDocSec.setImageResource(R.drawable.ic_gray_choose_normal);
                }
            });
        }
        if (!secPopupWindow.isShowing()) {
            tvHosDocSec.setTextColor(UIUtil.getColor(R.color.colorPrimary));
            ivHosDocSec.setImageResource(R.drawable.ic_blue_choose_normal);
        }
        secPopupWindow.show(llHosDocSec, Gravity.LEFT | Gravity.TOP, 0, 0);
    }

    @Override
    public void chooseHosSecListener(SecListBean bean, int position) {
        secId = bean.sec_id;
        tvHosDocSec.setText(bean.name);
        sendNetDoc(true);
    }

    private void showTecPopupWindow() {
        if (tecPopupWindow == null) {
            tecPopupWindow = new HosTecPopupWindow(this, tecList, R.layout.adapter_hos_lev);
            tecPopupWindow.setOnChooseTecListener(this);
            tecPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    tvHosDocTec.setTextColor(UIUtil.getColor(R.color.standardTextColor));
                    ivHosDocTec.setImageResource(R.drawable.ic_gray_choose_normal);
                }
            });
        }
        if (!tecPopupWindow.isShowing()) {
            tvHosDocTec.setTextColor(UIUtil.getColor(R.color.colorPrimary));
            ivHosDocTec.setImageResource(R.drawable.ic_blue_choose_normal);
        }
        tecPopupWindow.show(llHosDocTec, Gravity.LEFT | Gravity.TOP, 0, 0);
    }

    @Override
    public void chooseHosTecListener(TecListBean bean, int position) {
        tecId = bean.tec_id;
        tvHosDocTec.setText(bean.name);
        sendNetDoc(true);
    }

    @Override
    public void onLoadMoreRequested() {
        sendNetDoc(false);
    }
}
