package com.zrdb.app.ui.model;

import com.zrdb.app.ui.callback.IBuyCardCallback;

public interface IBuyCardModel {
    void sendNetBuyEnsureCard(String token, String uid, IBuyCardCallback cardCallback);
}
