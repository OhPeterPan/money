package com.zrdb.app.ui.presenter;

import android.widget.Toast;

import com.blankj.utilcode.util.StringUtils;
import com.zrdb.app.ui.callback.IRegisterCallback;
import com.zrdb.app.ui.model.modelImpl.RegisterModelImpl;
import com.zrdb.app.ui.viewImpl.IRegisterView;
import com.zrdb.app.util.ToastUtil;

public class RegisterPresenter extends BasePresenter<IRegisterView> implements IRegisterCallback {

    private final RegisterModelImpl model;

    public RegisterPresenter(IRegisterView view) {
        super(view);
        model = new RegisterModelImpl();
    }

    public boolean checkInfo(String phone) {
        if (StringUtils.isEmpty(phone)) {
            ToastUtil.showMessage("手机号不能为空！", Toast.LENGTH_SHORT);
            return false;
        }
        return true;
    }

    public boolean checkRegisterInfo(String phone, String verify, String pwd, String nextPwd) {
        if (StringUtils.isEmpty(phone)) {
            ToastUtil.showMessage("手机号不能为空！", Toast.LENGTH_SHORT);
            return false;
        }
        if (StringUtils.isEmpty(verify)) {
            ToastUtil.showMessage("验证码不能为空！", Toast.LENGTH_SHORT);
            return false;
        }
        if (StringUtils.isEmpty(pwd)) {
            ToastUtil.showMessage("密码不能为空！", Toast.LENGTH_SHORT);
            return false;
        }
        if (StringUtils.isEmpty(nextPwd)) {
            ToastUtil.showMessage("请再次输入密码！", Toast.LENGTH_SHORT);
            return false;
        }
        return true;
    }

    public void sendNetGetVerify(String phone) {
        if (mView != null) mView.showLoading();
        if (model != null) model.sendNetGetVerify(phone, this);
    }

    public void sendNetRegister(String phone, String verify, String pwd, String nextPwd) {
        if (mView != null) mView.showLoading();
        if (model != null) model.sendNetRegister(phone, pwd, nextPwd, verify, this);
    }

    @Override
    public void getVerify(String result) {
        if (mView != null && !checkResultError(result)) {
            mView.getVerifySuccess(result);
        }
    }

    @Override
    public void register(String result) {
        if (mView != null && !checkResultError(result)) {
            mView.registerSuccess(result);
        }
    }


}
