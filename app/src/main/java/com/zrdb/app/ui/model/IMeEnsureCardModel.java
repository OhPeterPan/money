package com.zrdb.app.ui.model;

import com.zrdb.app.ui.callback.IMeEnsureCardCallback;

public interface IMeEnsureCardModel {
    void sendNetGetEnsureCard(String token, String uid, IMeEnsureCardCallback callback);
}
