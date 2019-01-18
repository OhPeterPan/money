package com.zrdb.app.ui.main;

import android.content.Intent;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.zrdb.app.R;
import com.zrdb.app.albumconfig.AlbumConfig;
import com.zrdb.app.image_loader.ImageLoader;
import com.zrdb.app.imagecompress.ImageCompress;
import com.zrdb.app.popup.TecJobPopupWindow;
import com.zrdb.app.ui.BaseActivity;
import com.zrdb.app.ui.bean.LoginBean;
import com.zrdb.app.ui.bean.TecListBean;
import com.zrdb.app.ui.common.SchemeActivity;
import com.zrdb.app.ui.presenter.DocAddPresenter;
import com.zrdb.app.ui.response.DocJobResponse;
import com.zrdb.app.ui.response.StrResponse;
import com.zrdb.app.ui.viewImpl.IDocAddView;
import com.zrdb.app.util.ApiUtils;
import com.zrdb.app.util.Convert;
import com.zrdb.app.util.LogUtil;
import com.zrdb.app.util.ParamUtils;
import com.zrdb.app.util.SpUtil;
import com.zrdb.app.util.ToastUtil;

import java.io.File;
import java.util.List;

import butterknife.BindView;

public class DocAddActivity extends BaseActivity<DocAddPresenter> implements AlbumConfig.IUploadListener, ImageCompress.CompressImageListener, IDocAddView, TecJobPopupWindow.OnChooseDocJobListener {
    @BindView(R.id.tvActTitle)
    TextView tvActTitle;
    @BindView(R.id.ivToolbarRight)
    ImageView ivToolbarRight;
    @BindView(R.id.etDocInputName)
    EditText etDocInputName;
    @BindView(R.id.etDocInputPhone)
    EditText etDocInputPhone;
    @BindView(R.id.etDocInputWorkAddress)
    EditText etDocInputWorkAddress;
    @BindView(R.id.etDocInputOffices)
    EditText etDocInputOffices;
    @BindView(R.id.ivDocChooseHerdPic)
    ImageView ivDocChooseHerdPic;
    @BindView(R.id.tvDocImageState)
    TextView tvDocImageState;
    @BindView(R.id.cbDocServiceScheme)
    CheckBox cbDocServiceScheme;
    @BindView(R.id.tvAddDocSchemeDetail)
    TextView tvAddDocSchemeDetail;
    @BindView(R.id.tvDocChooseJob)
    TextView tvDocChooseJob;
    @BindView(R.id.btnSubmitDocInfo)
    Button btnSubmitDocInfo;
    private LoginBean account;
    private AlbumConfig albumConfig;
    private ImageCompress imageCompress;
    private String picture;
    private List<TecListBean> tecList;
    private TecJobPopupWindow tecJobPopupWindow;
    private String tecId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_doc_add;
    }

    @Override
    protected void initPresenter() {
        presenter = new DocAddPresenter(this);
    }

    private void initToolbar() {
        tvActTitle.setText("加入平台");
        tvActTitle.setVisibility(View.VISIBLE);
        setBackRes(R.drawable.ic_back);
        setBackVisibility(View.VISIBLE);
        //ivToolbarRight.setVisibility(View.VISIBLE);
    }

    private void initPage() {
        SpannableString sp = new SpannableString("添加图片(必选项)");
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#cac7c8"));
        sp.setSpan(colorSpan, 4, sp.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        tvDocImageState.setText(sp);
    }

    @Override
    protected void initData() {
        account = (LoginBean) SpUtil.get(SpUtil.ACCOUNT, LoginBean.class);
        initToolbar();
        initPage();
    }

    @Override
    protected void initListener() {
        ivDocChooseHerdPic.setOnClickListener(this);
        tvAddDocSchemeDetail.setOnClickListener(this);
        btnSubmitDocInfo.setOnClickListener(this);
        tvDocChooseJob.setOnClickListener(this);
    }

    @Override
    protected void innerListener(View v) {
        switch (v.getId()) {
            case R.id.ivDocChooseHerdPic://图片
                chooseImage(true);
                break;
            case R.id.tvAddDocSchemeDetail:
                startIntentActivity(new Intent().putExtra(ParamUtils.TITLE_NAME, "服务协议").putExtra(ParamUtils.URL, ApiUtils.Config.SERVICE_SCHEME_URL),
                        SchemeActivity.class
                );
                break;
            case R.id.tvDocChooseJob://选择医生职称
                if (tecList == null || tecList.isEmpty())
                    presenter.sendNetGetDocJob(account.token, account.uid);
                else
                    showDocJobPopupWindow(tecList);
                break;
            case R.id.btnSubmitDocInfo://提交
                submitDocInfo();
                break;
        }
    }

    private void submitDocInfo() {
        String name = etDocInputName.getText().toString().trim();
        String phone = etDocInputPhone.getText().toString().trim();
        String address = etDocInputWorkAddress.getText().toString().trim();
        String offices = etDocInputOffices.getText().toString().trim();
        if (presenter.checkInfo(name, phone, address, offices, tecId, picture)) {
            if (cbDocServiceScheme.isChecked()) {
                presenter.sendNetUploadDocInfo(account.token, account.uid, name, phone, address, offices, tecId, picture);
            } else {
                ToastUtil.showMessage("请勾选服务协议！", Toast.LENGTH_SHORT);
            }
        }
    }

    private void showDocJobPopupWindow(List<TecListBean> tecList) {
        if (tecJobPopupWindow == null) {
            tecJobPopupWindow = new TecJobPopupWindow(this, tecList, R.layout.adapter_tec_job);
            tecJobPopupWindow.setOnChooseDocJobListener(this);
            tecJobPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    tvDocChooseJob.setSelected(false);
                }
            });
        }
        if (!tecJobPopupWindow.isShowing())
            tvDocChooseJob.setSelected(true);
        tecJobPopupWindow.show(tvDocChooseJob);
    }

    @Override
    public void onDocJobListener(TecListBean tecListBean) {
        tvDocChooseJob.setText(tecListBean.name);
        tecId = tecListBean.tec_id;
    }

    @Override
    public void docJobInfoSuccess(String result) {
        // LogUtil.LogI("职称：" + result);
        DocJobResponse response = Convert.fromJson(result, DocJobResponse.class);
        List<TecListBean> tecList = response.data;
        this.tecList = tecList;
        if (tecList != null && !tecList.isEmpty()) {
            showDocJobPopupWindow(tecList);
        }
    }

    private void chooseImage(boolean isSingle) {
        initAlbumConfig();
        if (isSingle)
            albumConfig.singleImage(true);
    }

    /**
     * 选择图片的配置信息
     */
    private void initAlbumConfig() {
        if (albumConfig == null) {
            albumConfig = new AlbumConfig(this);
            albumConfig.setILoadImageListener(this);
        }
    }

    @Override
    public void getImagePathList(List<String> imagePathList) {
        imageCompress(imagePathList.get(0));
    }

    @Override
    public void getImagePath(String imagePath) {
        imageCompress(imagePath);
    }

    private void imageCompress(String imagePath) {
        LogUtil.logResult("文件本地地址", imagePath);
        if (imageCompress == null) {
            imageCompress = new ImageCompress(this);
            imageCompress.setOnCompleteCompressListener(this);
        }
        imageCompress.setFile(new File(imagePath));
        imageCompress.startCompress();
    }

    @Override
    public void onCompleteCompress(File file) {
        presenter.sendNetUploadPic(account.token, account.uid, file);
    }

    @Override
    public void uploadPictureSuccess(String result) {
        StrResponse strResponse = Convert.fromJson(result, StrResponse.class);
        ImageLoader.with(this).load(String.format("%1$s%2$s", ApiUtils.Config.getImageDimen(), strResponse.data)).skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(ivDocChooseHerdPic);
        picture = strResponse.data;
    }

    @Override
    public void addDocResultSuccess(String result) {
        ToastUtil.showMessage("加入平台成功！", Toast.LENGTH_LONG);
        finish();
    }

    @Override
    public void showDataErrInfo(String result) {

    }
}
