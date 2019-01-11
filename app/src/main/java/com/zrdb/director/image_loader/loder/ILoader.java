package com.zrdb.director.image_loader.loder;

import android.content.Context;

import com.bumptech.glide.MemoryCategory;
import com.zrdb.director.image_loader.ImageConfig;


public interface ILoader {
    void init(Context context, int cacheSize, MemoryCategory memoryCategory, boolean isInternalCD);

    void request(ImageConfig config);

    void onDestroy();
}
