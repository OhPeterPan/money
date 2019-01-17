package com.zrdb.app.fragment.order;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zrdb.app.R;
import com.zrdb.app.adapter.OrderAdapter;
import com.zrdb.app.fragment.LazyFragment;
import com.zrdb.app.ui.bean.LoginBean;
import com.zrdb.app.ui.bean.OrderDetailBean;
import com.zrdb.app.ui.bean.OrderInfoBean;
import com.zrdb.app.ui.order.OrderBuyActivity;
import com.zrdb.app.ui.presenter.OrderPresenter;
import com.zrdb.app.ui.response.MeOrderResponse;
import com.zrdb.app.ui.viewImpl.IOrderModelView;
import com.zrdb.app.util.Convert;
import com.zrdb.app.util.LogUtil;
import com.zrdb.app.util.ParamUtils;
import com.zrdb.app.util.SpUtil;
import com.zrdb.app.util.ToastUtil;

import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;

public class OrderFragment extends LazyFragment<OrderPresenter> implements IOrderModelView, BaseQuickAdapter.OnItemChildClickListener {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private LoginBean account;
    private String status;
    private List<OrderDetailBean> orderList;
    private OrderAdapter adapter;
    private int position;

    @Override
    protected int getLayoutId() {
        return R.layout.frag_order;
    }

    @Override
    protected void initPresenter() {
        presenter = new OrderPresenter(this);
    }

    @Override
    protected void fetchData() {
        Bundle bundle = getArguments();
        status = bundle.getString(ParamUtils.STATUS);
        account = (LoginBean) SpUtil.get(SpUtil.ACCOUNT, LoginBean.class);
        sendNet();
    }


    private void sendNet() {
        setRefresh(true);
        innerRefresh();
    }

    @Override
    protected void innerRefresh() {
        LogUtil.LogI(account.uid);
        presenter.sendNetGetOrder(account.token, account.uid, status);
    }

    @Override
    public void getOrderSuccess(String result) {
        LogUtil.LogI(result);
        initAdapter();
        MeOrderResponse response = Convert.fromJson(result, MeOrderResponse.class);
        OrderInfoBean data = response.data;
        initData(data.doctor_list, data.hospital_list, data.card_list);
    }

    private void initData(List<OrderDetailBean> doctorList, List<OrderDetailBean> hospitalList, List<OrderDetailBean> cardList) {
        if (orderList == null)
            orderList = new LinkedList<>();
        else
            orderList.clear();
        if (doctorList != null && !doctorList.isEmpty()) {
            for (OrderDetailBean bean :
                    doctorList) {
                bean.type = "1";
            }
            orderList.addAll(doctorList);
        }
        if (hospitalList != null && !hospitalList.isEmpty()) {
            for (OrderDetailBean bean :
                    hospitalList) {
                bean.type = "2";
            }
            orderList.addAll(hospitalList);
        }
        if (cardList != null && !cardList.isEmpty()) {
            for (OrderDetailBean bean :
                    cardList) {
                bean.type = "3";
            }
            orderList.addAll(cardList);
        }
        if (adapter != null)
            adapter.setNewData(orderList);
    }

    @Override
    public void orderDeleteSuccess(String result) {
        ToastUtil.showMessage("订单删除成功！", Toast.LENGTH_SHORT);
        if (adapter != null) adapter.remove(position);
    }

    @Override
    public void showDataErrInfo(String result) {
        initAdapter();
    }

    private void initAdapter() {
        if (adapter == null) {
            adapter = new OrderAdapter();
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setAdapter(adapter);
            adapter.setEmptyView(getEmpty("暂无订单~"));
            adapter.setOnItemChildClickListener(this);
        }
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter ada, View v, int position) {
        OrderDetailBean orderDetail = adapter.getItem(position);
        this.position = position;
        switch (v.getId()) {
            case R.id.tvAdapterGoPay://去支付
                startActivityForResult(new Intent(getActivity(), OrderBuyActivity.class)
                        .putExtra(ParamUtils.TYPE, orderDetail.type)
                        .putExtra(ParamUtils.ORDER_ID, orderDetail.sub_id)
                        .putExtra(ParamUtils.MONEY, orderDetail.money)
                        .putExtra(ParamUtils.FLAG, 0), 0x001);
                break;
            case R.id.tvAdapterDelOrder:
                showDeleteOrderDialog(orderDetail);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 0x002) {
            if (requestCode == 0x001) {
                setRefresh(true);
                innerRefresh();
            }
        }
    }

    private void showDeleteOrderDialog(OrderDetailBean orderDetail) {
        final String type = orderDetail.type;
        final String subId = orderDetail.sub_id;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("确认删除订单？");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //LogUtil.LogI(subId);
                presenter.sendNetDelOrder(account.token, account.uid, type, subId);
            }
        });
        builder.setNegativeButton("取消", null);
        builder.show();
    }

    /**
     * @param status 0待付款 1全部
     * @return
     */
    public static Fragment newInstance(String status) {
        OrderFragment fragment = new OrderFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ParamUtils.STATUS, status);
        fragment.setArguments(bundle);
        return fragment;
    }

}
