package com.zrdb.app.ui.common;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.just.agentweb.AgentWeb;
import com.just.agentweb.IAgentWebSettings;
import com.zrdb.app.R;
import com.zrdb.app.ui.BaseActivity;
import com.zrdb.app.util.ParamUtils;
import com.zrdb.app.util.UIUtil;
import com.zrdb.app.webview.CommentAgentWebSetting;

import butterknife.BindView;

public class SchemeActivity extends BaseActivity {
    @BindView(R.id.tvActTitle)
    TextView tvActTitle;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.tvActRightTitle)
    TextView tvActRightTitle;
    @BindView(R.id.ivToolbarRight)
    ImageView ivToolbarRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.flWebViewContainer)
    FrameLayout flWebViewContainer;
    private String url;
    private AgentWeb mAgentWeb;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_scheme;
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initData() {
        url = getIntent().getStringExtra(ParamUtils.URL);
        String titleName = getIntent().getStringExtra(ParamUtils.TITLE_NAME);
        initToolbar(titleName);
        loadUrl();
    }

    private void loadUrl() {
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(flWebViewContainer, new FrameLayout.LayoutParams(-1, -1))
                .useDefaultIndicator(UIUtil.getColor(R.color.colorPrimary))
                .setAgentWebWebSettings(getSetting())
                .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
                .createAgentWeb()
                .ready()
                .go(url);
    }

    private void initToolbar(String titleName) {
        tvActTitle.setText(titleName);
        tvActTitle.setVisibility(View.VISIBLE);
        setBackRes(R.drawable.ic_back);
        setBackVisibility(View.VISIBLE);
    }

    private IAgentWebSettings getSetting() {
        return new CommentAgentWebSetting();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mAgentWeb != null) mAgentWeb.getWebLifeCycle().onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mAgentWeb != null) mAgentWeb.getWebLifeCycle().onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mAgentWeb != null) mAgentWeb.getWebLifeCycle().onDestroy();
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void innerListener(View v) {

    }
}
