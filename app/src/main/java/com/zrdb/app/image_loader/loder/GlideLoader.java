package com.zrdb.app.image_loader.loder;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.MemoryCategory;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.cache.ExternalPreferredCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.request.RequestOptions;
import com.zrdb.app.R;
import com.zrdb.app.image_loader.ImageConfig;


public class GlideLoader implements ILoader {
    private ImageConfig mConfig;

    @Override
    public void init(Context context, int cacheSize, MemoryCategory memoryCategory, boolean isInternalCD) {
        GlideBuilder builder = new GlideBuilder();
        Glide.get(context).setMemoryCategory(memoryCategory); //如果在应用当中想要调整内存缓存的大小，开发者可以通过如下方式：
        if (isInternalCD) {
            builder.setDiskCache(new InternalCacheDiskCacheFactory(context, cacheSize * 1024 * 1024));
        } else {
            builder.setDiskCache(new ExternalPreferredCacheDiskCacheFactory(context, cacheSize * 1024 * 1024));
        }
    }

    @Override
    public void request(ImageConfig config) {
        this.mConfig = config;
        if (mConfig == null) return;
        RequestBuilder<Bitmap> builder = null;
        RequestManager requestManager = Glide.with(config.context);
        if (config.asBitmap)
            builder = requestManager.asBitmap();

        if (!StringUtils.isEmpty(config.url)) {
            builder = builder.load(config.url);
        } else if (config.file != null) {
            builder = builder.load(config.file);
        } else {
            builder = builder.load(R.drawable.ic_test_image);
        }
        RequestOptions RequestOptions = new RequestOptions()
                .error(config.errIdRes == 0 ? R.drawable.ic_placeholder : config.errIdRes)
                .placeholder(config.placeholder == 0 ? R.drawable.ic_placeholder : config.placeholder)
                .diskCacheStrategy(config.diskCacheStrategy == null ? DiskCacheStrategy.ALL : config.diskCacheStrategy)
                .skipMemoryCache(config.skipMemory)
                .centerCrop();
        if (builder != null && mConfig != null && mConfig.view != null)
            builder.apply(RequestOptions).into((ImageView) mConfig.view);
    }

    public void onDestroy() {
        if (mConfig != null)
            mConfig = null;
    }
}
