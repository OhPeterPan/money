package com.zrdb.app.image_loader;

import android.content.Context;

import com.bumptech.glide.MemoryCategory;
import com.zrdb.app.image_loader.loder.GlideLoader;
import com.zrdb.app.image_loader.loder.ILoader;


public class ImageLoader {

    private static final int DISK_CACHE_SIZE = 255;
    private static ILoader loader;

    public static void init(Context context) {
        init(context, DISK_CACHE_SIZE);
    }

    private static void init(Context context, int diskCacheSize) {
        init(context, diskCacheSize, MemoryCategory.NORMAL);
    }

    private static void init(Context context, int diskCacheSize, MemoryCategory memoryCategory) {
        getLoader().init(context, diskCacheSize, memoryCategory, true);
    }

    public static ILoader getLoader() {
        if (loader == null) {
            synchronized (ImageLoader.class) {
                if (loader == null) {
                    loader = new GlideLoader();
                }
            }
        }
        return loader;
    }

    public static ImageConfig.ImageConfigBuilder with(Context context) {
        return new ImageConfig.ImageConfigBuilder(context);
    }

    public static void onDestroy() {
        getLoader().onDestroy();
    }
}
