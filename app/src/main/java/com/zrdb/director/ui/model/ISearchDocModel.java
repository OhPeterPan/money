package com.zrdb.director.ui.model;

import com.zrdb.director.ui.callback.ISearchDocCallback;

public interface ISearchDocModel {
    void sendNetGetDoc(String token, String uid, String keyword,
                       String cityId,
                       String tecId,
                       String levId,
                       String cateId,
                       String curPage,
                       String page,
                       ISearchDocCallback callback);

    void sendNetFilterDoc(String token, String uid,
                          ISearchDocCallback callback);
}
