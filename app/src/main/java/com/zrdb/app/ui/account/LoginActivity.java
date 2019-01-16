package com.zrdb.app.ui.account;

import android.content.Context;
import android.content.Intent;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gyf.barlibrary.ImmersionBar;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zrdb.app.R;
import com.zrdb.app.ui.BaseActivity;
import com.zrdb.app.ui.bean.LoginBean;
import com.zrdb.app.ui.bean.LoginResponse;
import com.zrdb.app.ui.main.MainActivity;
import com.zrdb.app.ui.presenter.LoginPresenter;
import com.zrdb.app.ui.viewImpl.ILoginView;
import com.zrdb.app.util.ApiUtils;
import com.zrdb.app.util.Convert;
import com.zrdb.app.util.LogUtil;
import com.zrdb.app.util.ParamUtils;
import com.zrdb.app.util.SpUtil;
import com.zrdb.app.util.ToastUtil;

import butterknife.BindView;

public class LoginActivity extends BaseActivity<LoginPresenter> implements ILoginView {
    @BindView(R.id.loginPhone)
    EditText loginPhone;
    @BindView(R.id.loginPwd)
    EditText loginPwd;
    @BindView(R.id.ivLoginShowHintPwd)
    ImageView ivLoginShowHintPwd;
    @BindView(R.id.btnLogin)
    Button btnLogin;
    @BindView(R.id.tvLoginForgetPwd)
    TextView tvLoginForgetPwd;
    @BindView(R.id.tvLoginForgetRegister)
    TextView tvLoginForgetRegister;
    @BindView(R.id.ivWxLogin)
    ImageView ivWxLogin;
    private boolean pwdShow = false;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initStatusBar() {
        ImmersionBar.with(this)
                .fitsSystemWindows(true)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true)
                .init();
    }

    @Override
    protected void initPresenter() {
        presenter = new LoginPresenter(this);
    }

    @Override
    protected void initData() {
        setBackVisibility(View.VISIBLE);
    }

    @Override
    protected void initListener() {
        ivLoginShowHintPwd.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        tvLoginForgetPwd.setOnClickListener(this);
        tvLoginForgetRegister.setOnClickListener(this);
        ivWxLogin.setOnClickListener(this);
    }

    @Override
    protected void innerListener(View v) {
        switch (v.getId()) {
            case R.id.ivLoginShowHintPwd://密码展示隐藏
                changePwdShowState();
                break;
            case R.id.btnLogin:
                login();
                break;
            case R.id.tvLoginForgetPwd://忘记密码
                startIntentActivity(new Intent(), ForgetPwdActivity.class);
                break;
            case R.id.tvLoginForgetRegister://注册
                startIntentActivity(new Intent(), RegisterActivity.class);
                break;
            case R.id.ivWxLogin://微信登录
                onClickWeChatLogin();
                break;
        }
    }

    private void onClickWeChatLogin() {
        //LogUtil.LogI("来吗？");
        IWXAPI api = WXAPIFactory.createWXAPI(this, ApiUtils.Config.WX_APP_ID, true);

        if (!api.isWXAppInstalled()) {
            ToastUtil.showMessage("您手机尚未安装微信，请安装后再登录", Toast.LENGTH_SHORT);
            return;
        }

        api.registerApp(ApiUtils.Config.WX_APP_ID);
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "123";//官方说明：用于保持请求和回调的状态，授权请求后原样带回给第三方。该参数可用于防止csrf攻击（跨站请求伪造攻击），建议第三方带上该参数，可设置为简单的随机数加session进行校验
        boolean result = api.sendReq(req);
        LogUtil.LogI("结果：" + result);
    }

    private void login() {
        String phone = loginPhone.getText().toString().trim();
        String pwd = loginPwd.getText().toString().trim();
        if (presenter.checkParams(phone, pwd)) {
            presenter.sendNetLogin(phone, pwd);
        }
    }

    private void changePwdShowState() {
        if (pwdShow) {
            loginPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
        } else {
            loginPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }
        pwdShow = !pwdShow;
        loginPwd.setSelection(getPwdLength());
    }

    private int getPwdLength() {
        int length;
        String pwd = loginPwd.getText().toString().trim();
        length = pwd == null ? 0 : pwd.length();
        return length;
    }

    @Override
    public void getLoginResult(String result) {
        LogUtil.LogI("result:" + result);
        LoginResponse loginResponse = Convert.fromJson(result, LoginResponse.class);
        if (loginResponse.code == 200) {
            LoginBean loginBean = loginResponse.data;
            SpUtil.save(SpUtil.ACCOUNT, loginBean);
            startIntentActivity(new Intent(), MainActivity.class);
            ToastUtil.showMessage("登录成功", Toast.LENGTH_SHORT);
            finish();
        } else {
            ToastUtil.showMessage(String.valueOf(loginResponse.msg), Toast.LENGTH_SHORT);
        }
    }

    @Override
    public void showDataErrInfo(String result) {

    }

    public static void launchNewFlag(Context mContext) {
        Intent in = new Intent(mContext, LoginActivity.class);
        in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        in.putExtra(ParamUtils.FLAG, 1);
        mContext.startActivity(in);
    }
}
