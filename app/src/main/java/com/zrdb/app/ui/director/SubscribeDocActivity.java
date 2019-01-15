package com.zrdb.app.ui.director;

import android.content.Intent;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.StringUtils;
import com.zrdb.app.R;
import com.zrdb.app.image_loader.ImageLoader;
import com.zrdb.app.ui.BaseActivity;
import com.zrdb.app.ui.bean.DocDetailBean;
import com.zrdb.app.ui.bean.LoginBean;
import com.zrdb.app.ui.common.SchemeActivity;
import com.zrdb.app.ui.presenter.SubscribeDocPresenter;
import com.zrdb.app.ui.response.DocResponse;
import com.zrdb.app.ui.viewImpl.ISubscribeDocView;
import com.zrdb.app.util.ApiUtils;
import com.zrdb.app.util.Convert;
import com.zrdb.app.util.LogUtil;
import com.zrdb.app.util.ParamUtils;
import com.zrdb.app.util.SpUtil;
import com.zrdb.app.util.ToastUtil;
import com.zrdb.app.watcher.SimpleTextWatcher;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

public class SubscribeDocActivity extends BaseActivity<SubscribeDocPresenter> implements ISubscribeDocView {
    @BindView(R.id.tvActTitle)
    TextView tvActTitle;
    @BindView(R.id.ivToolbarRight)
    ImageView ivToolbarRight;
    @BindView(R.id.civSubDirectorPic)
    CircleImageView civSubDirectorPic;
    @BindView(R.id.tvSubDirectorName)
    TextView tvSubDirectorName;
    @BindView(R.id.tvSubDirectorOffice)
    TextView tvSubDirectorOffice;
    @BindView(R.id.tvSubMulTitleProfessor)
    TextView tvSubMulTitleProfessor;
    @BindView(R.id.tvSubHosScale)
    TextView tvSubHosScale;
    @BindView(R.id.tvSubPlaceHos)
    TextView tvSubPlaceHos;
    @BindView(R.id.etSubInputName)
    EditText etSubInputName;
    @BindView(R.id.etSubInputPhone)
    EditText etSubInputPhone;
    @BindView(R.id.etSubInputDisease)
    EditText etSubInputDisease;
    @BindView(R.id.tvSubDocPhone)
    TextView tvSubDocPhone;
    @BindView(R.id.cbSubServiceScheme)
    CheckBox cbSubServiceScheme;
    @BindView(R.id.tvScheme)
    TextView tvScheme;
    @BindView(R.id.btnSubscribeSubmitHerd)
    Button btnSubscribeSubmitHerd;
    @BindView(R.id.llRoot)
    LinearLayout llRoot;
    private LoginBean account;
    private String docId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_subscribe_doc;
    }

    @Override
    protected void initPresenter() {
        presenter = new SubscribeDocPresenter(this);
    }

    @Override
    protected void initData() {
        account = (LoginBean) SpUtil.get(SpUtil.ACCOUNT, LoginBean.class);
        docId = getIntent().getStringExtra(ParamUtils.DOC_ID);
        initToolbar();
        sendNet();
    }

    private void sendNet() {
        presenter.sendNetSubDocInfo(account.token, account.uid, docId);
    }

    private void initToolbar() {
        tvActTitle.setText("预约主任");
        tvActTitle.setVisibility(View.VISIBLE);
        setBackRes(R.drawable.ic_back);
        setBackVisibility(View.VISIBLE);
        //ivToolbarRight.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initListener() {
        btnSubscribeSubmitHerd.setOnClickListener(this);
        tvScheme.setOnClickListener(this);
        etSubInputPhone.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                super.afterTextChanged(s);
                if (!StringUtils.isEmpty(s))
                    tvSubDocPhone.setText(String.format("医生助理会联系您的手机：%s", String.valueOf(s.toString())));
                else
                    tvSubDocPhone.setText("");
            }
        });
    }

    @Override
    protected void innerListener(View v) {
        switch (v.getId()) {
            case R.id.btnSubscribeSubmitHerd:
                submitPageInfo();
                break;
            case R.id.tvScheme:
                startIntentActivity(new Intent().putExtra(ParamUtils.TITLE_NAME, "服务协议").putExtra(ParamUtils.URL, ApiUtils.Config.SERVICE_SCHEME_URL),
                        SchemeActivity.class
                );
                break;
        }
    }

    private void submitPageInfo() {
        String name = etSubInputName.getText().toString().trim();
        String phone = etSubInputPhone.getText().toString().trim();
        String disease = etSubInputDisease.getText().toString().trim();
        if (presenter.checkInfo(name, phone, disease)) {
            if (cbSubServiceScheme.isChecked())
                presenter.sendNetSubmitPageInfo(account.token, account.uid, docId, name, phone, disease);
            else
                ToastUtil.showMessage("请勾选服务协议！", Toast.LENGTH_SHORT);
        }
    }

    @Override
    public void subDocInfoSuccess(String result) {
        LogUtil.logResult("医生", result);

        DocResponse response = Convert.fromJson(result, DocResponse.class);
        DocDetailBean docDetail = response.data;
        ImageLoader.with(this).
                load(String.format("%1$s%2$s", ApiUtils.Config.getImageDimen(), docDetail.picture))
                .into(civSubDirectorPic);
        tvSubDirectorName.setText(String.valueOf(docDetail.realname));
        tvSubDirectorOffice.setText(String.valueOf(docDetail.sec_name));
        tvSubMulTitleProfessor.setText(String.valueOf(docDetail.tec_name));
        tvSubHosScale.setText(String.valueOf(docDetail.lev_name));
        tvSubPlaceHos.setText(String.valueOf(docDetail.hos_name));

        llRoot.setVisibility(View.VISIBLE);
    }

    @Override
    public void submitSubPersonSuccess(String result) {
        LogUtil.logResult("提交", result);
    }

    @Override
    public void showDataErrInfo(Object result) {

    }
}
