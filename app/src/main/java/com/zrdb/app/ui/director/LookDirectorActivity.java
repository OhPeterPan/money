package com.zrdb.app.ui.director;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.zrdb.app.R;
import com.zrdb.app.annotation.Register;
import com.zrdb.app.rxbus.RxBus;
import com.zrdb.app.ui.BaseActivity;
import com.zrdb.app.ui.main.SearchActivity;
import com.zrdb.app.util.ParamUtils;

import butterknife.BindView;

public class LookDirectorActivity extends BaseActivity {
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
    @BindView(R.id.etDirectorSearch)
    TextView etDirectorSearch;
    @BindView(R.id.tvLookDocMedicine)
    TextView tvLookDocMedicine;
    @BindView(R.id.tvLookDocSurgery)
    TextView tvLookDocSurgery;
    @BindView(R.id.tvLookDocGyn)
    TextView tvLookDocGyn;
    @BindView(R.id.tvLookDocChild)
    TextView tvLookDocChild;
    @BindView(R.id.tvLookDocHerbalist)
    TextView tvLookDocHerbalist;
    @BindView(R.id.tvLookDocOrthopaedics)
    TextView tvLookDocOrthopaedics;
    @BindView(R.id.tvLookDocDepartment)
    TextView tvLookDocDepartment;
    @BindView(R.id.tvLookDocExtra)
    TextView tvLookDocExtra;
    private String secId = "";
    private String secName = "";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_look_director;
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initData() {
        RxBus.getInstance().register(this);
        initToolbar();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().remove(this);
    }

    @Register
    public void finishView(String message) {
        if (StringUtils.equals("关闭", message))
            finish();
    }

    private void initToolbar() {
        tvActTitle.setText("找主任");
        tvActTitle.setVisibility(View.VISIBLE);
        setBackRes(R.drawable.ic_back);
        setBackVisibility(View.VISIBLE);
        //ivToolbarRight.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initListener() {
        tvLookDocMedicine.setOnClickListener(this);
        tvLookDocSurgery.setOnClickListener(this);
        tvLookDocGyn.setOnClickListener(this);
        tvLookDocChild.setOnClickListener(this);
        tvLookDocHerbalist.setOnClickListener(this);
        tvLookDocOrthopaedics.setOnClickListener(this);
        tvLookDocDepartment.setOnClickListener(this);
        tvLookDocExtra.setOnClickListener(this);
        etDirectorSearch.setOnClickListener(this);
    }

    @Override
    protected void innerListener(View v) {
        switch (v.getId()) {
            case R.id.tvLookDocMedicine://内科
                startDirectorDetail("9", "内科");
                break;
            case R.id.tvLookDocSurgery://外科
                startDirectorDetail("1", "外科");
                break;
            case R.id.tvLookDocGyn://妇产科
                startDirectorDetail("2", "妇产科");
                break;
            case R.id.tvLookDocChild://儿科
                startDirectorDetail("3", "儿科");
                break;
            case R.id.tvLookDocHerbalist://中医科
                startDirectorDetail("54", "中医科");
                break;
            case R.id.tvLookDocOrthopaedics://骨科
                startDirectorDetail("4", "骨科");
                break;
            case R.id.tvLookDocDepartment://呼吸科
                startDirectorDetail("55", "呼吸科");
                break;
            case R.id.tvLookDocExtra://其它
                startDirectorDetail("8", "其它");
                break;
            case R.id.etDirectorSearch://搜索
                startIntentActivity(new Intent(), SearchActivity.class);
                break;
        }
    }

    private void startDirectorDetail(String id, String name) {
        secId = id;
        secName = name;
        startIntentActivity(new Intent().putExtra(ParamUtils.SEC_ID, secId)
                        .putExtra(ParamUtils.SEC_NAME, secName),
                DirectorDetailActivity.class);
    }
}
