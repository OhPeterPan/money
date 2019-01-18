package com.zrdb.app.ui.presenter;

import com.zrdb.app.ui.callback.IMessageOnLineCallback;
import com.zrdb.app.ui.model.modelImpl.MessageOnLineModelImpl;
import com.zrdb.app.ui.viewImpl.IMessageOnLineView;

public class MessageOnLinePresenter extends BasePresenter<IMessageOnLineView> implements IMessageOnLineCallback {

    private final MessageOnLineModelImpl model;

    public MessageOnLinePresenter(IMessageOnLineView view) {
        super(view);
        model = new MessageOnLineModelImpl();
    }

    public void sendNetGetMessage(String token, String uid) {
        if (mView != null) mView.showLoading();
        if (model != null)
            model.sendNetGetMessage(token, uid, this);
    }

    public void sendNetSendMessage(String token, String uid, String content) {
        if (mView != null) mView.showLoading();
        if (model != null)
            model.sendNetSendMessage(token, uid, content, this);
    }

    @Override
    public void getMessageResult(String result) {
        if (!checkResultError(result)) {
            mView.getMessageResultSuccess(result);
        }
    }

    @Override
    public void sendMessage(String result) {
        if (!checkResultError(result)) {
            mView.sendMessageSuccess(result);
        }
    }


}
