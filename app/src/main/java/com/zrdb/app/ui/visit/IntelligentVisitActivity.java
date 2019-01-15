package com.zrdb.app.ui.visit;

import android.content.Intent;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gyf.barlibrary.ImmersionBar;
import com.lzy.okgo.model.HttpParams;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagFlowLayout;
import com.zrdb.app.R;
import com.zrdb.app.adapter.IntelligentVisitAdapter;
import com.zrdb.app.albumconfig.AlbumConfig;
import com.zrdb.app.image_loader.ImageLoader;
import com.zrdb.app.imagecompress.ImageCompress;
import com.zrdb.app.ui.BaseActivity;
import com.zrdb.app.ui.bean.LoginBean;
import com.zrdb.app.ui.common.SchemeActivity;
import com.zrdb.app.ui.presenter.IntelligentPresenter;
import com.zrdb.app.ui.response.StrResponse;
import com.zrdb.app.ui.viewImpl.IIntelligentView;
import com.zrdb.app.util.ApiUtils;
import com.zrdb.app.util.Convert;
import com.zrdb.app.util.LogUtil;
import com.zrdb.app.util.ParamUtils;
import com.zrdb.app.util.SpUtil;
import com.zrdb.app.util.ToastUtil;
import com.zrdb.app.util.UIUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;

