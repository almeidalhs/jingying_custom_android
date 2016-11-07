package com.base.baselibs.base;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.view.Display;
import android.view.WindowManager;

import com.base.baselibs.widget.localalbum.common.LocalImageHelper;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.tbl.okhttputils.utils.L;

import java.io.File;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/6/28 15:17
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class BaseApplication extends Application {

    private static Context mBaseContext = null;
    protected static BaseApplication mBaseInstance = null;
    private Display display;

    @Override
    public void onCreate() {
        super.onCreate();
        mBaseContext = getApplicationContext();
        mBaseInstance = this;
        //本地图片辅助类初始化
        LocalImageHelper.init(this);
    }

    public static Context getmContext() {
        return mBaseContext;
    }

    public static BaseApplication getBaseApp(){
        return mBaseInstance;
    }

    /**
     * 但是当我们没在AndroidManifest.xml中设置其debug属性时:
     * 使用Eclipse运行这种方式打包时其debug属性为true,使用Eclipse导出这种方式打包时其debug属性为法false.
     * 在使用ant打包时，其值就取决于ant的打包参数是release还是debug.
     * 因此在AndroidMainifest.xml中最好不设置android:debuggable属性置，而是由打包方式来决定其值.
     */
    public boolean isApkDebugable() {
        try {
            ApplicationInfo info= getApplicationInfo();
            return (info.flags& ApplicationInfo.FLAG_DEBUGGABLE)!=0;
        } catch (Exception e) {
            L.e("e:"+e.toString());
        }
        return false;
    }
}
