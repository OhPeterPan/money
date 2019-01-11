package com.zrdb.director.ui.account;

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
import com.zrdb.director.R;
import com.zrdb.director.ui.BaseActivity;
import com.zrdb.director.ui.bean.LoginBean;
import com.zrdb.director.ui.bean.LoginResponse;
import com.zrdb.director.ui.main.MainActivity;
import com.zrdb.director.ui.presenter.LoginPresenter;
import com.zrdb.director.ui.viewImpl.ILoginView;
import com.zrdb.director.util.Convert;
import com.zrdb.director.util.LogUtil;
import com.zrdb.director.util.SpUtil;
import com.zrdb.director.util.ToastUtil;

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
        }
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
}
