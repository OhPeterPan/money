package com.zrdb.app.banner;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.youth.banner.Banner;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;
import com.youth.banner.loader.ImageLoaderInterface;
import com.zrdb.app.R;

import java.lang.ref.WeakReference;
import java.util.List;

public class BannerImpl implements IBanner {
    private Banner mBanner;
    private Context mContext;

    public Context getContext() {
        return mContext;
    }

    public BannerImpl(Context context, Banner banner) {
        WeakReference<Context> contextWeakReference = new WeakReference<>(context);
        mContext = contextWeakReference.get();
        this.mBanner = banner;
    }

    @Override
    public IBanner setBannerStyle(int bannerStyle) {
        mBanner.setBannerStyle(bannerStyle);
        return this;
    }

    @Override
    public IBanner setImageLoader(ImageLoaderInterface imageLoader) {
        mBanner.setImageLoader(imageLoader);
        return this;
    }

    @Override
    public IBanner setImages(List<?> imageUrls) {
        if (imageUrls != null)
            mBanner.setImages(imageUrls);
        else
            throw new NullPointerException("图片集合不能为空！");
        return this;
    }

    @Override
    public IBanner setBannerAnimation(Class<? extends ViewPager.PageTransformer> transformer) {
        //设置banner动画效果
        mBanner.setBannerAnimation(Transformer.Default);
        return this;
    }

    @Override
    public IBanner setBannerTitles(List<String> titles) {
        if (titles != null)
            //设置标题集合（当banner样式有显示title时）
            mBanner.setBannerTitles(titles);
        else
            throw new NullPointerException("标题集合不能为空！");
        return this;
    }

    @Override
    public IBanner setDelayTime(int delayTime) {
        //设置轮播时间
        mBanner.setDelayTime(delayTime);
        return this;
    }

    @Override
    public IBanner setIndicatorGravity(int type) {
        //设置指示器位置（当banner模式中有指示器时）
        mBanner.setIndicatorGravity(type);
        return this;
    }

    @Override
    public void start() {
        if (mBanner != null) mBanner.start();
    }

    @Override
    public void update(List<?> arrayListImages, List<String> arrayListTitles) {
        mBanner.update(arrayListImages, arrayListTitles);
    }

    public static class GlideImageLoader extends ImageLoader {

        private BannerImpl mBannerImpl;

        public GlideImageLoader(BannerImpl bannerImpl) {
            WeakReference<BannerImpl> bannerWeakReference = new WeakReference<>(bannerImpl);
            mBannerImpl = bannerWeakReference.get();
        }

        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            //Glide 加载图片简单用法
            RequestOptions options = new RequestOptions().placeholder(R.drawable.ic_placeholder);
            Glide.with(mBannerImpl.getContext()).asBitmap().load(path).apply(options).into(imageView);
        }
    }

    @Override
    public void onStart() {
        if (mBanner != null)
            mBanner.startAutoPlay();
    }

    @Override
    public void onStop() {
        if (mBanner != null)
            mBanner.stopAutoPlay();
    }
}
