package com.zrdb.app.ui.model;

import com.lzy.okgo.model.HttpParams;
import com.zrdb.app.ui.callback.IIntelligentCallback;

import java.util.List;

public interface IIntelligentModel {
    void sendNetUploadPic(String token, String uid, List<HttpParams.FileWrapper> file, IIntelligentCallback callback);

    void sendNetSubmitInfo(String token, String uid, String name, String phone,
                           String city, String human, String tag, String details,
                           List<String> pictures, IIntelligentCallback callback);
}
