package com.zrdb.director.fragment.searchfrag;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.blankj.utilcode.util.KeyboardUtils;
import com.zrdb.director.R;
import com.zrdb.director.adapter.MultipleAdapter;
import com.zrdb.director.fragment.LazyFragment;
import com.zrdb.director.ui.bean.LoginBean;
import com.zrdb.director.ui.bean.MultipleBean;
import com.zrdb.director.ui.bean.MultipleDocBean;
import com.zrdb.director.ui.bean.MultipleHosBean;
import com.zrdb.director.ui.bean.MultipleTypeBean;
import com.zrdb.director.ui.presenter.MultipleResultPresenter;
import com.zrdb.director.ui.response.MultipleResponse;
import com.zrdb.director.ui.viewImpl.IMultipleResultView;
import com.zrdb.director.util.Convert;
import com.zrdb.director.util.LogUtil;
import com.zrdb.director.util.ParamUtils;
import com.zrdb.director.util.SpUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MultipleFrag extends LazyFragment<MultipleResultPresenter> implements IMultipleResultView {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private String keyword;
    private LoginBean account;
    private MultipleAdapter adapter;
    private List<MultipleTypeBean> multipleList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.frag_multiple;
    }

    @Override
    protected void initPresenter() {
        presenter = new MultipleResultPresenter(this);
    }

    @Override
    protected void fetchData() {
        Bundle bundle = getArguments();
        keyword = bundle.getString(ParamUtils.KEYWORD);
        account = (LoginBean) SpUtil.get(SpUtil.ACCOUNT, LoginBean.class);
        initAdapter();
        setRefresh(true);
        innerRefresh();
    }

    private void initAdapter() {
        adapter = new MultipleAdapter(null);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void innerRefresh() {
        LogUtil.LogI("keyword:" + keyword);
        presenter.sendNet(account.token, account.uid, keyword);
    }

    public static Fragment newInstance(String keyword) {
        Fragment fragment = new MultipleFrag();
        Bundle bundle = new Bundle();
        bundle.putString(ParamUtils.KEYWORD, keyword);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void getMultipleResultSuccess(String result) {
        //LogUtil.LogI("multiple:" + result);
        KeyboardUtils.hideSoftInput(getActivity());
        MultipleResponse response = Convert.fromJson(result, MultipleResponse.class);
        MultipleBean data = response.data;
        setMultipleData(data);
    }

    private void setMultipleData(MultipleBean data) {
        List<MultipleDocBean> doctorList = data.doctor_list;
        List<MultipleHosBean> hospitalList = data.hospital_list;
        multipleList.clear();
        MultipleTypeBean multipleType;
        if (doctorList != null && doctorList.size() != 0) {
            multipleType = new MultipleTypeBean();
            multipleType.type = 0;
            multipleType.name = "相关医生";
            multipleList.add(multipleType);
            for (MultipleDocBean bean : doctorList) {
                multipleType = new MultipleTypeBean();
                multipleType.type = 1;
                multipleType.doc = bean;
                multipleList.add(multipleType);
            }
        }
        if (hospitalList != null && hospitalList.size() != 0) {
            multipleType = new MultipleTypeBean();
            multipleType.type = 0;
            multipleType.name = "相关医院";
            multipleList.add(multipleType);
            for (MultipleHosBean bean : hospitalList) {
                multipleType = new MultipleTypeBean();
                multipleType.type = 2;
                multipleType.hos = bean;
                multipleList.add(multipleType);
            }
        }

        adapter.setNewData(multipleList);
        adapter.setEmptyView(getEmpty("没有搜到相关数据~"));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0x100 && resultCode == 0x200) {
            keyword = data.getStringExtra(ParamUtils.KEYWORD);
            setRefresh(true);
            innerRefresh();
        }
    }

    @Override
    public void showDataErrInfo(String result) {

    }
}
