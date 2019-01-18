package com.zrdb.app.ui.me;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.CleanUtils;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.zrdb.app.R;
import com.zrdb.app.image_loader.ImageLoader;
import com.zrdb.app.ui.BaseActivity;
import com.zrdb.app.ui.bean.LoginBean;
import com.zrdb.app.ui.bean.UserIndexBean;
import com.zrdb.app.ui.bean.UserInfoBean;
import com.zrdb.app.ui.common.SchemeActivity;
import com.zrdb.app.ui.presenter.MeMeanPresenter;
import com.zrdb.app.ui.response.UserResponse;
import com.zrdb.app.ui.viewImpl.IMeMeanModelView;
import com.zrdb.app.util.ApiUtils;
import com.zrdb.app.util.Convert;
import com.zrdb.app.util.InfoUtil;
import com.zrdb.app.util.ParamUtils;
import com.zrdb.app.util.SpUtil;
import com.zrdb.app.util.SystemUtil;
import com.zrdb.app.util.UIUtil;

import java.util.Locale;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;
import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

public class MeMeanActivity extends BaseActivity<MeMeanPresenter> implements IMeMeanModelView {
    @BindView(R.id.tvActTitle)
    TextView tvActTitle;
    @BindView(R.id.ivToolbarRight)
    ImageView ivToolbarRight;

    @BindView(R.id.ivMeEdit)
    ImageView ivMeEdit;
    @BindView(R.id.ivMeMessage)
    ImageView ivMeMessage;
    @BindView(R.id.rlMeMessage)
    RelativeLayout rlMeMessage;
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
    private int requestCode = 0x001;

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
        initToolbar();
        badgeView = new QBadgeView(this).bindTarget(rlMeMessage);
        sendNet();
    }

    private void initPage() {
        long length = FileUtils.getDirLength(UIUtil.getContext().getCacheDir());
        String dirSize = length == -1 ? "0B" : byte2FitMemorySize(length);
        tvCacheNumber.setText(dirSize);
    }

    @Override
    protected void onStart() {
        super.onStart();
        initPage();
    }

    private static String byte2FitMemorySize(final long byteNum) {
        if (byteNum < 0) {
            return "shouldn't be less than zero!";
        } else if (byteNum < 1024) {
            return String.format(Locale.getDefault(), "%.2fB", (double) byteNum);
        } else if (byteNum < 1048576) {
            return String.format(Locale.getDefault(), "%.2fKB", (double) byteNum / 1024);
        } else if (byteNum < 1073741824) {
            return String.format(Locale.getDefault(), "%.2fMB", (double) byteNum / 1048576);
        } else {
            return String.format(Locale.getDefault(), "%.2fGB", (double) byteNum / 1073741824);
        }
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
        ivMeEdit.setOnClickListener(this);
        llClearCache.setOnClickListener(this);
        tvMeBespoke.setOnClickListener(this);
        tvCommonQuestion.setOnClickListener(this);
        tvAboutMe.setOnClickListener(this);
        tvMeLaw.setOnClickListener(this);
        tvMeOrder.setOnClickListener(this);
        btnMeMeanExitApp.setOnClickListener(this);
        tvAppUpdate.setOnClickListener(this);
    }

    @Override
    protected void innerListener(View v) {
        switch (v.getId()) {
            case R.id.ivMeEdit://用户详情页
                startActivityForResult(new Intent(this, UserInfoActivity.class), requestCode);
                break;
            case R.id.llClearCache://清除缓存
                showClearCacheDialog();
                break;
            case R.id.tvMeBespoke://我的预约
                startIntentActivity(new Intent(), MeBespokeActivity.class);
                break;
            case R.id.tvCommonQuestion://常见问题
                startIntentActivity(new Intent()
                                .putExtra(ParamUtils.URL, ApiUtils.Config.COMMON_QUESTION_URL)
                                .putExtra(ParamUtils.TITLE_NAME, "常见问题")
                        , SchemeActivity.class);
                break;
            case R.id.tvAppUpdate://版本更新
                startIntentActivity(new Intent(), AppUpdateActivity.class);
                break;
            case R.id.tvAboutMe://关于我们
                startIntentActivity(new Intent()
                                .putExtra(ParamUtils.URL, ApiUtils.Config.ABOUT_ME_URL)
                                .putExtra(ParamUtils.TITLE_NAME, "关于我们")
                        , SchemeActivity.class);
                break;
            case R.id.tvMeLaw://法律条款
                startIntentActivity(new Intent()
                                .putExtra(ParamUtils.URL, ApiUtils.Config.SERVICE_SCHEME_URL)
                                .putExtra(ParamUtils.TITLE_NAME, "法律声明")
                        , SchemeActivity.class);
                break;
            case R.id.tvMeOrder://我的订单
                startIntentActivity(new Intent(), MeOrderActivity.class);
                break;
            case R.id.btnMeMeanExitApp:
                showExitAppDialog();
                break;
        }
    }

    private void showExitAppDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("确定退出登录？");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //  CacheUtils.getInstance().c
                SystemUtil.exitApp(MeMeanActivity.this);
            }
        });
        builder.setNegativeButton("取消", null);
        builder.show();
    }

    private void showClearCacheDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("确认清除缓存？");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //  CacheUtils.getInstance().c
                CleanUtils.cleanInternalCache();
            }
        });
        builder.setNegativeButton("取消", null);
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 0x002) {
            if (requestCode == requestCode) {
                sendNet();
            }
        }
    }

    @Override
    public void getMeInfoSuccess(String result) {
        //LogUtil.logResult("个人中心", result);
        UserResponse response = Convert.fromJson(result, UserResponse.class);
        UserIndexBean userIndexBean = response.data;
        UserInfoBean userInfo = userIndexBean.userinfo;
        badgeView.setBadgeNumber(userIndexBean.message_count);
        tvMePersonName.setText(TextUtils.isEmpty(userInfo.username) ? "" : userInfo.username);
        tvMePersonPhone.setText(InfoUtil.getNumber(userInfo.phone));
        if (!StringUtils.isEmpty(userInfo.thumb)) {
            ImageLoader.with(this)
                    .load(String.format("%1$s%2$s", ApiUtils.Config.getImageDimen(), userInfo.thumb))
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(civMePersonPic);
        }
    }

    @Override
    public void showDataErrInfo(String result) {

    }
}
