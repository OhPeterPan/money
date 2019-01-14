package com.zrdb.app.ui.director;

import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.zrdb.app.R;
import com.zrdb.app.image_loader.ImageLoader;
import com.zrdb.app.ui.BaseActivity;
import com.zrdb.app.ui.bean.DocDetailBean;
import com.zrdb.app.ui.bean.LoginBean;
import com.zrdb.app.ui.presenter.DirectorInfoPresenter;
import com.zrdb.app.ui.response.DocResponse;
import com.zrdb.app.ui.viewImpl.IDirectorInfoView;
import com.zrdb.app.util.ApiUtils;
import com.zrdb.app.util.Convert;
import com.zrdb.app.util.LogUtil;
import com.zrdb.app.util.ParamUtils;
import com.zrdb.app.util.SpUtil;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

public class DirectorInfoActivity extends BaseActivity<DirectorInfoPresenter> implements IDirectorInfoView {

    @BindView(R.id.llRoot)
    LinearLayout llRoot;
    @BindView(R.id.tvActTitle)
    TextView tvActTitle;
    @BindView(R.id.ivToolbarRight)
    ImageView ivToolbarRight;
    @BindView(R.id.civDocInfoPic)
    CircleImageView civDocInfoPic;
    @BindView(R.id.tvDirectorName)
    TextView tvDirectorName;
    @BindView(R.id.tvDirectorOffice)
    TextView tvDirectorOffice;
    @BindView(R.id.tvDocProfessor)
    TextView tvDocProfessor;
    @BindView(R.id.tvDocPlaceHos)
    TextView tvDocPlaceHos;
    @BindView(R.id.tvDocHosScale)
    TextView tvDocHosScale;
    @BindView(R.id.tvAdapterDocGood)
    TextView tvAdapterDocGood;
    @BindView(R.id.tvDocInfoPercent)
    TextView tvDocInfoPercent;
    @BindView(R.id.tvDocDetailSerNumber)
    TextView tvDocDetailSerNumber;
    @BindView(R.id.tvDocReplyPercent)
    TextView tvDocReplyPercent;
    @BindView(R.id.tvDocDetailRank)
    TextView tvDocDetailRank;
    @BindView(R.id.tvDocDetailExcels)
    TextView tvDocDetailExcels;
    @BindView(R.id.expandable_text)
    TextView expandableText;
    @BindView(R.id.expand_collapse)
    ImageButton expandCollapse;
    @BindView(R.id.expand_text_view)
    ExpandableTextView expandTextView;
    @BindView(R.id.tvDocCustomService)
    TextView tvDocCustomService;
    @BindView(R.id.tvDocApply)
    TextView tvDocApply;
    private LoginBean account;
    private String docId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_director_detail;
    }

    @Override
    protected void initPresenter() {
        presenter = new DirectorInfoPresenter(this);
    }

    @Override
    protected void initData() {
        account = (LoginBean) SpUtil.get(SpUtil.ACCOUNT, LoginBean.class);
        String secName = getIntent().getStringExtra(ParamUtils.SEC_NAME);
        docId = getIntent().getStringExtra(ParamUtils.DOC_ID);
        tvActTitle.setText(secName);
        llRoot.setVisibility(View.INVISIBLE);
        sendNet();
    }

    private void sendNet() {
        presenter.sendNet(account.token, account.uid, docId);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void innerListener(View v) {

    }

    @Override
    public void getDocInfoSuccess(String result) {
        LogUtil.logResult("医生详情", result);
        DocResponse response = Convert.fromJson(result, DocResponse.class);
        DocDetailBean docDetail = response.data;

        expandTextView.setText(Html.fromHtml(docDetail.introduce));
        ImageLoader.with(this).
                load(String.format("%1$s%2$s", ApiUtils.Config.getImageDimen(), docDetail.picture))
                .into(civDocInfoPic);
        tvDirectorName.setText(String.valueOf(docDetail.realname));
        tvDirectorOffice.setText(String.valueOf(docDetail.sec_name));
        tvDocProfessor.setText(String.valueOf(docDetail.tec_name));
        tvDocPlaceHos.setText(String.valueOf(docDetail.hos_name));
        tvDocHosScale.setText(String.valueOf(docDetail.lev_name));
        tvAdapterDocGood.setText(String.format("擅长：%s", String.valueOf(docDetail.excels)));
        tvDocInfoPercent.setText("接 诊 率");
        tvDocReplyPercent.setText("回复率100.0%");
        serNumber(docDetail.round);
        if (llRoot.getVisibility() == View.INVISIBLE)
            llRoot.setVisibility(View.VISIBLE);
    }

    private void serNumber(int round) {
        SpannableString sp = new SpannableString(String.format("服 务 人 数 %d人", round));
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#f89e24"));
        StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
        RelativeSizeSpan sizeSpanBig = new RelativeSizeSpan(1.2f);
        RelativeSizeSpan sizeSpanSmall = new RelativeSizeSpan(0.8f);
        sp.setSpan(colorSpan, 7, sp.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        sp.setSpan(boldSpan, 7, sp.length() - 1, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        sp.setSpan(sizeSpanBig, 7, sp.length() - 1, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        sp.setSpan(sizeSpanSmall, sp.length() - 1, sp.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);

        tvDocDetailSerNumber.setText(sp);
    }

    @Override
    public void showDataErrInfo(Object result) {

    }
}
