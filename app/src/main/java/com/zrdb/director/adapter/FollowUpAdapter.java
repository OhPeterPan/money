package com.zrdb.director.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zrdb.director.R;
import com.zrdb.director.image_loader.ImageLoader;
import com.zrdb.director.ui.bean.IndexListBean;
import com.zrdb.director.util.ApiUtils;

public class FollowUpAdapter extends BaseQuickAdapter<IndexListBean, BaseViewHolder> {
    public FollowUpAdapter() {
        super(R.layout.adapter_follow_up, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, IndexListBean item) {
        ImageView ivAdapterFollowUpPic = helper.getView(R.id.ivAdapterFollowUpPic);
        ImageLoader.with(mContext)
                .load(String.format("%1$s%2$s", ApiUtils.Config.getImageDimen(), item.image)).into(ivAdapterFollowUpPic);
        helper.setText(R.id.tvAdapterFollowUpInfo, item.name);
    }
}
