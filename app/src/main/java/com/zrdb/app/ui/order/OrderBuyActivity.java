package com.zrdb.app.ui.order;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.mm.opensdk.constants.Build;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zrdb.app.R;
import com.zrdb.app.annotation.Register;
import com.zrdb.app.rxbus.RxBus;
import com.zrdb.app.ui.BaseActivity;
import com.zrdb.app.ui.bean.LoginBean;
import com.zrdb.app.ui.bean.OrderBean;
import com.zrdb.app.ui.bean.OrderInfoDetailBean;
import com.zrdb.app.ui.bean.PayInfoBean;
import com.zrdb.app.ui.presenter.OrderBuyPresenter;
import com.zrdb.app.ui.response.OrderResponse;
import com.zrdb.app.ui.viewImpl.IOrderBuyView;
import com.zrdb.app.util.ApiUtils;
import com.zrdb.app.util.Convert;
import com.zrdb.app.util.LogUtil;
import com.zrdb.app.util.ParamUtils;
import com.zrdb.app.util.SpUtil;
import com.zrdb.app.util.ToastUtil;

import butterknife.BindView;

public class OrderBuyActivity extends BaseActivity<OrderBuyPresenter> implements IOrderBuyView {

    @BindView(R.id.ivClosePage)
    ImageView ivClosePage;
    @BindView(R.id.tvOrderMoney)
    TextView tvOrderMoney;
    @BindView(R.id.tvOrderHosName)
    TextView tvOrderHosName;
    @BindView(R.id.tvOrderHosAddress)
    TextView tvOrderHosAddress;
    @BindView(R.id.llHosPay)
    LinearLayout llHosPay;
    @BindView(R.id.llEnsurePay)
    LinearLayout llEnsurePay;
    @BindView(R.id.tvMulPersonName)
    TextView tvMulPersonName;
    @BindView(R.id.tvMulTitleProfessor)
    TextView tvMulTitleProfessor;
    @BindView(R.id.tvMulOffice)
    TextView tvMulOffice;
    @BindView(R.id.tvAdapterMulHos)
    TextView tvAdapterMulHos;
    @BindView(R.id.rlDocPay)
    RelativeLayout rlDocPay;
    @BindView(R.id.btnOrderPay)
    Button btnOrderPay;
    private LoginBean account;
    private String type;
    private int flag;
    private String orderId;
    private PayInfoBean payInfo;

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
        RxBus.getInstance().register(this);
        account = (LoginBean) SpUtil.get(SpUtil.ACCOUNT, LoginBean.class);
        type = getIntent().getStringExtra(ParamUtils.TYPE);
        flag = getIntent().getIntExtra(ParamUtils.FLAG, 0);
        orderId = getIntent().getStringExtra(ParamUtils.ORDER_ID);
        String money = getIntent().getStringExtra(ParamUtils.MONEY);
        if (flag == 1) {//购买保障卡界面而来
            sendNetEnsureCardPayInfo();
        } else {//订单列表界面而来
            sendNetPayInfo();
            tvOrderMoney.setText(money);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().remove(this);
    }

    private void sendNetEnsureCardPayInfo() {
        presenter.sendNet(account.token, account.uid);
    }

    @Override
    public void getEnsureInfoSuccess(String result) {
        LogUtil.LogI("保障卡：" + result);
        parseJson(result);
    }

    private void sendNetPayInfo() {
        presenter.sendNetPay(account.token, account.uid, type, orderId);
    }

    @Override
    public void getPayInfoSuccess(String result) {
        LogUtil.logResult("结果", result);
        parseJson(result);

    }

    private void parseJson(String result) {
        OrderResponse response = Convert.fromJson(result, OrderResponse.class);
        OrderBean data = response.data;
        OrderInfoDetailBean subInfo = data.sub_info;
        payInfo = data.pay_info;
        if (flag == 0) {
            switch (type) {
                case "1":
                    rlDocPay.setVisibility(View.VISIBLE);
                    tvMulPersonName.setText(subInfo.name);
                    tvAdapterMulHos.setText(subInfo.info);
                    break;
                case "2":
                    llHosPay.setVisibility(View.VISIBLE);
                    tvOrderHosName.setText(subInfo.name);
                    tvOrderHosAddress.setText(subInfo.info);
                    break;
                case "3":
                    llEnsurePay.setVisibility(View.VISIBLE);

                    break;
            }
        } else {
            llEnsurePay.setVisibility(View.VISIBLE);
            tvOrderMoney.setText(subInfo.money);
        }
    }

    @Override
    protected void initListener() {
        btnOrderPay.setOnClickListener(this);
    }

    @Override
    protected void innerListener(View v) {
        switch (v.getId()) {
            case R.id.btnOrderPay:
                wxPay();
                break;
        }
    }

    private void wxPay() {
        if (payInfo != null) {
            IWXAPI api = WXAPIFactory.createWXAPI(this, ApiUtils.Config.WX_APP_ID, true);

            if (!api.isWXAppInstalled()) {
                ToastUtil.showMessage("您手机尚未安装微信，请安装后再登录", Toast.LENGTH_SHORT);
                return;
            }

            // 判断该版本微信是否支持微信支付
            boolean isPaySupported = api.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;
            if (!isPaySupported) {
                ToastUtil.showMessage("您的终端不支持微信支付！", Toast.LENGTH_SHORT);
                return;
            }

            api.registerApp(ApiUtils.Config.WX_APP_ID);

            PayReq payReq = new PayReq();
            // 微信分配的公众账号ID
            payReq.appId = ApiUtils.Config.WX_APP_ID;
            // 微信支付分配的商户号
            payReq.partnerId = payInfo.partnerid;
            // payReq.partnerId="1386077702";
            // 微信返回的支付交易会话ID(预支付交易会话ID)
            payReq.prepayId = payInfo.prepayid;// 保存prepay_id

            payReq.packageValue = payInfo.packageX;

            payReq.nonceStr = payInfo.noncestr;

            payReq.timeStamp = payInfo.timestamp;

            payReq.sign = payInfo.sign;

            payReq.extData = "app data";

            api.sendReq(payReq);
        }
    }

    @Register
    private void payResult(String result) {
        if (TextUtils.equals("支付成功", result)) {
            setResult(0x002);
            finish();
        } else if (TextUtils.equals("支付失败", result)) {
            ToastUtil.showMessage("支付失败", Toast.LENGTH_SHORT);
        }
    }

    @Override
    public void showDataErrInfo(String result) {

    }
}
