package com.zrdb.director.fragment.searchfrag;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.zrdb.director.R;
import com.zrdb.director.fragment.LazyFragment;
import com.zrdb.director.util.LogUtil;
import com.zrdb.director.util.ParamUtils;

public class DocFragment extends LazyFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.frag_doc;
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void fetchData() {
        Bundle bundle = getArguments();
        String keyword = bundle.getString(ParamUtils.KEYWORD);
        LogUtil.LogI("医生:" + keyword);
    }

    @Override
    protected void innerRefresh() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0x100 && resultCode == 0x200) {
            LogUtil.LogI("医生");
        }
    }

    public static Fragment newInstance(String keyword) {
        Fragment fragment = new DocFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ParamUtils.KEYWORD, keyword);
        fragment.setArguments(bundle);
        return fragment;
    }
}
