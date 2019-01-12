package com.zrdb.director.ui.presenter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;

import com.zrdb.director.ui.response.BaseResponse;
import com.zrdb.director.util.Convert;
import com.zrdb.director.util.SystemUtil;
import com.zrdb.director.util.ToastUtil;
import com.zrdb.director.view.IView;

public abstract class BasePresenter<T extends IView> {
    public T mView;
    private boolean isError = false;//暂时没用

    public boolean isError() {
        return isError;
    }

    public BasePresenter(T view) {
        mView = view;
    }

    //判断是否返回的数据是否包含错误
    public boolean checkResultError(String message) {
        boolean result = false;
        if (mView != null) {
            result = false;
            mView.hideLoading();
            BaseResponse baseResponse = Convert.fromJson(message, BaseResponse.class);
            if (baseResponse.code == 200) {
                result = false;
            } else {
                result = true;

                if (TextUtils.equals("用户验证错误", baseResponse.msg)) {//重新登录
                    ToastUtil.showMessage(baseResponse.msg + ",请重新登录！", Toast.LENGTH_SHORT);
                    if (mView instanceof AppCompatActivity) {
                        SystemUtil.exitApp((Context) mView);
                    } else if (mView instanceof Fragment) {
                        SystemUtil.exitApp(((Fragment) mView).getActivity());
                    }
                } else {
                    ToastUtil.showMessage(baseResponse.msg, Toast.LENGTH_SHORT);
                }
            }
        } else {
            result = true;
        }
        return result;
    }

    public void error(Throwable e) {
        if (mView != null) {
            mView.hideLoading();
            mView.showErrInfo(e);
        }
    }

    //防止内存泄漏
    public void destroy() {
        if (mView != null)
            mView = null;
    }
}
