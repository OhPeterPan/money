package com.zrdb.app.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zrdb.app.R;
import com.zrdb.app.image_loader.ImageLoader;
import com.zrdb.app.ui.bean.MultipleDocBean;
import com.zrdb.app.util.ApiUtils;

public class SearchDocAdapter extends BaseQuickAdapter<MultipleDocBean, BaseViewHolder> {
    public SearchDocAdapter() {
        super(R.layout.adapter_doc, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultipleDocBean item) {
        ImageView ivMulAdapterDocHead = helper.getView(R.id.ivMulAdapterDocHead);
        ImageLoader.with(mContext)
                .load(String.format("%1$s%2$s", ApiUtils.Config.getImageDimen(), item.picture))
                .into(ivMulAdapterDocHead);
        helper.setText(R.id.tvAdapterMulPersonName, item.realname);
        helper.setText(R.id.tvAdapterMulTitleProfessor, item.tec_name);
        helper.setText(R.id.tvAdapterMulOffice, item.sec_name);
        helper.setText(R.id.tvAdapterMulHos, item.hos_name);
        helper.setText(R.id.tvAdapterMulJob, item.excels);
    }
}
