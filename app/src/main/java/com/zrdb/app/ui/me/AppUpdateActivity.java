package com.zrdb.app.ui.me;

import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.AppUtils;
import com.zrdb.app.R;
import com.zrdb.app.ui.BaseActivity;

import butterknife.BindView;

public class AppUpdateActivity extends BaseActivity {
    @BindView(R.id.tvActTitle)
    TextView tvActTitle;
    @BindView(R.id.ivToolbarRight)
    ImageView ivToolbarRight;
    @BindView(R.id.tvUpdateVersion)
    TextView tvUpdateVersion;
    @BindView(R.id.viewStub)
    ViewStub viewStub;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_app_update;
    }

    @Override
    protected void initPresenter() {

    }

    private void initToolbar() {
        tvActTitle.setText("版本更新");
        tvActTitle.setVisibility(View.VISIBLE);
        setBackRes(R.drawable.ic_back);
        setBackVisibility(View.VISIBLE);
        //ivToolbarRight.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initData() {
        initToolbar();
        tvUpdateVersion.setText(String.format("版本号 %s", AppUtils.getAppVersionName()));
        initViewStub();
    }

    private void initViewStub() {
        viewStub.setLayoutResource(R.layout.view_stub_no_update);
        viewStub.inflate();
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void innerListener(View v) {

    }
}
