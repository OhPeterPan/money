package com.zrdb.app.popup;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.blankj.utilcode.util.ScreenUtils;
import com.zrdb.app.R;
import com.zrdb.app.adapter.CityAdapter;
import com.zrdb.app.adapter.ProvinceAdapter;
import com.zrdb.app.ui.bean.CityBean;
import com.zrdb.app.ui.bean.ProvinceBean;

import java.util.List;

public class AddressPopupWindow extends BasePopupWindow<List<ProvinceBean>> implements AdapterView.OnItemClickListener {

    private List<ProvinceBean> mData;
    private ListView lvProvince;
    private ListView lvCity;
    private List<CityBean> cityList;
    private ProvinceAdapter provinceAdapter;
    private CityAdapter cityAdapter;
    private OnChooseAddressListener listener;

    public AddressPopupWindow(Context context, List<ProvinceBean> data) {
        super(context, data);
    }

    @Override
    protected void initView(View view) {
        lvProvince = view.findViewById(R.id.lvProvince);
        lvCity = view.findViewById(R.id.lvCity);
        setAdapter();
    }

    private void setAdapter() {
        mData = getData();
        provinceAdapter = new ProvinceAdapter(getContext(), mData);
        lvProvince.setAdapter(provinceAdapter);
        cityList = mData.get(0).child;
        cityAdapter = new CityAdapter(getContext(), cityList);
        lvCity.setAdapter(cityAdapter);
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
                cityList = mData.get(position).child;
                cityAdapter.notifyData(cityList);
                break;
            case R.id.lvCity:
                if (listener != null)
                    listener.getAddressInfo(cityAdapter.getItem(position));
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

    public void setOnChooseAddressListener(OnChooseAddressListener listener) {
        this.listener = listener;
    }

    public interface OnChooseAddressListener {
        void getAddressInfo(CityBean city);
    }
}
