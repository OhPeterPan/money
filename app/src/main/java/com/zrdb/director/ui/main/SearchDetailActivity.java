package com.zrdb.director.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.StringUtils;
import com.zrdb.director.R;
import com.zrdb.director.adapter.FragmentAdapter;
import com.zrdb.director.fragment.LazyFragment;
import com.zrdb.director.fragment.searchfrag.DocFragment;
import com.zrdb.director.fragment.searchfrag.HospitalFrag;
import com.zrdb.director.fragment.searchfrag.MultipleFrag;
import com.zrdb.director.ui.BaseActivity;
import com.zrdb.director.util.ParamUtils;
import com.zrdb.director.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SearchDetailActivity extends BaseActivity implements TextView.OnEditorActionListener {

    @BindView(R.id.tvActRightTitle)
    TextView tvActRightTitle;
    @BindView(R.id.ivToolbarRight)
    ImageView ivToolbarRight;
    @BindView(R.id.etSearchDetail)
    EditText etSearchDetail;
    @BindView(R.id.tvSearchDetailClear)
    TextView tvSearchDetailClear;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    private List<Fragment> fragList = new ArrayList<>();
    private String titles[] = new String[]{"综合", "医生", "医院"};
    private int currPos = 0;//当前展示的fragment
    private String keyword;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search_detail;
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initData() {
        setBackVisibility(View.VISIBLE);
        ivToolbarRight.setVisibility(View.VISIBLE);
        keyword = getIntent().getStringExtra(ParamUtils.KEYWORD);
        etSearchDetail.setHint(keyword);
        initTabLayout();
    }

    private void initTabLayout() {
        Fragment oneFrag = MultipleFrag.newInstance(keyword);
        Fragment twoFrag = DocFragment.newInstance(keyword);
        Fragment threeFrag = HospitalFrag.newInstance(keyword);
        ((MultipleFrag) oneFrag).setOnPageChangeListener(new MultipleFrag.OnPageChangeListener() {
            @Override
            public void pagePos(int position) {
                if (viewPager != null) viewPager.setCurrentItem(position);
            }
        });
        fragList.add(oneFrag);
        fragList.add(twoFrag);
        fragList.add(threeFrag);
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager(), fragList, titles);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                currPos = position;
            }
        });
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    protected void initListener() {
        tvSearchDetailClear.setOnClickListener(this);
        etSearchDetail.setOnEditorActionListener(this);
    }

    @Override
    protected void innerListener(View v) {
        switch (v.getId()) {
            case R.id.tvSearchDetailClear:
                etSearchDetail.setText("");
                etSearchDetail.setHint("搜索疾病、医生或医院");
                break;
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (((actionId == EditorInfo.IME_ACTION_SEARCH) || (actionId == 0)) && event != null) {
            KeyboardUtils.hideSoftInput(this);
            String keyword = etSearchDetail.getText().toString().trim();
            Bundle bundle;
            if (!StringUtils.isEmpty(keyword)) {
                if (fragList != null) {
                    for (int i = 0; i < fragList.size(); i++) {
                        if (i == currPos) {
                            fragList.get(i)
                                    .onActivityResult(0x100, 0x200,
                                            new Intent().putExtra(ParamUtils.KEYWORD, keyword));
                        } else {
                            ((LazyFragment) fragList.get(i)).setKeyword(keyword);
                            bundle = new Bundle();
                            bundle.putString(ParamUtils.KEYWORD, keyword);
                            fragList.get(i).setArguments(bundle);
                        }
                    }
                }

            } else {
                ToastUtil.showMessage("请输入要搜索的数据！", Toast.LENGTH_SHORT);
            }
        }
        return false;
    }
}
