package com.atman.jixin.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

import com.base.baselibs.widget.PromptDialog;

import java.io.File;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/7/8 16:49
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class UiHelper {

    public static String photo(final Context context, String path, int TAKE_PICTURE) {

        if (!MyTools.cameraIsCanUse()) {
            PromptDialog.Builder builder = new PromptDialog.Builder(context);
            builder.setMessage("您没有开启照相机权限？");
            builder.setPositiveButton("去开启", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    context.startActivity(getAppDetailSettingIntent(context));
                }
            });
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.show();
            return "";
        }

        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            Intent getImageByCamera = new Intent("android.media.action.IMAGE_CAPTURE");
            String out_file_path = FileUtils.SDPATH;
            File dir = new File(out_file_path);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            path = out_file_path + System.currentTimeMillis() + ".jpg";
            getImageByCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(path)));
            ((Activity)context).startActivityForResult(getImageByCamera, TAKE_PICTURE);
        } else {
            Toast.makeText(context, "请确认已经插入SD卡", Toast.LENGTH_LONG).show();
        }
        return path;
    }

    public static String photoBefor(final Context context, String path, int TAKE_PICTURE) {

        if (!MyTools.cameraIsCanUse()) {
            PromptDialog.Builder builder = new PromptDialog.Builder(context);
            builder.setMessage("您没有开启照相机权限？");
            builder.setPositiveButton("去开启", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    context.startActivity(getAppDetailSettingIntent(context));
                }
            });
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.show();
            return "";
        }

        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            Intent getImageByCamera = new Intent("android.media.action.IMAGE_CAPTURE");
            String out_file_path = FileUtils.SDPATH;
            File dir = new File(out_file_path);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            path = out_file_path + System.currentTimeMillis() + ".jpg";
            getImageByCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(path)));

            getImageByCamera.putExtra("camerasensortype", 2); // 调用前置摄像头
            getImageByCamera.putExtra("autofocus", true); // 自动对焦
            getImageByCamera.putExtra("fullScreen", false); // 全屏
            getImageByCamera.putExtra("showActionIcons", false);

            ((Activity)context).startActivityForResult(getImageByCamera, TAKE_PICTURE);
        } else {
            Toast.makeText(context, "请确认已经插入SD卡", Toast.LENGTH_LONG).show();
        }
        return path;
    }

    public static Intent getAppDetailSettingIntent(Context context) {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", "com.atman.jixin",null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings","com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", "com.atman.jixin");
        }
        return localIntent;
    }

    /**
     * 判断是否平板设备
     * @param context
     * @return true:平板,false:手机
     */
    public static boolean isTabletDevice(Context context) {
        return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >=
                Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    public static void sendSMS(Context context, String content, String number){
        Uri smsToUri = Uri.parse("smsto:"+number);
        Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);
        intent.putExtra("sms_body", content);
        context.startActivity(intent);
    }
}
