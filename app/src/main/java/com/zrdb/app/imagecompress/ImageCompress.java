package com.zrdb.app.imagecompress;

import android.content.Context;
import android.widget.Toast;

import com.zrdb.app.util.ToastUtil;

import java.io.File;
import java.lang.ref.WeakReference;

import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class ImageCompress {
    private File file;
    private final Context mContext;
    private CompressImageListener listener;

    public ImageCompress(Context context) {
        WeakReference<Context> weakReference = new WeakReference<>(context);
        mContext = weakReference.get();
    }

    public void setFile(File file) {
        this.file = file;
    }

    public void startCompress() {
        Luban.with(mContext)
                .load(file)
                .ignoreBy(80)
                .filter(new CompressionPredicate() {
                    @Override
                    public boolean apply(String path) {
                        return file != null;
                    }
                })
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                        // TODO 压缩开始前调用，可以在方法内启动 loading UI
                    }

                    @Override
                    public void onSuccess(File file) {
                        // TODO 压缩成功后调用，返回压缩后的图片文件
                        if (listener != null) {
                            listener.onCompleteCompress(file);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        // TODO 当压缩过程出现问题时调用
                        ToastUtil.showMessage("图片压缩失败", Toast.LENGTH_SHORT);
                    }
                }).launch();
    }

    public void setOnCompleteCompressListener(CompressImageListener listener) {
        this.listener = listener;
    }

    public interface CompressImageListener {
        void onCompleteCompress(File file);
    }
}
