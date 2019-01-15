package com.zrdb.app.ui.presenter;

import android.widget.Toast;

import com.blankj.utilcode.util.StringUtils;
import com.zrdb.app.ui.callback.ISubscribeDocCallback;
import com.zrdb.app.ui.model.modelImpl.SubscribeDocModelImpl;
import com.zrdb.app.ui.viewImpl.ISubscribeDocView;
import com.zrdb.app.util.ToastUtil;

public class SubscribeDocPresenter extends BasePresenter<ISubscribeDocView> implements ISubscribeDocCallback {

    private final SubscribeDocModelImpl model;

    public SubscribeDocPresenter(ISubscribeDocView view) {
        super(view);
        model = new SubscribeDocModelImpl();
    }

    public boolean checkInfo(String name, String phone, String disease) {
        if (StringUtils.isEmpty(name)) {
            ToastUtil.showMessage("请输入姓名！", Toast.LENGTH_SHORT);
            return false;
        }
        if (StringUtils.isEmpty(phone)) {
            ToastUtil.showMessage("请输入电话号码！", Toast.LENGTH_SHORT);
            return false;
        }
        if (StringUtils.isEmpty(disease)) {
            ToastUtil.showMessage("请输入疾病名称！", Toast.LENGTH_SHORT);
            return false;
        }
        return true;
    }

    public void sendNetSubDocInfo(String token, String uid, String docId) {
        if (mView != null) mView.showLoading();
        if (model != null) model.sendNetSubDocInfo(token, uid, docId, this);
    }

    public void sendNetSubmitPageInfo(String token, String uid, String docId, String name, String phone, String disease) {
        if (mView != null) mView.showLoading();
        if (model != null)
            model.sendNetSubmitSubPersonInfo(token, uid, docId, name, phone, disease, this);
    }

    @Override
    public void subDocInfo(String result) {
        if (!checkResultError(result)) {
            mView.subDocInfoSuccess(result);
        }
    }

    @Override
    public void submitSubPersonResult(String result) {
        if (!checkResultError(result)) {
            mView.submitSubPersonSuccess(result);
        }
    }
}
