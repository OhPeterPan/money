package com.zrdb.director.ui.account;

import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zrdb.director.R;
import com.zrdb.director.ui.BaseActivity;
import com.zrdb.director.util.UIUtil;

import butterknife.BindView;

public class RegisterActivity extends BaseActivity {

    @BindView(R.id.etRegisterPhone)
    EditText etRegisterPhone;
    @BindView(R.id.ivRegisterClearPhone)
    ImageView ivRegisterClearPhone;
    @BindView(R.id.tvRegisterGetVerify)
    TextView tvRegisterGetVerify;
    @BindView(R.id.etRegisterVerify)
    EditText etRegisterVerify;
    @BindView(R.id.etRegisterInputNewPwd)
    EditText etRegisterInputNewPwd;
    @BindView(R.id.etNextRegisterInputPwd)
    EditText etNextRegisterInputPwd;
    @BindView(R.id.btnConfirmChangePwd)
    Button btnConfirmChangePwd;
    /**
     * 验证码倒计时
     */
    private CountDownTimer mCountDownTimer = new CountDownTimer(60000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            tvRegisterGetVerify.setEnabled(false);
            tvRegisterGetVerify.setText(String.format(UIUtil.getString(R.string.after_time_resend), millisUntilFinished / 1000));
        }

        @Override
        public void onFinish() {
            tvRegisterGetVerify.setEnabled(true);
            tvRegisterGetVerify.setText(UIUtil.getString(R.string.resend));
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
        return R.layout.activity_register;
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initData() {
        setBackVisibility(View.VISIBLE);
    }

    @Override
    protected void initListener() {
        ivRegisterClearPhone.setOnClickListener(this);
        tvRegisterGetVerify.setOnClickListener(this);
        btnConfirmChangePwd.setOnClickListener(this);
    }

    @Override
    protected void innerListener(View v) {
        switch (v.getId()) {
            case R.id.ivRegisterClearPhone:
                etRegisterPhone.setText("");
                break;
            case R.id.tvRegisterGetVerify://获取验证码
                mCountDownTimer.start();
                break;
            case R.id.btnConfirmChangePwd://确认注册

                break;
        }
    }
}
