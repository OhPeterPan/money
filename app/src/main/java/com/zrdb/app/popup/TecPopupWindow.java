package com.zrdb.app.popup;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.blankj.utilcode.util.ScreenUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.zrdb.app.R;
import com.zrdb.app.adapter.CityAdapter;
import com.zrdb.app.adapter.SecAdapter;
import com.zrdb.app.dialog.LoadDialog;
import com.zrdb.app.ui.bean.CityBean;
import com.zrdb.app.ui.bean.LoginBean;
import com.zrdb.app.ui.bean.SecListBean;
import com.zrdb.app.util.ApiUtils;
import com.zrdb.app.util.EncryptUtil;
import com.zrdb.app.util.LogUtil;
import com.zrdb.app.util.SpUtil;
import com.zrdb.app.util.TimeUtil;

import java.util.List;

public class TecPopupWindow extends BasePopupWindow<List<SecListBean>> implements AdapterView.OnItemClickListener {

    private String secId;
    private List<SecListBean> mData;
    private ListView lvProvince;
    private ListView lvCity;
    private List<CityBean> cityList;
    private SecAdapter provinceAdapter;
    private CityAdapter cityAdapter;
    private OnChooseSecListener listener;
    private LoadDialog loadDialog;
    private LoginBean account;

    public TecPopupWindow(Context context, List<SecListBean> data) {
        super(context, data);
        loadDialog = new LoadDialog(context);
        sendNet();
    }

    @Override
    protected void initView(View view) {
        lvProvince = view.findViewById(R.id.lvProvince);
        lvCity = view.findViewById(R.id.lvCity);
        account = (LoginBean) SpUtil.get(SpUtil.ACCOUNT, LoginBean.class);
        setAdapter();
    }

    private void setAdapter() {
        mData = getData();
        provinceAdapter = new SecAdapter(getContext(), mData);
        lvProvince.setAdapter(provinceAdapter);
        secId = mData.get(0).sec_id;
    }

    @Override
    protected void initListener() {
        lvProvince.setOnItemClickListener(this);
        lvCity.setOnItemClickListener(this);
    }

    @Override
    protected int getInflateRes() {
        return R.layout.poppup_address;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.lvProvince:
                provinceAdapter.setPosition(position);
                provinceAdapter.notifyDataSetChanged();
         /*       cityList = mData.get(position).child;
                cityAdapter.notifyData(cityList);*/
                break;
            case R.id.lvCity:
          /*      if (listener != null)
                    listener.getAddressInfo(cityAdapter.getItem(position));*/
                dismiss();
                break;
        }
    }

    public void show(View parent, int gravity, int x, int y) {
        Rect rect = new Rect();
        parent.getGlobalVisibleRect(rect);
        setHeight(ScreenUtils.getScreenHeight() - rect.bottom);
        showAtLocation(parent, gravity, x, rect.bottom + y);
    }

    private void showLoading() {
        if (loadDialog != null && !loadDialog.isShowing()) {
            loadDialog.show();
        }
    }

    private void hideLoading() {
        if (loadDialog != null && loadDialog.isShowing()) {
            loadDialog.hide();
        }
    }

    private void sendNet() {
        LogUtil.logResult("secId", secId);
        String url = ApiUtils.Config.getDimen() + ApiUtils.GET_DISEASE_URL + EncryptUtil.getMD5("Doctorgetdisease"
                + TimeUtil.date2String(TimeUtil.getNowDate(), TimeUtil.YEAR_MONTH_DAY)
                + ApiUtils.clientSecret);
        OkGo.<String>get(url)
                .tag("disease")
                .params("token", account.token)
                .params("uid", account.uid)
                .params("sec_id", secId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        diseaseInfo(response.body());
                    }
                });
    }

    private void diseaseInfo(String result) {
        LogUtil.logResult("科室", result);
    }

    public void destroy() {
        OkGo.getInstance().cancelTag("disease");
    }

    public void setOnChooseSecListener(OnChooseSecListener listener) {
        this.listener = listener;
    }

    public interface OnChooseSecListener {
        void getSecInfo(SecListBean secBean);
    }
}