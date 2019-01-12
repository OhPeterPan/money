package com.zrdb.director.banner;

import android.support.v4.view.ViewPager;

import com.youth.banner.loader.ImageLoaderInterface;

import java.util.List;

public interface IBanner {

    //如果你需要考虑更好的体验，可以这么操作
    //开始轮播
    void onStart();

    //结束轮播
    void onStop();

    IBanner setBannerStyle(int bannerStyle);

    IBanner setImageLoader(ImageLoaderInterface imageLoader);

    IBanner setImages(List<?> imageUrls);

    IBanner setBannerAnimation(Class<? extends ViewPager.PageTransformer> transformer);

    IBanner setBannerTitles(List<String> titles);

    IBanner setDelayTime(int delayTime);

    IBanner setIndicatorGravity(int type);

    void start();

    void update(List<?> arrayListImages, List<String> arrayListTitles);
}
