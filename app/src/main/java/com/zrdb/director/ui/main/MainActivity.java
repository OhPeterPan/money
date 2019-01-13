package com.zrdb.director.ui.main;

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
import com.zrdb.director.R;
import com.zrdb.director.adapter.FollowUpAdapter;
import com.zrdb.director.banner.BannerImpl;
import com.zrdb.director.banner.IBanner;
import com.zrdb.director.decorate.MainGridDecorate;
import com.zrdb.director.image_loader.ImageLoader;
import com.zrdb.director.test.TestActivity;
import com.zrdb.director.ui.BaseActivity;
import com.zrdb.director.ui.bean.IndexBean;
import com.zrdb.director.ui.bean.IndexListBean;
import com.zrdb.director.ui.bean.LoginBean;
import com.zrdb.director.ui.presenter.MainPresenter;
import com.zrdb.director.ui.response.MainResponse;
import com.zrdb.director.ui.viewImpl.IMainView;
import com.zrdb.director.ui.visit.IntelligentVisitActivity;
import com.zrdb.director.util.ApiUtils;
import com.zrdb.director.util.Convert;
import com.zrdb.director.util.LogUtil;
import com.zrdb.director.util.SpUtil;
import com.zrdb.director.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity<MainPresenter> implements IMainView {

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
    private LinearLayout llMainHeadIntelligentVisit;

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
        banner = headView.findViewById(R.id.banner);
        initFollowAdapter();
        adapter.addHeaderView(headView);
        llMainHeadIntelligentVisit.setOnClickListener(this);
    }

    private void initFollowAdapter() {
        followUpAdapter = new FollowUpAdapter();
        horizontalRecyclerView.setHasFixedSize(true);
        horizontalRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        horizontalRecyclerView.setAdapter(followUpAdapter);
    }

    private void initAdapter() {
        adapter = new MainAdapter();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.addItemDecoration(new MainGridDecorate());
        adapter.setHeaderAndEmpty(true);
        recyclerView.setAdapter(adapter);
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
        } else {
            ToastUtil.showMessage(String.valueOf(mainResponse.msg), Toast.LENGTH_SHORT);
        }
    }

    private void setBanner(List<IndexListBean> topSlider) {
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
          /*      String u_id = bannerList.get(position).u_id;
                FirstResultBean resultBean = new FirstResultBean();
                resultBean.u_id = u_id;
                startActivity(new Intent(getActivity(), UserDetailActivity.class).putExtra(IntentUtil.USER_BEAN, resultBean));*/
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
                    .load(ApiUtils.Config.getImageDimen() + item.image)
                    .into(ivAdapterBottom);
        }
    }
}
