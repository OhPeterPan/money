package com.zrdb.app.albumconfig;

import android.content.Context;
import android.support.annotation.NonNull;

import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumFile;
import com.zrdb.app.util.ToastUtil;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/2/8 0008.
 * album封装  严振杰
 */

public class AlbumConfig {
    private Context mContext;
    private List<String> imagePathList;
    private IUploadListener uploadListener;
    private ArrayList<AlbumFile> mAlbumFiles;
    private IChoosePicListener listener;

    public AlbumConfig(Context ctx) {
        WeakReference<Context> weakReference = new WeakReference<>(ctx);
        mContext = weakReference.get();
    }

    private void initImageList() {
        if (imagePathList == null)
            imagePathList = new ArrayList<>();
        else
            imagePathList.clear();
    }

    //单选相片
    public void sigleImage() {
        initImageList();
        Album.image(mContext)
                .singleChoice()
                .requestCode(100)
                .camera(false)
                .columnCount(4)
                .onResult(new Action<ArrayList<AlbumFile>>() {
                    @Override
                    public void onAction(int requestCode, @NonNull ArrayList<AlbumFile> result) {
                        //LogUtil.logI("返回照片大小" + result.size());
                        mAlbumFiles = result;
                        if (result == null)
                            return;
                        for (int i = 0; i < result.size(); i++) {
                            imagePathList.add(result.get(i).getPath());
                        }
                        if (uploadListener != null && imagePathList.size() != 0)
                            uploadListener.getImagePathList(imagePathList);
                    }
                })
                .onCancel(new Action<String>() {
                    @Override
                    public void onAction(int requestCode, @NonNull String result) {
                        ToastUtil.showError(result);
                    }
                })
                .start();
    }

    //单选相片
    public void singleImage(boolean camera) {
        initImageList();
        Album.image(mContext)
                .singleChoice()
                .requestCode(100)
                .camera(camera)
                .columnCount(4)
                .onResult(new Action<ArrayList<AlbumFile>>() {
                    @Override
                    public void onAction(int requestCode, @NonNull ArrayList<AlbumFile> result) {
                        //LogUtil.logI("返回照片大小" + result.size());
                        mAlbumFiles = result;
                        if (result == null)
                            return;
                        for (int i = 0; i < result.size(); i++) {
                            imagePathList.add(result.get(i).getPath());
                        }
                        if (uploadListener != null && imagePathList.size() != 0)
                            uploadListener.getImagePathList(imagePathList);
                    }
                })
                .onCancel(new Action<String>() {
                    @Override
                    public void onAction(int requestCode, @NonNull String result) {
                        ToastUtil.showError(result);
                    }
                })
                .start();
    }

    //单选照相
    public void singleCamera() {
        initImageList();
        Album.camera(mContext)
                .image()
                .requestCode(200)
                .onResult(new Action<String>() {
                    @Override
                    public void onAction(int requestCode, @NonNull String result) {
                        if (uploadListener != null)
                            uploadListener.getImagePath(result);
                    }
                })
                .onCancel(new Action<String>() {
                    @Override
                    public void onAction(int requestCode, @NonNull String result) {
                        ToastUtil.showError(result);
                    }
                })
                .start();
    }

/*    //单选照相
    public void singleCamera() {
        initImageList();
        Album.camera(mContext)
                .image()
                .requestCode(200)
                .onResult(new Action<String>() {
                    @Override
                    public void onAction(int requestCode, @NonNull String result) {
                        if (uploadListener != null)
                            uploadListener.getImagePath(result);
                    }
                })
                .onCancel(new Action<String>() {
                    @Override
                    public void onAction(int requestCode, @NonNull String result) {
                        ToastUtil.showError(result);
                    }
                })
                .start();
    }*/

    /**
     * 多选相册
     *
     * @param camera 是否带相机 多图显示
     * @param index  最多可选的图片数
     */
    public void multipleImage(boolean camera, final int index, final ArrayList<AlbumFile> albumFiles) {
        initImageList();
        Album.image(mContext)
                .multipleChoice()
                .requestCode(300)
                .selectCount(index)
                .camera(camera)
                .columnCount(4)
                .checkedList(albumFiles)
                .onResult(new Action<ArrayList<AlbumFile>>() {
                    @Override
                    public void onAction(int requestCode, @NonNull ArrayList<AlbumFile> result) {
                        mAlbumFiles = result;
                        if (result == null)
                            return;
                        for (int i = 0; i < result.size(); i++) {
                            imagePathList.add(result.get(i).getPath());
                        }

                        if (listener != null && imagePathList.size() != 0)
                            listener.getPicListener(mAlbumFiles);
                    }
                })
                .onCancel(new Action<String>() {
                    @Override
                    public void onAction(int requestCode, @NonNull String result) {
                        ToastUtil.showError(result);
                    }
                })
                .start();
    }

    /**
     * 多选相册
     *
     * @param camera 是否带相机 多图显示
     * @param index  最多可选的图片数
     */
    public void multipleImage(boolean camera, int index) {
        initImageList();
        Album.image(mContext)
                .multipleChoice()
                .requestCode(300)
                .selectCount(index)
                .camera(camera)
                .columnCount(4)//    .checkedList(mAlbumFiles)
                .onResult(new Action<ArrayList<AlbumFile>>() {
                    @Override
                    public void onAction(int requestCode, @NonNull ArrayList<AlbumFile> result) {
                        mAlbumFiles = result;
                        if (result == null)
                            return;
                        for (int i = 0; i < result.size(); i++) {
                            imagePathList.add(result.get(i).getPath());
                        }
                        if (uploadListener != null && imagePathList.size() != 0)
                            uploadListener.getImagePathList(imagePathList);
                    }
                })
                .onCancel(new Action<String>() {
                    @Override
                    public void onAction(int requestCode, @NonNull String result) {
                        ToastUtil.showError(result);
                    }
                })
                .start();
    }

    public void setILoadImageListener(IUploadListener uploadListener) {
        this.uploadListener = uploadListener;
    }

    public void setIChoosePicListener(IChoosePicListener listener) {
        this.listener = listener;
    }

    public interface IUploadListener {
        //选择相册图片
        void getImagePathList(List<String> imagePathList);

        //直接拍照得到
        void getImagePath(String imagePath);
    }

    public interface IChoosePicListener {
        void getPicListener(ArrayList<AlbumFile> mAlbumFiles);
    }

}
