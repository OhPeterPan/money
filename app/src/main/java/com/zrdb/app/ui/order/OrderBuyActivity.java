package com.zrdb.app.ui.order;

import android.view.View;

import com.zrdb.app.R;
import com.zrdb.app.ui.BaseActivity;
import com.zrdb.app.ui.bean.LoginBean;
import com.zrdb.app.ui.presenter.OrderBuyPresenter;
import com.zrdb.app.ui.viewImpl.IOrderBuyView;
import com.zrdb.app.util.LogUtil;
import com.zrdb.app.util.ParamUtils;
import com.zrdb.app.util.SpUtil;

public class OrderBuyActivity extends BaseActivity<OrderBuyPresenter> implements IOrderBuyView {

    private LoginBean account;
    private String type;
    private int flag;
    private String orderId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_buy_detail;
    }

    @Override
    protected void initPresenter() {
        presenter = new OrderBuyPresenter(this);
    }

    @Override
    protected void initData() {
        account = (LoginBean) SpUtil.get(SpUtil.ACCOUNT, LoginBean.class);
        type = getIntent().getStringExtra(ParamUtils.TYPE);
        flag = getIntent().getIntExtra(ParamUtils.FLAG, 0);
        orderId = getIntent().getStringExtra(ParamUtils.ORDER_ID);
        if (flag == 1) {//购买保障卡界面而来
            sendNetEnsureCardPayInfo();
        } else {//订单列表界面而来
            sendNetPayInfo();
        }
    }

    private void sendNetEnsureCardPayInfo() {
        presenter.sendNet(account.token, account.uid);
    }

    @Override
    public void getEnsureInfoSuccess(String result) {
        LogUtil.LogI("保障卡：" + result);
    }

    private void sendNetPayInfo() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void innerListener(View v) {

    }


    @Override
    public void getPayInfoSuccess(String result) {

    }

    @Override
    public void showDataErrInfo(String result) {

    }
}
