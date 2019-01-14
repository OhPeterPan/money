package com.zrdb.app.adapter;

import android.widget.ImageView;

import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zrdb.app.R;
import com.zrdb.app.image_loader.ImageLoader;
import com.zrdb.app.ui.bean.MultipleTypeBean;
import com.zrdb.app.util.ApiUtils;

import java.util.List;

public class MultipleAdapter extends BaseMultiItemQuickAdapter<MultipleTypeBean, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public MultipleAdapter(List<MultipleTypeBean> data) {
        super(data);
        addItemType(0, R.layout.adapter_multiple_top);
        addItemType(1, R.layout.adapter_doc);
        addItemType(2, R.layout.adapter_hos);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultipleTypeBean item) {
        switch (item.type) {
            case 0:
                multipleTop(helper, item);
                break;
            case 1:
                multipleDoc(helper, item);
                break;
            case 2:
                multipleHos(helper, item);
                break;
        }
    }

    private void multipleTop(BaseViewHolder helper, MultipleTypeBean item) {
        helper.setText(R.id.tvAdapterMultipleTop, item.name);
        helper.addOnClickListener(R.id.tvAdapterMultipleMore);
    }

    private void multipleDoc(BaseViewHolder helper, MultipleTypeBean item) {
        ImageView ivMulAdapterDocHead = helper.getView(R.id.ivMulAdapterDocHead);
        ImageLoader.with(mContext)
                .load(String.format("%1$s%2$s", ApiUtils.Config.getImageDimen(), item.doc.picture))
                .into(ivMulAdapterDocHead);
        helper.setText(R.id.tvAdapterMulPersonName, item.doc.realname);
        helper.setText(R.id.tvAdapterMulTitleProfessor, item.doc.tec_name);
        helper.setText(R.id.tvAdapterMulOffice, item.doc.sec_name);
        helper.setText(R.id.tvAdapterMulHos, item.doc.hos_name);
        helper.setText(R.id.tvAdapterMulJob, item.doc.excels);
    }

    private void multipleHos(BaseViewHolder helper, MultipleTypeBean item) {
        ImageView ivMulAdapterHosPic = helper.getView(R.id.ivMulAdapterHosPic);
        ImageLoader.with(mContext)
                .load(String.format("%1$s%2$s", ApiUtils.Config.getImageDimen(), item.hos.picture))
                .into(ivMulAdapterHosPic);
        helper.setText(R.id.tvAdapterMulHosName, item.hos.name);
        helper.setGone(R.id.tvAdapterHosLev, !StringUtils.isEmpty(item.hos.lev_name));
        helper.setGone(R.id.tvAdapterHosCate, !StringUtils.isEmpty(item.hos.cate_name));
        helper.setText(R.id.tvAdapterHosLev, String.valueOf(item.hos.lev_name));
        helper.setText(R.id.tvAdapterHosCate, String.valueOf(item.hos.cate_name));
    }
}
