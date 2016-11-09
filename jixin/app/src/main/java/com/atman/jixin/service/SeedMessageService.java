package com.atman.jixin.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.base.baselibs.util.LogUtils;

/**
 * Created by tangbingliang on 16/11/8.
 */

public class SeedMessageService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        LogUtils.e("in onBind");
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.e("in onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtils.e("in onStartCommand");
        LogUtils.e("MyService:" + this);
        String name = intent.getStringExtra("name");
        LogUtils.e("name:" + name);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.e("in onDestroy");
    }
}
