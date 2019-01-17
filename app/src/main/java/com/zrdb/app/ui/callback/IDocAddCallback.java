package com.zrdb.app.ui.callback;

public interface IDocAddCallback extends ICallback {
    void uploadPicture(String result);

    void docJobInfo(String result);

    void addDocResult(String result);
}
