package com.zrdb.app.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zrdb.app.R;
import com.zrdb.app.image_loader.ImageLoader;
import com.zrdb.app.ui.bean.MessageOnLineBean;
import com.zrdb.app.util.ApiUtils;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageOnLineAdapter extends BaseMultiItemQuickAdapter<MessageOnLineBean, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     */
    public MessageOnLineAdapter() {
        super(null);
        addItemType(1, R.layout.adapter_user_message);
        addItemType(2, R.layout.adapter_person_message);
    }

    @Override
    protected void convert(BaseViewHolder helper, MessageOnLineBean item) {
        switch (item.type) {
            case 1:
                platform(helper, item);
                break;
            case 2:
                person(helper, item);
                break;
        }
    }

    private void platform(BaseViewHolder helper, MessageOnLineBean item) {
        CircleImageView civAdapterMessagePic = helper.getView(R.id.civAdapterMessagePic);
        ImageLoader.with(mContext)
                .skipMemoryCache(true)
                .load(String.format("%1$s%2$s", ApiUtils.Config.getImageDimen(), item.fromavatar))
                .into(civAdapterMessagePic);
        helper.setText(R.id.tvAdapterMessageContent, item.content);
    }

    private void person(BaseViewHolder helper, MessageOnLineBean item) {
        CircleImageView civAdapterMessagePersonPic = helper.getView(R.id.civAdapterMessagePersonPic);
        ImageLoader.with(mContext)
                .skipMemoryCache(true)
                .load(String.format("%1$s%2$s", ApiUtils.Config.getImageDimen(), item.fromavatar))
                .into(civAdapterMessagePersonPic);
        helper.setText(R.id.tvAdapterMessagePersonContent, item.content);
    }
}
