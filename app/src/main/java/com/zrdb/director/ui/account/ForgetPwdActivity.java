package com.zrdb.director.ui.account;

import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zrdb.director.R;
import com.zrdb.director.ui.BaseActivity;
import com.zrdb.director.ui.presenter.ForgetPwdPresenter;
import com.zrdb.director.ui.response.RegisterResponse;
import com.zrdb.director.ui.viewImpl.IForgetPwdView;
import com.zrdb.director.util.Convert;
import com.zrdb.director.util.ToastUtil;
import com.zrdb.director.util.UIUtil;

import butterknife.BindView;

public class ForgetPwdActivity extends BaseActivity<ForgetPwdPresenter> implements IForgetPwdView {

    @BindView(R.id.forgetPwdPhone)
    EditText forgetPwdPhone;
    @BindView(R.id.ivForgetPwdClearPhone)
    ImageView ivForgetPwdClearPhone;
    @BindView(R.id.tvGetVerify)
    TextView tvGetVerify;
    @BindView(R.id.forgetPwdVerify)
    EditText forgetPwdVerify;
    @BindView(R.id.etInputNewPwd)
    EditText etInputNewPwd;
    @BindView(R.id.btnConfirmChangePwd)
    Button btnConfirmChangePwd;
    /**
     * 验证码倒计时
     */
    private CountDownTimer mCountDownTimer = new CountDownTimer(60000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            tvGetVerify.setEnabled(false);
            tvGetVerify.setText(String.format(UIUtil.getString(R.string.after_time_resend), millisUntilFinished / 1000));
        }

        @Override
        public void onFinish() {
            tvGetVerify.setEnabled(true);
            tvGetVerify.setText(UIUtil.getString(R.string.resend));
            cancel();
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_forget_pwd;
    }

    @Override
    protected void initPresenter() {
        presenter = new ForgetPwdPresenter(this);
    }

    @Override
    protected void initData() {
        setBackVisibility(View.VISIBLE);
    }

    @Override
    protected void initListener() {
        ivForgetPwdClearPhone.setOnClickListener(this);
        tvGetVerify.setOnClickListener(this);
        btnConfirmChangePwd.setOnClickListener(this);
    }

    @Override
    protected void innerListener(View v) {
        switch (v.getId()) {
            case R.id.ivForgetPwdClearPhone:
                forgetPwdPhone.setText("");
                break;
            case R.id.tvGetVerify://获取验证码
                getVerifyCode();
                break;
            case R.id.btnConfirmChangePwd:
                confirmChangePwd();
                break;
        }
    }

    private void confirmChangePwd() {
        String phone = forgetPwdPhone.getText().toString().trim();
        String pwd = etInputNewPwd.getText().toString().trim();
        String verify = forgetPwdVerify.getText().toString().trim();
        if (presenter.checkInfo(phone,pwd,verify)) {
            presenter.sendNetChangePwd(phone,pwd,verify);
        }
    }

    private void getVerifyCode() {
        String phone = forgetPwdPhone.getText().toString().trim();
        if (presenter.checkPhone(phone)) {
            presenter.sendNetGetVerify(phone);
        }
    }

    @Override
    public void getVerifyResultSuccess(String result) {
        RegisterResponse registerResponse = Convert.fromJson(result, RegisterResponse.class);
        if (registerResponse.code == 200) {
            mCountDownTimer.start();
            ToastUtil.showMessage("验证码已发送，请注意查收！", Toast.LENGTH_SHORT);
        } else {
            ToastUtil.showMessage(registerResponse.msg, Toast.LENGTH_SHORT);
        }
    }

    @Override
    public void changePwdResultSuccess(String result) {
        RegisterResponse registerResponse = Convert.fromJson(result, RegisterResponse.class);
        if (registerResponse.code == 200) {
            ToastUtil.showMessage("密码修改成功", Toast.LENGTH_SHORT);
            finish();
        } else {
            ToastUtil.showMessage(registerResponse.msg, Toast.LENGTH_SHORT);
        }
    }

    @Override
    public void showDataErrInfo(String result) {

    }
}
