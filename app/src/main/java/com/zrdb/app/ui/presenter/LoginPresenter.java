package com.zrdb.app.ui.presenter;

import android.widget.Toast;

import com.blankj.utilcode.util.StringUtils;
import com.zrdb.app.ui.callback.ILoginCallback;
import com.zrdb.app.ui.model.modelImpl.LoginModelImpl;
import com.zrdb.app.ui.viewImpl.ILoginView;
import com.zrdb.app.util.ToastUtil;

public class LoginPresenter extends BasePresenter<ILoginView> implements ILoginCallback {

    private final LoginModelImpl model;

    public LoginPresenter(ILoginView view) {
        super(view);
        model = new LoginModelImpl();
    }

    public boolean checkParams(String phone, String pwd) {


        if (StringUtils.isEmpty(phone)) {
            ToastUtil.showMessage("电话号码不能为空", Toast.LENGTH_SHORT);
            return false;
        }
        if (StringUtils.isEmpty(pwd)) {
            ToastUtil.showMessage("密码不能为空", Toast.LENGTH_SHORT);
            return false;
        }
        return true;
    }

    public void sendNetLogin(String phone, String pwd) {
        if (mView != null) mView.showLoading();
        if (model != null) model.sendNetLogin(phone, pwd, this);
    }

    @Override
    public void getLoginResult(String result) {
        if (mView != null && !checkResultError(result)) {
            mView.hideLoading();
            mView.getLoginResult(result);
        }
    }
}
