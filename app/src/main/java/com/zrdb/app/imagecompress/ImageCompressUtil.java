package com.zrdb.app.imagecompress;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;

import com.zrdb.app.R;
import com.zrdb.app.util.LogUtil;
import com.zrdb.app.util.UIUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageCompressUtil {
    private static final String SAVE_IMAGE_PATH_NAME = "upload";

    private static int readPictureDegree(String path) {  //path 图片路径
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return degree;
        }
        return degree;
    }

    public static String getFilePath(String fileName) {
        String imageName = fileName.substring(fileName.lastIndexOf("/") + 1, fileName.length());

        int orientation = readPictureDegree(fileName);

        Bitmap bitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        BitmapFactory.decodeFile(fileName, options);
        options.inJustDecodeBounds = true;
        int outWidth = options.outWidth;
        int outHeight = options.outHeight;
        int width = 480;
        int height = 800;
        LogUtil.LogI("旋转角度：" + orientation + ":::outWidth:" + outWidth + ":::outHeight:" + outHeight);
        if (outWidth < width && outHeight < height) {
            return fileName;
        }
        int widthRadio = Math.round(outWidth / width);
        int heightRadio = Math.round(outHeight / height);
        int radio = widthRadio < heightRadio ? widthRadio : heightRadio;
        options.inSampleSize = radio;
        options.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeFile(fileName, options);
        if (orientation != 0) {
            Matrix matrix = new Matrix();
            matrix.setRotate(orientation);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            Canvas canvas = new Canvas(bitmap);
            canvas.drawBitmap(bitmap, 0, 0, new Paint());
        }

        String path = compressFile(bitmap, fileName, imageName);

        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
        }
        return path;
    }

    private static String compressFile(Bitmap bitmap, String fileName, String imageName) {
        StringBuffer stringBuffer = new StringBuffer();

        File path = UIUtil.getContext().getExternalCacheDir();//外部私有文件目录

        stringBuffer.append(path.getAbsolutePath());
        stringBuffer.append(File.separator);
        stringBuffer.append(SAVE_IMAGE_PATH_NAME);

        File fileDir = new File(stringBuffer.toString());
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        int quality = 100;
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);//质量压缩
        while ((outputStream.toByteArray().length / 1024) > 100) {
            outputStream.reset();
            quality -= 10;
            if (quality < 11) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);//质量压缩
                break;
            }
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);//质量压缩
        }
        File f = null;
        String filePath = "";
        try {
            f = new File(fileDir.getAbsolutePath(), imageName);
            FileOutputStream fileOutputStream = new FileOutputStream(f);
            fileOutputStream.write(outputStream.toByteArray());
            fileOutputStream.flush();
            fileOutputStream.close();
            filePath = f.getAbsolutePath();

        } catch (IOException e) {
            e.printStackTrace();
            LogUtil.LogE(e);
            filePath = fileName;
        }
        LogUtil.LogI(String.format(UIUtil.getString(R.string.file_info), outputStream.toByteArray().length / 1024, filePath));
        return filePath;
    }
}
