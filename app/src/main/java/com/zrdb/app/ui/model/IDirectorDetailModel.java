package com.zrdb.app.ui.model;

import com.zrdb.app.ui.callback.IDirectorDetailCallback;

public interface IDirectorDetailModel {
    void sendNetGetDocList(String token, String uid,
                           String secId, String disId, String areaId, String tecId, String cateId, String levId, String curPage,
                           IDirectorDetailCallback callback);

    void sendNetFilter(String token, String uid,
                       String secId, IDirectorDetailCallback callback);
}
