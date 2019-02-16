package com.zrdb.app.ui.hospital;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zrdb.app.R;
import com.zrdb.app.image_loader.ImageLoader;
import com.zrdb.app.rxbus.RxBus;
import com.zrdb.app.ui.BaseActivity;
import com.zrdb.app.ui.bean.HospitalInfoBean;
import com.zrdb.app.ui.bean.LoginBean;
import com.zrdb.app.ui.common.SchemeActivity;
import com.zrdb.app.ui.me.MeMeanActivity;
import com.zrdb.app.ui.presenter.SubscribeHosPresenter;
import com.zrdb.app.ui.response.HosInfoResponse;
import com.zrdb.app.ui.viewImpl.ISubscribeHosView;
import com.zrdb.app.util.ApiUtils;
import com.zrdb.app.util.Convert;
import com.zrdb.app.util.LogUtil;
import com.zrdb.app.util.ParamUtils;
import com.zrdb.app.util.SpUtil;
import com.zrdb.app.util.ToastUtil;
import com.zrdb.app.watcher.SimpleTextWatcher;

import butterknife.BindView;
import io.reactivex.functions.Function;

public class SubscribeHosActivity extends BaseActivity<SubscribeHosPresenter> implements ISubscribeHosView {
    @BindView(R.id.tvActTitle)
    TextView tvActTitle;
    @BindView(R.id.tvActRightTitle)
    TextView tvActRightTitle;
    @BindView(R.id.ivToolbarRight)
    ImageView ivToolbarRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ivSubHosPic)
    ImageView ivSubHosPic;
    @BindView(R.id.tvHosSubName)
    TextView tvHosSubName;
    @BindView(R.id.tvHosSubLev)
    TextView tvHosSubLev;
    @BindView(R.id.tvHosSubCate)
    TextView tvHosSubCate;
    @BindView(R.id.tvHosSubAddress)
    TextView tvHosSubAddress;
    @BindView(R.id.etSubHosInputName)
    EditText etSubHosInputName;
    @BindView(R.id.etSubHosInputDisease)
    EditText etSubHosInputDisease;
    @BindView(R.id.etSubHosInputIllness)
    EditText etSubHosInputIllness;
    @BindView(R.id.etSubHosInputPhone)
    EditText etSubHosInputPhone;
    @BindView(R.id.tvSubHocPhone)
    TextView tvSubHocPhone;
    @BindView(R.id.cbSubHosServiceScheme)
    CheckBox cbSubHosServiceScheme;
    @BindView(R.id.tvScheme)
    TextView tvScheme;
    @BindView(R.id.btnSubHosSubmitHerd)
    Button btnSubHosSubmitHerd;
    @BindView(R.id.llRoot)
    LinearLayout llRoot;
    private LoginBean account;
    private String hosId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_subscribe_hos;
    }

    @Override
    protected void initPresenter() {
        presenter = new SubscribeHosPresenter(this);
    }

    @Override
    protected void initData() {
        hosId = getIntent().getStringExtra(ParamUtils.HOS_ID);
        account = (LoginBean) SpUtil.get(SpUtil.ACCOUNT, LoginBean.class);
        initToolbar();
        sendNet();
    }

    private void sendNet() {
        presenter.sendNetHosInfo(account.token, account.uid, hosId);
    }

    private void initToolbar() {
        tvActTitle.setText("找医院");
        tvActTitle.setVisibility(View.VISIBLE);
        setBackRes(R.drawable.ic_back);
        setBackVisibility(View.VISIBLE);
    }

    @Override
    protected void initListener() {
        tvScheme.setOnClickListener(this);
        btnSubHosSubmitHerd.setOnClickListener(this);
        etSubHosInputPhone.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                super.afterTextChanged(s);
        /*        if (!StringUtils.isEmpty(s))
                    tvSubHocPhone.setText(String.format("医生助理会联系您的手机：%s", String.valueOf(s.toString())));
                else
                    tvSubHocPhone.setText("");*/
            }
        });
    }

    @Override
    protected void innerListener(View v) {
        switch (v.getId()) {
            case R.id.tvScheme:
                startIntentActivity(new Intent().putExtra(ParamUtils.TITLE_NAME, "服务协议").putExtra(ParamUtils.URL, ApiUtils.Config.SERVICE_SCHEME_URL),
                        SchemeActivity.class
                );
                break;
            case R.id.btnSubHosSubmitHerd://提交
                submitPersonInfo();
                break;
        }
    }

    private void submitPersonInfo() {
        String name = etSubHosInputName.getText().toString().trim();
        String phone = etSubHosInputPhone.getText().toString().trim();
        String secName = etSubHosInputDisease.getText().toString().trim();
        String disease = etSubHosInputIllness.getText().toString().trim();
        if (presenter.checkInfo(name, phone, secName, disease)) {
            if (cbSubHosServiceScheme.isChecked())
                presenter.sendNetSubmitPageInfo(account.token, account.uid, hosId, name, phone, secName, disease);
            else
                ToastUtil.showMessage("请勾选服务协议！", Toast.LENGTH_SHORT);

        }
    }

    @Override
    public void getSubHosInfoSuccess(String result) {
        // LogUtil.logResult("医院", result);
        llRoot.setVisibility(View.VISIBLE);
        HosInfoResponse response = Convert.fromJson(result, HosInfoResponse.class);
        HospitalInfoBean hospitalInfo = response.data;
        ImageLoader.with(this)
                .load(String.format("%1$s%2$s", ApiUtils.Config.getImageDimen(), hospitalInfo.picture))
                .into(ivSubHosPic);
        tvHosSubName.setText(String.valueOf(hospitalInfo.name));
        tvHosSubLev.setText(String.valueOf(hospitalInfo.lev_name));
        tvHosSubCate.setText(String.valueOf(hospitalInfo.cate_name));
        tvHosSubAddress.setText(String.valueOf(hospitalInfo.address));
    }

    @Override
    public void SubmitPersonInfoSuccess(String result) {//去我的界面  关闭前面的所有界面
        LogUtil.logResult("提交", result);
        ToastUtil.showMessage("预约成功！", Toast.LENGTH_SHORT);
        RxBus.getInstance().chainProcess(new Function() {
            @Override
            public Object apply(Object o) throws Exception {
                return "关闭";//通知详情以及列表页关闭
            }
        });
        startIntentActivity(new Intent(), MeMeanActivity.class);
        finish();
    }

    @Override
    public void showDataErrInfo(String result) {

    }
}
