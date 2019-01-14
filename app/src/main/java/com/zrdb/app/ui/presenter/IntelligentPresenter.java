package com.zrdb.app.ui.presenter;

import android.widget.Toast;

import com.blankj.utilcode.util.StringUtils;
import com.lzy.okgo.model.HttpParams;
import com.zrdb.app.ui.callback.IIntelligentCallback;
import com.zrdb.app.ui.model.modelImpl.IntelligentModelImpl;
import com.zrdb.app.ui.viewImpl.IIntelligentView;
import com.zrdb.app.util.ToastUtil;

import java.util.List;

public class IntelligentPresenter extends BasePresenter<IIntelligentView> implements IIntelligentCallback {

    private final IntelligentModelImpl model;

    public IntelligentPresenter(IIntelligentView view) {
        super(view);
        model = new IntelligentModelImpl();
    }

    public boolean checkInfo(String name, String phone, String address, String human, String tag, String detail) {

        if (StringUtils.isEmpty(name)) {
            ToastUtil.showMessage("请输入姓名！", Toast.LENGTH_SHORT);
            return false;
        }

        if (StringUtils.isEmpty(phone)) {
            ToastUtil.showMessage("请输入电话号码！", Toast.LENGTH_SHORT);
            return false;
        }

        if (StringUtils.isEmpty(address)) {
            ToastUtil.showMessage("请输入就诊城市！", Toast.LENGTH_SHORT);
            return false;
        }
        if (StringUtils.isEmpty(human)) {
            ToastUtil.showMessage("请选择人群标签！", Toast.LENGTH_SHORT);
            return false;
        }
        if (StringUtils.isEmpty(tag)) {
            ToastUtil.showMessage("请选择症状描述标签！", Toast.LENGTH_SHORT);
            return false;
        }

        if (StringUtils.isEmpty(detail)) {
            ToastUtil.showMessage("请输入症状详细描述！", Toast.LENGTH_SHORT);
            return false;
        }
        return true;
    }

    public void sendNetUploadPic(String token, String uid, List<HttpParams.FileWrapper> file) {
        if (mView != null) mView.showLoading();
        if (model != null) model.sendNetUploadPic(token, uid, file, this);
    }

    public void sendNetSubmitInfo(String token, String uid, String name, String phone,
                                  String address, String human, String tag,
                                  String detail, List<String> pictures) {
        if (mView != null) mView.showLoading();
        if (model != null)
            model.sendNetSubmitInfo(token, uid, name, phone, address, human, tag, detail, pictures, this);
    }

    @Override
    public void uploadPic(String result) {
        if (!checkResultError(result)) {
            mView.uploadPicSuccess(result);
        }
    }

    @Override
    public void submitPageResult(String result) {
        if (!checkResultError(result)) {
            mView.submitPageResultSuccess(result);
        }
    }
}
