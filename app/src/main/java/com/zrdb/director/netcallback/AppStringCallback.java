package com.zrdb.director.netcallback;

import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.zrdb.director.ui.callback.ICallback;

public abstract class AppStringCallback extends StringCallback {
    private ICallback callback;

    public AppStringCallback(ICallback callback) {
        super();
        this.callback = callback;
    }

    @Override
    public void onError(Response<String> response) {
        super.onError(response);
        if (callback != null)
            callback.error(response.getException());
    }
}
