package com.zrdb.director.ui.presenter;

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
