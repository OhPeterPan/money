package com.zrdb.director.ui.main;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.StringUtils;
import com.gyf.barlibrary.ImmersionBar;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagFlowLayout;
import com.zrdb.director.R;
import com.zrdb.director.adapter.HistoryFlowAdapter;
import com.zrdb.director.adapter.SearchFlowAdapter;
import com.zrdb.director.ui.BaseActivity;
import com.zrdb.director.ui.bean.KeywordBean;
import com.zrdb.director.ui.bean.LoginBean;
import com.zrdb.director.ui.bean.SearchBean;
import com.zrdb.director.ui.presenter.MainSearchPresenter;
import com.zrdb.director.ui.response.SearchResponse;
import com.zrdb.director.ui.viewImpl.IMainSearchView;
import com.zrdb.director.util.Convert;
import com.zrdb.director.util.LogUtil;
import com.zrdb.director.util.ParamUtils;
import com.zrdb.director.util.SpUtil;
import com.zrdb.director.util.ToastUtil;

import java.util.List;

import butterknife.BindView;

public class SearchActivity extends BaseActivity<MainSearchPresenter> implements IMainSearchView, TextView.OnEditorActionListener {
    @BindView(R.id.tvActTitle)
    TextView tvActTitle;
    @BindView(R.id.ivToolbarRight)
    ImageView ivToolbarRight;
    @BindView(R.id.etSearch)
    EditText etSearch;
    @BindView(R.id.tagFlowLayout)
    TagFlowLayout tagFlowLayout;
    @BindView(R.id.tagHistoryLayout)
    TagFlowLayout tagHistoryLayout;
    @BindView(R.id.tvClearHistory)
    TextView tvClearHistory;
    private LoginBean account;
    private boolean isFirst = true;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void initStatusBar() {
        ImmersionBar.with(this)
                .fitsSystemWindows(true)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true)
                .init();
    }

    @Override
    protected void initPresenter() {
        presenter = new MainSearchPresenter(this);
    }

    @Override
    protected void initData() {
        account = (LoginBean) SpUtil.get(SpUtil.ACCOUNT, LoginBean.class);
        initToolbar();
        sendNet();
    }

    private void sendNet() {
        presenter.sendNet(account.token, account.uid);
    }

    private void initToolbar() {
        tvActTitle.setText("搜索");
        tvActTitle.setVisibility(View.VISIBLE);
        setBackRes(R.drawable.ic_back);
        setBackVisibility(View.VISIBLE);
        ivToolbarRight.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (presenter != null && !isFirst) {
            presenter.sendNet(account.token, account.uid);
        }
    }

    @Override
    protected void initListener() {
        etSearch.setOnEditorActionListener(this);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (((actionId == EditorInfo.IME_ACTION_SEARCH) || (actionId == 0)) && event != null) {
            String keyWord = etSearch.getText().toString().trim();
            if (!StringUtils.isEmpty(keyWord)) {
                startIntentActivity(new Intent().putExtra(ParamUtils.KEYWORD, keyWord), SearchDetailActivity.class);
            } else {
                ToastUtil.showMessage("请输入要搜索的数据！", Toast.LENGTH_SHORT);
            }
        }
        return false;
    }

    @Override
    protected void innerListener(View v) {

    }

    @Override
    public void getSearchPageInfoSuccess(String result) {
        LogUtil.LogI("result:" + result);
        SearchResponse searchResponse = Convert.fromJson(result, SearchResponse.class);
        if (searchResponse.code == 200) {
            SearchBean searchBean = searchResponse.data;
            List<KeywordBean> tag = searchBean.tag;
            List<KeywordBean> log = searchBean.log;
            setTag(tag);
            setHistory(log);
            isFirst = false;
        } else {
            ToastUtil.showMessage(String.valueOf(searchResponse.msg), Toast.LENGTH_SHORT);
        }
    }

    private void setHistory(final List<KeywordBean> log) {
        if (log == null || log.size() == 0) {
            tagHistoryLayout.setVisibility(View.GONE);
        } else {
            tagHistoryLayout.setVisibility(View.VISIBLE);
            tagHistoryLayout.setVisibility(View.VISIBLE);
            tagHistoryLayout.setAdapter(new HistoryFlowAdapter(log, this));
            tagHistoryLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                @Override
                public boolean onTagClick(View view, int position, FlowLayout parent) {
                    startIntentActivity(new Intent().putExtra(ParamUtils.KEYWORD, log.get(position).keywords), SearchDetailActivity.class);
                    return true;
                }
            });
        }
    }

    private void setTag(final List<KeywordBean> tag) {
        if (tag == null || tag.size() == 0) {
            tagFlowLayout.setVisibility(View.GONE);
        } else {
            tagFlowLayout.setVisibility(View.VISIBLE);
            tagFlowLayout.setAdapter(new SearchFlowAdapter(tag, this));
            tagFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                @Override
                public boolean onTagClick(View view, int position, FlowLayout parent) {
                    startIntentActivity(new Intent().putExtra(ParamUtils.KEYWORD, tag.get(position).keywords), SearchDetailActivity.class);
                    return true;
                }
            });
        }
    }

    @Override
    public void showDataErrInfo(String result) {

    }


}
