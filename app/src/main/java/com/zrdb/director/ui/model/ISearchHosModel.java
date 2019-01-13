package com.zrdb.director.ui.model;

import com.zrdb.director.ui.callback.ISearchHosCallback;

public interface ISearchHosModel {
    void sendNetGetHos(String token, String uid, String keyword,
                       String cityId,
                       String cateId,
                       String curPage,
                       String page, ISearchHosCallback callback);

    void sendNetFilterHos(String token, String uid,
                          ISearchHosCallback callback);
}
