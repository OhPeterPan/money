package com.zrdb.director.popup;

import android.content.Context;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;
import com.zrdb.director.R;
import com.zrdb.director.ui.bean.CateListBean;
import com.zrdb.director.ui.bean.DocFilterBean;
import com.zrdb.director.ui.bean.LevelListBean;
import com.zrdb.director.ui.bean.TecListBean;
import com.zrdb.director.ui.response.DocFilterResponse;
import com.zrdb.director.util.Convert;
import com.zrdb.director.util.ToastUtil;

import java.util.List;

public class DocFilterPopupWindow extends BasePopupWindow<String> implements View.OnClickListener, TagFlowLayout.OnTagClickListener {
    private LayoutInflater mInflater;
    private TextView tvFilterJob;
    private TagFlowLayout tagFlowFilterJob;
    private TextView tvFilterHodGrade;
    private TagFlowLayout tagFlowFilterHodGrade;
    private TextView tvFilterHosClass;
    private TagFlowLayout tagFlowFilterHosClass;
    private TextView tvPopupFilterReset;
    private TextView tvPopupFilterConfirm;
    private List<TecListBean> tecList;
    private List<LevelListBean> levelList;
    private List<CateListBean> cateList;
    private String tecId = "0";
    private String levId = "0";
    private String cateId = "0";
    private OnChooseFilterInfoListener listener;

    public DocFilterPopupWindow(Context context, String data) {
        super(context, data);
    }

    @Override
    protected int getInflateRes() {
        return R.layout.popup_doc_filter;
    }

    @Override
    protected void initView(View view) {
        mInflater = LayoutInflater.from(getContext());
        tvFilterJob = view.findViewById(R.id.tvFilterJob);
        tagFlowFilterJob = view.findViewById(R.id.tagFlowFilterJob);
        tvFilterHodGrade = view.findViewById(R.id.tvFilterHodGrade);
        tagFlowFilterHodGrade = view.findViewById(R.id.tagFlowFilterHodGrade);
        tvFilterHosClass = view.findViewById(R.id.tvFilterHosClass);
        tagFlowFilterHosClass = view.findViewById(R.id.tagFlowFilterHosClass);
        tvPopupFilterReset = view.findViewById(R.id.tvPopupFilterReset);
        tvPopupFilterConfirm = view.findViewById(R.id.tvPopupFilterConfirm);
        initData();
    }

    private void initData() {
        DocFilterResponse response = Convert.fromJson(getData(), DocFilterResponse.class);
        DocFilterBean filterBean = response.data;
        tecList = filterBean.tec_list;
        levelList = filterBean.level_list;
        cateList = filterBean.cate_list;
        if (tecList == null || tecList.size() == 0) {
            tvFilterJob.setVisibility(View.GONE);
            tagFlowFilterJob.setVisibility(View.GONE);
        }
        if (levelList == null || levelList.size() == 0) {
            tvFilterHodGrade.setVisibility(View.GONE);
            tagFlowFilterHodGrade.setVisibility(View.GONE);
        }
        if (cateList == null || cateList.size() == 0) {
            tvFilterHosClass.setVisibility(View.GONE);
            tagFlowFilterHosClass.setVisibility(View.GONE);
        }
        setAdapter();
    }

    private void setAdapter() {

        tecId = "0";
        levId = "0";
        cateId = "0";

        tagFlowFilterJob.setAdapter(new FilterAdapter<TecListBean>(tecList) {

            @Override
            public void fullData(TextView tv, TecListBean bean) {
                tv.setText(bean.name);
            }
        });
        tagFlowFilterHodGrade.setAdapter(new FilterAdapter<LevelListBean>(levelList) {

            @Override
            public void fullData(TextView tv, LevelListBean bean) {
                tv.setText(bean.name);
            }
        });
        tagFlowFilterHosClass.setAdapter(new FilterAdapter<CateListBean>(cateList) {

            @Override
            public void fullData(TextView tv, CateListBean bean) {
                tv.setText(bean.name);
            }
        });

    }

    @Override
    protected void initListener() {
        tvPopupFilterReset.setOnClickListener(this);
        tvPopupFilterConfirm.setOnClickListener(this);

        tagFlowFilterJob.setOnTagClickListener(this);
        tagFlowFilterHodGrade.setOnTagClickListener(this);
        tagFlowFilterHosClass.setOnTagClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvPopupFilterReset://重置
                setAdapter();
                break;
            case R.id.tvPopupFilterConfirm:
                if (listener != null) listener.chooseFilterInfo(tecId, levId, cateId);
                dismiss();
                break;
        }
    }

    public void setOnChooseFilterInfoListener(OnChooseFilterInfoListener listener) {
        this.listener = listener;
    }

    public interface OnChooseFilterInfoListener {
        void chooseFilterInfo(String tecId, String levId, String cateId);
    }

    @Override
    public boolean onTagClick(View view, int position, FlowLayout parent) {
        switch (parent.getId()) {
            case R.id.tagFlowFilterJob:
                tecId = tecList.get(position).tec_id;
                ToastUtil.showMessage("tecId:" + tecId, Toast.LENGTH_SHORT);
                break;
            case R.id.tagFlowFilterHodGrade:
                levId = levelList.get(position).lev_id;
                ToastUtil.showMessage("levId:" + levId, Toast.LENGTH_SHORT);
                break;
            case R.id.tagFlowFilterHosClass:
                cateId = cateList.get(position).cate_id;
                ToastUtil.showMessage("cateId:" + cateId, Toast.LENGTH_SHORT);
                break;
        }
        return true;
    }

    private abstract class FilterAdapter<T> extends TagAdapter<T> {

        public FilterAdapter(List<T> datas) {
            super(datas);
        }

        @Override
        public View getView(FlowLayout parent, int position, T bean) {
            TextView tv = (TextView) mInflater.inflate(R.layout.layout_filter_tag, parent, false);
            ViewGroup.LayoutParams lp = tv.getLayoutParams();
            lp.width = (ScreenUtils.getScreenWidth() - SizeUtils.dp2px(12) * 2 - SizeUtils.dp2px(5) * 6) / 3;
            fullData(tv, bean);
            return tv;
        }

        public abstract void fullData(TextView tv, T t);
    }

    public void show(View parent, int gravity, int x, int y) {
        Rect rect = new Rect();
        parent.getGlobalVisibleRect(rect);
        setHeight(ScreenUtils.getScreenHeight() - rect.bottom);
        showAtLocation(parent, gravity, x, rect.bottom + y);
    }
}
