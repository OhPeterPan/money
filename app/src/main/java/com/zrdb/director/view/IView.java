package com.zrdb.director.view;

public interface IView<T> {
    void showLoading();

    void hideLoading();

    void showErrInfo(Throwable e);

    void showDataErrInfo(T result);
}
