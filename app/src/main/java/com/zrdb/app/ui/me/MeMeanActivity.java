package com.zrdb.app.ui.me;

import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zrdb.app.R;
import com.zrdb.app.ui.BaseActivity;
import com.zrdb.app.ui.bean.LoginBean;
import com.zrdb.app.ui.bean.UserIndexBean;
import com.zrdb.app.ui.bean.UserInfoBean;
import com.zrdb.app.ui.presenter.MeMeanPresenter;
import com.zrdb.app.ui.response.UserResponse;
import com.zrdb.app.ui.viewImpl.IMeMeanModelView;
import com.zrdb.app.util.Convert;
import com.zrdb.app.util.InfoUtil;
import com.zrdb.app.util.LogUtil;
import com.zrdb.app.util.SpUtil;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;
import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

public class MeMeanActivity extends BaseActivity<MeMeanPresenter> implements IMeMeanModelView {
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
    @BindView(R.id.flMeMessage)
    FrameLayout flMeMessage;
    @BindView(R.id.civMePersonPic)
    CircleImageView civMePersonPic;
    @BindView(R.id.tvMePersonName)
    TextView tvMePersonName;
    @BindView(R.id.tvMePersonPhone)
    TextView tvMePersonPhone;
    @BindView(R.id.tvMeBespoke)
    TextView tvMeBespoke;
    @BindView(R.id.tvMeOrder)
    TextView tvMeOrder;
    @BindView(R.id.tvMeContactService)
    TextView tvMeContactService;
    @BindView(R.id.tvMeDocCard)
    TextView tvMeDocCard;
    @BindView(R.id.tvCommonQuestion)
    TextView tvCommonQuestion;
    @BindView(R.id.tvAppUpdate)
    TextView tvAppUpdate;
    @BindView(R.id.tvAboutMe)
    TextView tvAboutMe;
    @BindView(R.id.tvCacheNumber)
    TextView tvCacheNumber;
    @BindView(R.id.llClearCache)
    LinearLayout llClearCache;
    @BindView(R.id.tvMeLaw)
    TextView tvMeLaw;
    @BindView(R.id.cbAutoUpdate)
    CheckBox cbAutoUpdate;
    @BindView(R.id.btnMeMeanExitApp)
    Button btnMeMeanExitApp;
    private LoginBean account;
    private Badge badgeView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_me_mean;
    }

    @Override
    protected void initPresenter() {
        presenter = new MeMeanPresenter(this);
    }

    @Override
    protected void initData() {
        account = (LoginBean) SpUtil.get(SpUtil.ACCOUNT, LoginBean.class);
        badgeView = new QBadgeView(this).bindTarget(flMeMessage);
        initToolbar();
        sendNet();
    }

    private void sendNet() {
        // LogUtil.logResult("token", account.token);
        presenter.sendNet(account.token, account.uid);
    }

    private void initToolbar() {
        tvActTitle.setText("个人中心");
        tvActTitle.setVisibility(View.VISIBLE);
        setBackRes(R.drawable.ic_back);
        setBackVisibility(View.VISIBLE);
        //ivToolbarRight.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void innerListener(View v) {

    }

    @Override
    public void getMeInfoSuccess(String result) {
        LogUtil.logResult("个人中心", result);
        UserResponse response = Convert.fromJson(result, UserResponse.class);
        UserIndexBean userIndexBean = response.data;
        UserInfoBean userInfo = userIndexBean.userinfo;
        badgeView.setBadgeNumber(userIndexBean.message_count);
        tvMePersonName.setText(TextUtils.isEmpty(userInfo.username) ? "" : userInfo.username);
        tvMePersonPhone.setText(InfoUtil.getNumber(userInfo.phone));
    }

    @Override
    public void showDataErrInfo(String result) {

    }
}
