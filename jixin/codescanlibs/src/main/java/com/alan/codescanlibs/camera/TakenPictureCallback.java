package com.alan.codescanlibs.camera;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by tangbingliang on 16/12/13.
 */

public class TakenPictureCallback implements Camera.PictureCallback {
    private static final String TAG = PreviewCallback.class.getName();
    private final CameraConfigurationManager mConfigManager;
    private Handler mPreviewHandler;
    private int mPreviewMessage;

    TakenPictureCallback(CameraConfigurationManager configManager) {
        this.mConfigManager = configManager;
    }

    void setHandler(Handler previewHandler, int previewMessage) {
        this.mPreviewHandler = previewHandler;
        this.mPreviewMessage = previewMessage;
    }

    @Override
    public void onPictureTaken(byte[] data, Camera camera) {
        String path = Environment.getExternalStorageDirectory() + "/jiying/image/";

        Bitmap bMap;
        try {// 获得图片
            bMap = BitmapFactory.decodeByteArray(data, 0, data.length);

            Bitmap bMapRotate;
            Matrix matrix = new Matrix();
            matrix.reset();
            matrix.postRotate(90);
            bMapRotate = Bitmap.createBitmap(bMap, 0, 0, bMap.getWidth(),
                    bMap.getHeight(), matrix, true);
            bMap = bMapRotate;

            // Bitmap bm = BitmapFactory.decodeByteArray(data, 0, data.length);
            File file = new File(path,System.currentTimeMillis()+".jpg");
            BufferedOutputStream bos =
                    new BufferedOutputStream(new FileOutputStream(file));
            bMap.compress(Bitmap.CompressFormat.JPEG, 100, bos);//将图片压缩到流中
            bos.flush();//输出
            bos.close();//关闭
            if (mPreviewHandler != null) {
                Log.e(TAG, "file.getPath():"+file.getPath());
                Log.e(TAG, "mPreviewMessage:"+mPreviewMessage);
                Message message = mPreviewHandler.obtainMessage(mPreviewMessage, file.getPath());
                message.sendToTarget();
                mPreviewHandler = null;
            } else {
                Log.v(TAG, "no handler callback.");
            }
            // 在拍照的时候相机是被占用的,拍照之后需要重新预览
            camera.startPreview();
        } catch(Exception e) {
            e.printStackTrace();
        }

//        try {
//            File file = new File(path,System.currentTimeMillis()+".jpg");
//            FileOutputStream fos = new FileOutputStream(file);
//            fos.write(data);
//            if (mPreviewHandler != null) {
//                Log.e(TAG, "file.getPath():"+file.getPath());
//                Log.e(TAG, "mPreviewMessage:"+mPreviewMessage);
//                Message message = mPreviewHandler.obtainMessage(mPreviewMessage, file.getPath());
//                message.sendToTarget();
//                mPreviewHandler = null;
//            } else {
//                Log.v(TAG, "no handler callback.");
//            }
//            // 在拍照的时候相机是被占用的,拍照之后需要重新预览
//            camera.startPreview();
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
    }
}
