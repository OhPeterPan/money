package com.zrdb.app.ui.presenter;

import android.widget.Toast;

import com.blankj.utilcode.util.StringUtils;
import com.zrdb.app.ui.callback.ISubscribeHosCallback;
import com.zrdb.app.ui.model.modelImpl.SubscribeHosModelImpl;
import com.zrdb.app.ui.viewImpl.ISubscribeHosView;
import com.zrdb.app.util.ToastUtil;

public class SubscribeHosPresenter extends BasePresenter<ISubscribeHosView> implements ISubscribeHosCallback {

    private final SubscribeHosModelImpl model;

    public SubscribeHosPresenter(ISubscribeHosView view) {
        super(view);
        model = new SubscribeHosModelImpl();
    }

    public boolean checkInfo(String name, String phone, String secName, String disease) {
        if (StringUtils.isEmpty(name)) {
            ToastUtil.showMessage("请输入姓名！", Toast.LENGTH_SHORT);
            return false;
        }
        if (StringUtils.isEmpty(phone)) {
            ToastUtil.showMessage("请输入电话号码！", Toast.LENGTH_SHORT);
            return false;
        }
        if (StringUtils.isEmpty(secName)) {
            ToastUtil.showMessage("请输入科室！", Toast.LENGTH_SHORT);
            return false;
        }
        if (StringUtils.isEmpty(disease)) {
            ToastUtil.showMessage("请输入疾病名称！", Toast.LENGTH_SHORT);
            return false;
        }
        return true;
    }

    public void sendNetHosInfo(String token, String uid, String hosId) {
        if (mView != null)
            mView.showLoading();
        if (model != null)
            model.sendNetGetSunHosInfo(token, uid, hosId, this);
    }

    public void sendNetSubmitPageInfo(String token, String uid, String hosId,
                                      String name, String phone,
                                      String secName, String disease) {
        if (mView != null)
            mView.showLoading();
        if (model != null)
            model.sendNetSubmitPersonInfo(token, uid, hosId, name, phone, disease, secName, this);
    }

    @Override
    public void getSubHosInfo(String result) {
        if (!checkResultError(result)) {
            mView.getSubHosInfoSuccess(result);
        }
    }

    @Override
    public void SubmitPersonInfo(String result) {
        if (!checkResultError(result)) {
            mView.SubmitPersonInfoSuccess(result);
        }
    }
}
