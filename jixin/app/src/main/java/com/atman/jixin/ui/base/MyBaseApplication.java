package com.atman.jixin.ui.base;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.support.multidex.MultiDex;
import android.text.TextUtils;

import com.atman.jixin.R;
import com.atman.jixin.model.greendao.gen.DaoMaster;
import com.atman.jixin.model.greendao.gen.DaoSession;
import com.atman.jixin.model.response.LoginResultModel;
import com.base.baselibs.base.BaseApplication;
import com.base.baselibs.util.PreferenceUtil;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/6/30 14:31
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class MyBaseApplication extends BaseApplication {

    private static MyBaseApplication mInstance;

    public static String VERSION = "";
    public static String PLATFORM = "";
    public static String DEVICETYPE = "";
    public static String CHANNEL = "";

    public static String DEVICETOKEN = "";
    public static String USERNAME = "";
    public static String PASSWORD = "";
    public static String USERKEY = "";
    public static String USERTOKEN = "";
    public static String USERID = "";

    public static LoginResultModel USERINFOR;

    public DisplayImageOptions optionsHead;
    public DisplayImageOptions optionsNot;

    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        initInformation();
        initLoginInformation();
        setConfigLoad();
        initImageLoad();

        setDatabase();
    }

    /**     * 设置greenDao     */
    private void setDatabase() {

        // 通过DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为greenDAO 已经帮你做了。
        // 注意：默认的DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        mHelper = new DaoMaster.DevOpenHelper(this,"jiying_atmandb", null);
        db =mHelper.getWritableDatabase();
        // 注意：该数据库连接属于DaoMaster，所以多个 Session 指的是相同的数据库连接。
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }


    public DaoSession getDaoSession() {
        return mDaoSession;
    }


    public SQLiteDatabase getDb() {
        return db;
    }

    private void initImageLoad() {
        optionsHead = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.img_sign_logo) //设置图片在下载期间显示的图片
                .showImageForEmptyUri(R.mipmap.img_sign_logo)//设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.mipmap.img_sign_logo)  //设置图片加载/解码过程中错误时候显示的图片
                .cacheInMemory(true)//设置下载的图片是否缓存在内存中
                .cacheOnDisc(true)//设置下载的图片是否缓存在SD卡中
                .considerExifParams(true)  //是否考虑JPEG图像EXIF参数（旋转，翻转）
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)//设置图片以如何的编码方式显示
                .bitmapConfig(Bitmap.Config.RGB_565)//设置图片的解码类型//
                .resetViewBeforeLoading(true)//设置图片在下载前是否重置，复位
                .build();//构建完成

        optionsNot = new DisplayImageOptions.Builder()
                .cacheInMemory(true)//设置下载的图片是否缓存在内存中
                .cacheOnDisc(true)//设置下载的图片是否缓存在SD卡中
                .considerExifParams(true)  //是否考虑JPEG图像EXIF参数（旋转，翻转）
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)//设置图片以如何的编码方式显示
                .bitmapConfig(Bitmap.Config.RGB_565)//设置图片的解码类型//
                .resetViewBeforeLoading(true)//设置图片在下载前是否重置，复位
                .build();//构建完成
    }

    public boolean isLogined() {
        initLoginInformation();
        return !USERNAME.isEmpty() && !PASSWORD.isEmpty() &&
                !USERKEY.isEmpty() && !USERTOKEN.isEmpty() && !USERID.isEmpty();
    }

    private void initLoginInformation() {
        USERNAME = PreferenceUtil.getPreferences(getApplicationContext(), PreferenceUtil.PARM_US);
        PASSWORD = PreferenceUtil.getPreferences(getApplicationContext(), PreferenceUtil.PARM_PW);
        USERKEY = PreferenceUtil.getPreferences(getApplicationContext(), PreferenceUtil.PARM_USER_KEY);
        USERTOKEN = PreferenceUtil.getPreferences(getApplicationContext(), PreferenceUtil.PARM_USER_TOKEN);
        USERID = PreferenceUtil.getPreferences(getApplicationContext(), PreferenceUtil.PARM_USERID);
        DEVICETOKEN = PreferenceUtil.getPreferences(getApplicationContext(), PreferenceUtil.PARM_USER_TOKEN);
    }

    private void initInformation() {
        VERSION = getAppInfo().split("-")[0];
        PLATFORM = "android "+android.os.Build.VERSION.RELEASE;
        DEVICETYPE = android.os.Build.MODEL;
        CHANNEL = "android_"+getAppMetaData(getApplicationContext(), "UMENG_CHANNEL");
    }

    /**
     * 获取application中指定的meta-data
     * @return 如果没有获取成功(没有对应值，或者异常)，则返回值为空
     */
    public static String getAppMetaData(Context ctx, String key) {
        if (ctx == null || TextUtils.isEmpty(key)) {
            return null;
        }
        String resultData = null;
        try {
            PackageManager packageManager = ctx.getPackageManager();
            if (packageManager != null) {
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(ctx.getPackageName(), PackageManager.GET_META_DATA);
                if (applicationInfo != null) {
                    if (applicationInfo.metaData != null) {
                        resultData = applicationInfo.metaData.getString(key);
                    }
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return resultData;
    }

    private String getAppInfo() {
        try {
            String pkName = this.getPackageName();
            String versionName = this.getPackageManager().getPackageInfo(pkName, 0).versionName;
            int versionCode = this.getPackageManager().getPackageInfo(pkName, 0).versionCode;
            return versionName;
        } catch (Exception e) {
        }
        return null;
    }

    public static MyBaseApplication getApplication() {
        return mInstance;
    }

    private void setConfigLoad() {
        File cacheDir = StorageUtils.getOwnCacheDirectory(this, "imageloader/Cache");
        ImageLoaderConfiguration config = new ImageLoaderConfiguration
                .Builder(this)
                .memoryCacheExtraOptions(480, 800) // maxwidth, max height，即保存的每个缓存文件的最大长宽
                .threadPoolSize(6)//线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY -2)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new UsingFreqLimitedMemoryCache(2* 1024 * 1024)) // You can pass your own memory cache implementation/你可以通过自己的内存缓存实现
                .memoryCacheSize(4 * 1024 * 1024)
                .discCacheSize(50 * 1024 * 1024)
                .discCacheFileNameGenerator(new Md5FileNameGenerator())//将保存的时候的URI名称用MD5 加密
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .discCacheFileCount(100) //缓存的文件数量
                .discCache(new UnlimitedDiskCache(cacheDir))//自定义缓存路径
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .imageDownloader(new BaseImageDownloader(this,5 * 1000, 30 * 1000)) // connectTimeout (5 s), readTimeout (30 s)超时时间
                .writeDebugLogs() // Remove for releaseapp
                .build();//开始构建
        ImageLoader.getInstance().init(config);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public String getCookie() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("USER_KEY=");
        stringBuilder.append(PreferenceUtil.getPreferences(getApplicationContext(), PreferenceUtil.PARM_USER_KEY));
        stringBuilder.append(";USER_TOKEN=");
        stringBuilder.append(PreferenceUtil.getPreferences(getApplicationContext(), PreferenceUtil.PARM_USER_TOKEN));stringBuilder.append(";USER_TOKEN=");
        stringBuilder.append(PreferenceUtil.getPreferences(getApplicationContext(), PreferenceUtil.PARM_USER_TOKEN));
        return stringBuilder.toString();
    }

    public Map<String, String> getHeaderSeting() {
        Map<String, String> m = new HashMap<>();
        m.put("Accept", "application/json");
        m.put("Content-Type", "application/json");
        return m;
    }

    public void cleanLoginData() {
        PreferenceUtil.savePreference(getApplicationContext(), PreferenceUtil.PARM_PW, "");
        PreferenceUtil.savePreference(getApplicationContext(), PreferenceUtil.PARM_USER_KEY, "");
        PreferenceUtil.savePreference(getApplicationContext(), PreferenceUtil.PARM_USER_TOKEN, "");
        PreferenceUtil.savePreference(getApplicationContext(), PreferenceUtil.PARM_USERID, "");
        PreferenceUtil.savePreference(getApplicationContext(), PreferenceUtil.PARM_USER_IMG, "");
        USERINFOR = null;
    }
}
