package com.zrdb.director.ui.presenter;

import android.widget.Toast;

import com.blankj.utilcode.util.StringUtils;
import com.zrdb.director.ui.callback.IForgetPwdCallback;
import com.zrdb.director.ui.model.modelImpl.ForgetPwdModelImpl;
import com.zrdb.director.ui.viewImpl.IForgetPwdView;
import com.zrdb.director.util.ToastUtil;

public class ForgetPwdPresenter extends BasePresenter<IForgetPwdView> implements IForgetPwdCallback {

    private ForgetPwdModelImpl model;

    public ForgetPwdPresenter(IForgetPwdView view) {
        super(view);
        model = new ForgetPwdModelImpl();
    }

    public boolean checkPhone(String phone) {
        if (StringUtils.isEmpty(phone)) {
            ToastUtil.showMessage("手机号不能为空！", Toast.LENGTH_SHORT);
            return false;
        }
        return true;
    }

    public boolean checkInfo(String phone, String pwd, String verify) {

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
        return true;
    }

    public void sendNetGetVerify(String phone) {
        if (mView != null) mView.showLoading();
        if (model != null) model.sendNetGetVerify(phone, this);
    }

    public void sendNetChangePwd(String phone, String pwd, String verify) {
        if (mView != null) mView.showLoading();
        if (model != null) model.sendNetChangePwd(phone, pwd, verify, this);
    }

    @Override
    public void getVerifyResult(String result) {
        if (!checkResultError(result)) {
            mView.getVerifyResultSuccess(result);
        }
    }

    @Override
    public void changePwdResult(String result) {
        if (!checkResultError(result)) {
            mView.changePwdResultSuccess(result);
        }
    }
}
