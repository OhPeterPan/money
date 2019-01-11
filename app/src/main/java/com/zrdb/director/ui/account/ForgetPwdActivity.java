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

public class ForgetPwdActivity extends BaseActivity {

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
            case R.id.tvGetVerify:
                mCountDownTimer.start();
                break;
            case R.id.btnConfirmChangePwd:

                break;
        }
    }
}
