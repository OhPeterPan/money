package com.zrdb.app.ui.main;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gyf.barlibrary.ImmersionBar;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.zrdb.app.R;
import com.zrdb.app.adapter.FollowUpAdapter;
import com.zrdb.app.banner.BannerImpl;
import com.zrdb.app.banner.IBanner;
import com.zrdb.app.decorate.MainGridDecorate;
import com.zrdb.app.image_loader.ImageLoader;
import com.zrdb.app.test.TestActivity;
import com.zrdb.app.ui.BaseActivity;
import com.zrdb.app.ui.bean.IndexBean;
import com.zrdb.app.ui.bean.IndexListBean;
import com.zrdb.app.ui.bean.LoginBean;
import com.zrdb.app.ui.card.CardExhibitionActivity;
import com.zrdb.app.ui.common.SchemeActivity;
import com.zrdb.app.ui.director.LookDirectorActivity;
import com.zrdb.app.ui.hospital.LookHosIndexActivity;
import com.zrdb.app.ui.me.MeMeanActivity;
import com.zrdb.app.ui.me.MeOrderActivity;
import com.zrdb.app.ui.presenter.MainPresenter;
import com.zrdb.app.ui.response.MainResponse;
import com.zrdb.app.ui.response.StrResponse;
import com.zrdb.app.ui.viewImpl.IMainView;
import com.zrdb.app.ui.visit.IntelligentVisitActivity;
import com.zrdb.app.util.ApiUtils;
import com.zrdb.app.util.Convert;
import com.zrdb.app.util.LogUtil;
import com.zrdb.app.util.ParamUtils;
import com.zrdb.app.util.SpUtil;
import com.zrdb.app.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity<MainPresenter> implements IMainView, BaseQuickAdapter.OnItemClickListener {

    @BindView(R.id.ivMainMessage)
    ImageView ivMainMessage;
    @BindView(R.id.ivMainShare)
    ImageView ivMainShare;
    @BindView(R.id.tvMainSearch)
    TextView tvMainSearch;

    @BindView(R.id.tvMainService)
    TextView tvMainService;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private LoginBean account;
    private MainAdapter adapter;
    private RecyclerView horizontalRecyclerView;
    private View headView;
    private FollowUpAdapter followUpAdapter;
    private Banner banner;
    private IBanner bannerImpl;
    private LinearLayout llMainHeadIntelligentVisit, llMainLookHos;
    private LinearLayout llMainLookDirector;
    private LinearLayout llMainMeMean;
    private LinearLayout llMessageOnLine;

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
        presenter = new MainPresenter(this);
    }

    @Override
    protected void initData() {
        account = (LoginBean) SpUtil.get(SpUtil.ACCOUNT, LoginBean.class);
        initAdapter();
        initHeadView();
        sendNet();
    }

    private void sendNet() {
        presenter.sendNet(account.token, account.uid);
    }

    private void initHeadView() {
        headView = LayoutInflater.from(this).inflate(R.layout.view_main_head, recyclerView, false);
        horizontalRecyclerView = headView.findViewById(R.id.horizontalRecyclerView);
        llMainHeadIntelligentVisit = headView.findViewById(R.id.llMainHeadIntelligentVisit);
        llMainLookHos = headView.findViewById(R.id.llMainLookHos);
        llMainLookDirector = headView.findViewById(R.id.llMainLookDirector);
        llMainMeMean = headView.findViewById(R.id.llMainMeMean);
        llMessageOnLine = headView.findViewById(R.id.llMessageOnLine);
        banner = headView.findViewById(R.id.banner);
        initFollowAdapter();
        llMainHeadIntelligentVisit.setOnClickListener(this);
        llMainLookDirector.setOnClickListener(this);
        llMainLookHos.setOnClickListener(this);
        llMainMeMean.setOnClickListener(this);
        llMessageOnLine.setOnClickListener(this);
    }

    private void initFollowAdapter() {
        followUpAdapter = new FollowUpAdapter();
        horizontalRecyclerView.setHasFixedSize(true);
        horizontalRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        horizontalRecyclerView.setAdapter(followUpAdapter);
        followUpAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                IndexListBean bean = (IndexListBean) adapter.getItem(position);
                String url = bean.link;
                startIntentActivity(new Intent()
                        .putExtra(ParamUtils.URL, url)
                        .putExtra(ParamUtils.TITLE_NAME, String.valueOf(bean.name)), SchemeActivity.class);
            }
        });
    }

    private void initAdapter() {
        adapter = new MainAdapter();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.addItemDecoration(new MainGridDecorate());
        adapter.setHeaderAndEmpty(true);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(BaseQuickAdapter ada, View view, int position) {
        IndexListBean adapterItem = adapter.getItem(position);
        if (adapterItem == null) return;
        switch (adapterItem.name) {
            case "医疗保障卡":
                presenter.sendNetCardState(account.token, account.uid);
                break;
            case "医生加入":
                startIntentActivity(new Intent(), DocAddActivity.class);
                break;
            case "医疗保险":
            case "专家会诊":
                ToastUtil.showMessage("暂未开放,敬请期待~", Toast.LENGTH_SHORT);
                break;
        }
    }

    @Override
    protected void initListener() {
        tvMainSearch.setOnClickListener(this);
        ivMainMessage.setOnClickListener(this);
        ivMainShare.setOnClickListener(this);
    }

    @Override
    protected void innerListener(View v) {
        switch (v.getId()) {
            case R.id.tvMainSearch://搜索
                startIntentActivity(new Intent(), SearchActivity.class);
                break;
            case R.id.ivMainMessage://消息
                startIntentActivity(new Intent(), MessageActivity.class);
                break;
            case R.id.ivMainShare://分享
                startIntentActivity(new Intent(), TestActivity.class);
                break;
            case R.id.llMainHeadIntelligentVisit://智能就诊
                startIntentActivity(new Intent(), IntelligentVisitActivity.class);
                break;
            case R.id.llMainLookDirector://找主任
                startIntentActivity(new Intent(), LookDirectorActivity.class);
                break;
            case R.id.llMainLookHos://找医院
                startIntentActivity(new Intent(), LookHosIndexActivity.class);
                break;
            case R.id.llMainMeMean://个人中心
                startIntentActivity(new Intent(), MeMeanActivity.class);
                break;
            case R.id.llMessageOnLine://在线咨询
                startIntentActivity(new Intent(), MessageOnLineActivity.class);
                break;
        }
    }

    @Override
    public void getMainInfoSuccess(String info) {
        LogUtil.LogI("result:" + info);
        MainResponse mainResponse = Convert.fromJson(info, MainResponse.class);
        if (mainResponse.code == 200) {
            IndexBean indexBean = mainResponse.data;
            List<IndexListBean> topSlider = indexBean.top_slider;
            adapter.setNewData(indexBean.bottom_slider);
            followUpAdapter.setNewData(indexBean.middle_slider);
            tvMainService.setVisibility(View.VISIBLE);
            setBanner(topSlider);
            if (adapter.getHeaderLayoutCount() == 0) {
                adapter.addHeaderView(headView);
            }
        } else {
            ToastUtil.showMessage(String.valueOf(mainResponse.msg), Toast.LENGTH_SHORT);
        }
    }

    @Override
    public void getCardStateSuccess(String result) {
        LogUtil.LogI("保障卡：" + result);
        StrResponse response = Convert.fromJson(result, StrResponse.class);
        String state = response.data;
        switch (state) {
            case "0"://没有购买
                //startIntentActivity(new Intent(), BuyCardActivity.class);
                startIntentActivity(new Intent(), CardExhibitionActivity.class);
                break;
            case "1"://未支付 去我的订单页面
                startIntentActivity(new Intent(), MeOrderActivity.class);
                break;
            case "3"://已购买 去我的保障卡界面

                break;
        }
    }

    private void setBanner(final List<IndexListBean> topSlider) {
        List<String> arrayListImages = new ArrayList();
        List<String> arrayListTitles = new ArrayList();

        for (IndexListBean bean : topSlider) {
            arrayListImages.add(String.format("%1$s%2$s", ApiUtils.Config.getImageDimen(), bean.image));
            arrayListTitles.add(bean.name);
        }

        bannerImpl = new BannerImpl(this, banner)
                .setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE)
                .setImages(arrayListImages)
                .setBannerAnimation(Transformer.Default)
                .setBannerTitles(arrayListTitles)
                .setIndicatorGravity(BannerConfig.CENTER);
        bannerImpl.setImageLoader(new BannerImpl.GlideImageLoader((BannerImpl) bannerImpl));
        bannerImpl.start();
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                String url = topSlider.get(position).link;
                startIntentActivity(new Intent()
                        .putExtra(ParamUtils.URL, url)
                        .putExtra(ParamUtils.TITLE_NAME, "介绍"), SchemeActivity.class);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (bannerImpl != null)
            bannerImpl.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (bannerImpl != null)
            bannerImpl.onStop();
    }

    @Override
    public void showDataErrInfo(String result) {

    }

    private class MainAdapter extends BaseQuickAdapter<IndexListBean, BaseViewHolder> {

        public MainAdapter() {
            super(R.layout.adapter_main, null);
        }

        @Override
        protected void convert(BaseViewHolder helper, IndexListBean item) {
            ImageView ivAdapterBottom = helper.getView(R.id.ivAdapterBottom);
            ImageLoader.with(mContext)
                    .skipMemoryCache(true)
                    .load(ApiUtils.Config.getImageDimen() + item.image)
                    .into(ivAdapterBottom);
        }
    }
}
