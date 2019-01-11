package com.zrdb.director.ui.splash;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;
import com.zrdb.director.R;
import com.zrdb.director.ui.BaseActivity;
import com.zrdb.director.ui.account.LoginActivity;
import com.zrdb.director.ui.bean.LoginBean;
import com.zrdb.director.ui.main.MainActivity;
import com.zrdb.director.util.SpUtil;

import butterknife.BindView;


public class SplashActivity extends BaseActivity {

    @BindView(R.id.ivSplash)
    ImageView ivSplash;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initData() {
        ViewHelper.setAlpha(ivSplash, 0);
        ViewPropertyAnimator.animate(ivSplash).alpha(1).setDuration(2000).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Intent intent = new Intent();
                LoginBean account = (LoginBean) SpUtil.get(SpUtil.ACCOUNT, LoginBean.class);
                if (account == null)
                    startIntentActivity(intent, LoginActivity.class);
                else
                    startIntentActivity(intent, MainActivity.class);
                finish();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        }).start();
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void innerListener(View v) {

    }
}
