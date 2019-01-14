package com.zrdb.app.ui.director;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zrdb.app.R;
import com.zrdb.app.ui.BaseActivity;
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
    EditText etDirectorSearch;
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
        initToolbar();
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
    }

    @Override
    protected void innerListener(View v) {
        switch (v.getId()) {
            case R.id.tvLookDocMedicine://内科
                secId = "9";
                secName = "内科";
                break;
            case R.id.tvLookDocSurgery://外科
                secId = "1";
                secName = "外科";
                break;
            case R.id.tvLookDocGyn://妇产科
                secId = "2";
                secName = "妇产科";
                break;
            case R.id.tvLookDocChild://儿科
                secId = "3";
                secName = "儿科";
                break;
            case R.id.tvLookDocHerbalist://中医科
                secId = "54";
                secName = "中医科";
                break;
            case R.id.tvLookDocOrthopaedics://骨科
                secId = "4";
                secName = "骨科";
                break;
            case R.id.tvLookDocDepartment://呼吸科
                secId = "55";
                secName = "呼吸科";
                break;
            case R.id.tvLookDocExtra://其它
                secId = "8";
                secName = "其它";
                break;
        }
        startIntentActivity(new Intent().putExtra(ParamUtils.SEC_ID, secId)
                        .putExtra(ParamUtils.SEC_NAME, secName),
                DirectorDetailActivity.class);
    }
}
