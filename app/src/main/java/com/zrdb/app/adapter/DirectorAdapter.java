package com.zrdb.app.adapter;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zrdb.app.R;
import com.zrdb.app.image_loader.ImageLoader;
import com.zrdb.app.ui.bean.DirectorBean;
import com.zrdb.app.util.ApiUtils;

public class DirectorAdapter extends BaseQuickAdapter<DirectorBean, BaseViewHolder> {
    public DirectorAdapter() {
        super(R.layout.adapter_director, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, DirectorBean item) {
        ImageView civDirectorPic = helper.getView(R.id.civDirectorPic);
        ImageLoader.with(mContext).load(String.format("%1$s%2$s", ApiUtils.Config.getImageDimen(), item.picture))
                .into(civDirectorPic);
        helper.setText(R.id.tvAdapterDirectorName, item.realname);
        helper.setText(R.id.tvAdapterDirectorOffice, item.sec_name);
        helper.setText(R.id.tvAdapterMulTitleProfessor, item.tec_name);
        helper.setText(R.id.tvAdapterHosScale, item.lev_name);
        helper.setText(R.id.tvAdapterPlaceHos, item.hos_name);

        helper.setText(R.id.tvAdapterDocGood, item.excels);
        helper.setText(R.id.tvAdapterDocPrice, String.format("¥ %s", item.money));
        shakeRound(helper, String.format("接诊率：%s%%", item.round));
    }

    private void shakeRound(BaseViewHolder helper, String str) {
        SpannableString sp = new SpannableString(str);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#363636"));
        sp.setSpan(colorSpan, 0, 4, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        helper.setText(R.id.tvAdapterVaccinatePercent, sp);
    }
}
