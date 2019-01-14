package com.zrdb.app.adapter;

import android.widget.ImageView;

import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zrdb.app.R;
import com.zrdb.app.image_loader.ImageLoader;
import com.zrdb.app.ui.bean.MultipleHosBean;
import com.zrdb.app.util.ApiUtils;

public class SearchHosAdapter extends BaseQuickAdapter<MultipleHosBean, BaseViewHolder> {
    public SearchHosAdapter() {
        super(R.layout.adapter_hos, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultipleHosBean item) {
        ImageView ivMulAdapterHosPic = helper.getView(R.id.ivMulAdapterHosPic);
        ImageLoader.with(mContext)
                .load(String.format("%1$s%2$s", ApiUtils.Config.getImageDimen(), item.picture))
                .into(ivMulAdapterHosPic);
        helper.setText(R.id.tvAdapterMulHosName, item.name);
        helper.setGone(R.id.tvAdapterHosLev, !StringUtils.isEmpty(item.lev_name));
        helper.setGone(R.id.tvAdapterHosCate, !StringUtils.isEmpty(item.cate_name));
        helper.setText(R.id.tvAdapterHosLev, String.valueOf(item.lev_name));
        helper.setText(R.id.tvAdapterHosCate, String.valueOf(item.cate_name));
    }
}
