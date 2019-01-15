package com.zrdb.app.ui.hospital;

import android.support.v7.widget.LinearLayoutManager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.zrdb.app.R;
import com.zrdb.app.adapter.SearchDocAdapter;
import com.zrdb.app.custom_view.ScrollRecyclerView;
import com.zrdb.app.image_loader.ImageLoader;
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

import java.util.List;

import butterknife.BindView;

public class HosDetailActivity extends BaseActivity<HosDetailPresenter> implements IHosDetailView, BaseQuickAdapter.RequestLoadMoreListener, BaseQuickAdapter.OnItemClickListener {
    @BindView(R.id.tvActTitle)
    TextView tvActTitle;
    @BindView(R.id.ivToolbarRight)
    ImageView ivToolbarRight;
    @BindView(R.id.recyclerView)
    ScrollRecyclerView recyclerView;
    private String hosId;
    private String tecId = "0";//等级
    private String secId = "0";//科室
    private int curPage = 1;
    private boolean isRefresh = true;
    private boolean hasMore;
    private LoginBean account;
    private ImageView ivHosDetailPic;
    private TextView tvHosDetailName;
    private TextView tvHosDetailLev;
    private TextView tvHosDetailCate;
    private TextView tvHosDetailAddress;
    private ExpandableTextView expandableText;
    private SearchDocAdapter adapter;
    private View headView;
    private List<SecListBean> secList;//科室
    private List<TecListBean> tecList;//等级
    private LinearLayout llHosDocSec;
    private LinearLayout llHosDocTec;

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

    private void initHeadView() {
        headView = LayoutInflater.from(this).inflate(R.layout.view_hos_detail_head, recyclerView, false);
        ivHosDetailPic = headView.findViewById(R.id.ivHosDetailPic);
        tvHosDetailName = headView.findViewById(R.id.tvHosDetailName);
        tvHosDetailLev = headView.findViewById(R.id.tvHosDetailLev);
        tvHosDetailCate = headView.findViewById(R.id.tvHosDetailCate);
        tvHosDetailAddress = headView.findViewById(R.id.tvHosDetailAddress);
        expandableText = headView.findViewById(R.id.expand_text_view);
        llHosDocSec = headView.findViewById(R.id.llHosDocSec);
        llHosDocTec = headView.findViewById(R.id.llHosDocTec);
        llHosDocSec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showMessage("能响应吗", Toast.LENGTH_SHORT);
            }
        });
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
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        ToastUtil.showMessage("可以吗", Toast.LENGTH_SHORT);
    }

    private void sendNetHosDetail() {
        presenter.sendNetHosDetail(account.token, account.uid, hosId);
    }

    @Override
    public void getHosDetailSuccess(String result) {
        LogUtil.logResult("医院详情", result);
        initAdapter();
        initHeadView();
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
        expandableText.setText(Html.fromHtml(hospitalInfo.introduce));
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
        if (adapter.getHeaderLayoutCount() == 0) {
            adapter.addHeaderView(headView);
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

    }

    @Override
    protected void innerListener(View v) {

    }

    @Override
    public void onLoadMoreRequested() {
        sendNetDoc(false);
    }

}
