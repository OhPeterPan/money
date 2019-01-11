package com.zrdb.director.image_loader;

import android.content.Context;
import android.view.View;

import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;

public class ImageConfig {

    public Context context;
    public String url;
    public File file;
    public int errIdRes;
    public int placeholder;
    public View view;
    public DiskCacheStrategy diskCacheStrategy;
    public boolean skipMemory;
    public boolean asBitmap;
    public boolean asGif;

    public ImageConfig(ImageConfigBuilder configBuilder) {
        context = configBuilder.context;
        url = configBuilder.url;
        file = configBuilder.file;
        errIdRes = configBuilder.errIdRes;
        placeholder = configBuilder.placeholder;
        view = configBuilder.view;
        diskCacheStrategy = configBuilder.diskCacheStrategy;
        skipMemory = configBuilder.skipMemory;
        asBitmap = configBuilder.asBitmap;
        asGif = configBuilder.asGif;
    }

    private void show() {
        ImageLoader.getLoader().request(this);
    }

    public static class ImageConfigBuilder {
        private Context context;
        private String url;
        private File file;
        private int errIdRes;
        private int placeholder;
        private View view;
        private DiskCacheStrategy diskCacheStrategy;
        private boolean skipMemory;
        private boolean asBitmap = true;
        private boolean asGif;

        public ImageConfigBuilder(Context context) {
            this.context = context;
        }

        public ImageConfigBuilder asBitmap(boolean asBitmap) {
            this.asBitmap = asBitmap;
            return this;
        }

        public ImageConfigBuilder asGif(boolean asGif) {
            this.asGif = asGif;
            return this;
        }

        public ImageConfigBuilder load(String url) {
            this.url = url;
            return this;
        }

        public ImageConfigBuilder load(File file) {
            this.file = file;
            return this;
        }


        public ImageConfigBuilder error(int res) {
            this.errIdRes = res;
            return this;
        }

        public ImageConfigBuilder placeholder(int res) {
            this.placeholder = res;
            return this;
        }

        public ImageConfigBuilder diskCacheStrategy(DiskCacheStrategy diskCacheStrategy) {
            this.diskCacheStrategy = diskCacheStrategy;
            return this;
        }

        public ImageConfigBuilder skipMemoryCache(boolean skipMemory) {
            this.skipMemory = skipMemory;
            return this;
        }

        public void into(View v) {
            this.view = v;
            new ImageConfig(this).show();
        }
    }
}
