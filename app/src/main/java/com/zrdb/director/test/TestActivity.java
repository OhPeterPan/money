package com.zrdb.director.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;
import com.zrdb.director.R;

import java.util.List;

public class TestActivity extends AppCompatActivity {

    private TagFlowLayout flowLayout;
    private String[] mVals = new String[]{"Hello", "Android", "Weclome Hi ", "Button", "TextView", "Hello",
            "Android", "Weclome", "Button ImageView", "TextView", "Helloworld",
            "Android", "Weclome Hello", "Button Text", "TextView"};
    private LayoutInflater mInflater;
    private int screenWidth = ScreenUtils.getScreenWidth();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        flowLayout = findViewById(R.id.flowLayout);
        initData();
    }

    private void initData() {
        mInflater = LayoutInflater.from(this);
        flowLayout.setAdapter(new TestAdapter(mVals));
    }

    private class TestAdapter extends TagAdapter<String> {

        public TestAdapter(List<String> datas) {
            super(datas);
        }

        public TestAdapter(String[] datas) {
            super(datas);
        }

        @Override
        public View getView(FlowLayout parent, int position, String s) {
            TextView tv = (TextView) mInflater.inflate(R.layout.layout_tag, parent, false);
            ViewGroup.LayoutParams lp = tv.getLayoutParams();
            // LogUtil.LogI("padding:" + tv.getPaddingLeft() + ":::" + tv.getPaddingRight());
            lp.width = (screenWidth - SizeUtils.dp2px(6) * 2 - SizeUtils.dp2px(15) * 2) / 3;
            //tv.setLayoutParams(lp);
            tv.setText(s);
            return tv;
        }
    }
}