public class IntelligentVisitActivity extends BaseActivity<IntelligentPresenter> implements IIntelligentView, AlbumConfig.IUploadListener, ImageCompress.CompressImageListener, TagFlowLayout.OnTagClickListener {
    @BindView(R.id.tvActTitle)
    TextView tvActTitle;
    @BindView(R.id.ivToolbarRight)
    ImageView ivToolbarRight;
    @BindView(R.id.etIntelInputName)
    EditText etIntelInputName;
    @BindView(R.id.etIntelInputPhone)
    EditText etIntelInputPhone;
    @BindView(R.id.tvIntelChooseAddress)
    TextView tvIntelChooseAddress;
    @BindView(R.id.tagIntelFlow)
    TagFlowLayout tagIntelFlow;
    @BindView(R.id.tagIntelHerdFlow)
    TagFlowLayout tagIntelHerdFlow;
    @BindView(R.id.etInputHerdDetail)
    EditText etInputHerdDetail;
    @BindView(R.id.ivIntelChooseHerdPic)
    ImageView ivIntelChooseHerdPic;
    @BindView(R.id.cbIntelServiceScheme)
    CheckBox cbIntelServiceScheme;
    @BindView(R.id.tvSchemeDetail)
    TextView tvSchemeDetail;
    @BindView(R.id.tvImageState)
    TextView tvImageState;
    @BindView(R.id.btnSubmitHerd)
    Button btnSubmitHerd;
    private List<String> pictures = new ArrayList<>();
    private List<String> persons = Arrays.asList(UIUtil.getStringArray(R.array.person));
    private List<String> symptoms = Arrays.asList(UIUtil.getStringArray(R.array.symptom));
    private AlbumConfig albumConfig;
    private ImageCompress imageCompress;
    private LoginBean account;
    private String human;
    private String tag;

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
        return R.layout.activity_intelligent_visit;
    }

    @Override
    protected void initPresenter() {
        presenter = new IntelligentPresenter(this);
    }

    @Override
    protected void initData() {
        account = (LoginBean) SpUtil.get(SpUtil.ACCOUNT, LoginBean.class);
        initToolbar();
        initFlow();
        initPage();
    }

    private void initPage() {
        SpannableString sp = new SpannableString("添加图片(可选项)");
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#cac7c8"));
        RelativeSizeSpan sizeSpan = new RelativeSizeSpan(0.8f);
        sp.setSpan(colorSpan, 4, sp.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        sp.setSpan(sizeSpan, 4, sp.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        tvImageState.setText(sp);
    }

    private void initFlow() {
        tagIntelFlow.setAdapter(new IntelligentVisitAdapter(persons, this) {
            @Override
            protected void setLayoutParams(TextView tv) {
                ViewGroup.LayoutParams lp = tv.getLayoutParams();
                lp.width = (ScreenUtils.getScreenWidth() - SizeUtils.dp2px(12) * 2 - SizeUtils.dp2px(5) * persons.size() * 2) / persons.size();
            }
        });
        tagIntelHerdFlow.setAdapter(new IntelligentVisitAdapter(symptoms, this) {

            @Override
            protected void setLayoutParams(TextView tv) {

            }
        });
    }

    private void initToolbar() {
        tvActTitle.setText("智能就诊");
        tvActTitle.setVisibility(View.VISIBLE);
        setBackRes(R.drawable.ic_back);
        setBackVisibility(View.VISIBLE);
        ivToolbarRight.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initListener() {
        tvSchemeDetail.setOnClickListener(this);
        ivIntelChooseHerdPic.setOnClickListener(this);
        btnSubmitHerd.setOnClickListener(this);
        tagIntelFlow.setOnTagClickListener(this);
        tagIntelHerdFlow.setOnTagClickListener(this);
    }

    @Override
    protected void innerListener(View v) {
        switch (v.getId()) {
            case R.id.tvSchemeDetail://服务协议
                startIntentActivity(new Intent().putExtra(ParamUtils.TITLE_NAME, "服务协议").putExtra(ParamUtils.URL, ApiUtils.Config.SERVICE_SCHEME_URL),
                        SchemeActivity.class
                );
                break;
            case R.id.ivIntelChooseHerdPic://选择图片
                chooseImage(true);
                break;
            case R.id.btnSubmitHerd://提交
                submitInfo();
                break;
        }
    }

    private void submitInfo() {
        String name = etIntelInputName.getText().toString().trim();
        String phone = etIntelInputPhone.getText().toString().trim();
        String address = tvIntelChooseAddress.getText().toString().trim();
        String detail = etInputHerdDetail.getText().toString().trim();
        if (presenter.checkInfo(name, phone, address, human, tag, detail)) {
            if (cbIntelServiceScheme.isChecked()) {
                presenter.sendNetSubmitInfo(account.token, account.uid, name, phone, address, human, tag, detail, pictures);
            } else {
                ToastUtil.showMessage("请同意服务协议！", Toast.LENGTH_SHORT);
            }
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
        List<HttpParams.FileWrapper> list = new LinkedList<>();
        list.add(new HttpParams.FileWrapper(file, file.getName(), HttpParams.MEDIA_TYPE_JSON));
        presenter.sendNetUploadPic(account.token, account.uid, list);
    }

    @Override
    public void uploadPicSuccess(String result) {
        LogUtil.logResult("上传图片成功", result);
        pictures.clear();
        StrResponse strResponse = Convert.fromJson(result, StrResponse.class);
        ImageLoader.with(this).load(String.format("%1$s%2$s", ApiUtils.Config.getImageDimen(), strResponse.data)).skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(ivIntelChooseHerdPic);
        pictures.add(strResponse.data);
    }

    @Override
    public void submitPageResultSuccess(String result) {
        LogUtil.logResult("提交成功", result);
        ToastUtil.showMessage("提交成功", Toast.LENGTH_SHORT);
        finish();
    }

    @Override
    public void showDataErrInfo(String result) {

    }

    /**
     * tagIntelFlow.setOnTagClickListener(this);
     * tagIntelHerdFlow.s
     *
     * @param view
     * @param position
     * @param v
     * @return
     */
    @Override
    public boolean onTagClick(View view, int position, FlowLayout v) {
        switch (v.getId()) {
            case R.id.tagIntelFlow:
                human = persons.get(position);
                break;
            case R.id.tagIntelHerdFlow:
                tag = symptoms.get(position);
                break;
        }
        return true;
    }
}
