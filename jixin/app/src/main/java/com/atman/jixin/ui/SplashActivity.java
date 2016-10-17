package com.atman.jixin.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.atman.jixin.R;
import com.atman.jixin.model.request.LoginRequestModel;
import com.atman.jixin.model.response.LoginResultModel;
import com.atman.jixin.ui.base.MyBaseActivity;
import com.atman.jixin.ui.base.MyBaseApplication;
import com.atman.jixin.ui.personal.LoginActivity;
import com.atman.jixin.utils.Common;
import com.atman.jixin.widget.downfile.DownloadFile;
import com.base.baselibs.iimp.TimeCountInterface;
import com.base.baselibs.net.MyStringCallback;
import com.base.baselibs.util.LogUtils;
import com.base.baselibs.util.MD5Util;
import com.base.baselibs.util.PreferenceUtil;
import com.base.baselibs.util.TimeCount;
import com.tbl.okhttputils.OkHttpUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by loyee on 16-1-5.
 */
public class SplashActivity extends MyBaseActivity implements TimeCountInterface {
    @Bind(R.id.splash_iv_one)
    ImageView splashIvOne;
    @Bind(R.id.splash_iv_two)
    ImageView splashIvTwo;
    @Bind(R.id.jump_tx)
    TextView jumpTx;

    private boolean isInit = true;
    private TimeCount timeCount;
    private Context mContext = SplashActivity.this;

    private int unLoginTime = 5000;
    private int loginTime = 500;
    private int defalTime = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        disableLoginCheck();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        setSwipeBackEnable(false);

        if (MyBaseApplication.PASSWORD.isEmpty()) {
            jumpTx.setVisibility(View.VISIBLE);
            timeCount = new TimeCount(unLoginTime, defalTime, this);
        } else {
            jumpTx.setVisibility(View.GONE);
            timeCount = new TimeCount(loginTime, defalTime, this);
        }
        timeCount.start();

        getSomedate();
    }

    private void getSomedate() {
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;  // 屏幕宽度（像素）
        int height = metric.heightPixels;  // 屏幕高度（像素）
        float density = metric.density;  // 屏幕密度（0.75 / 1.0 / 1.5）
        int densityDpi = metric.densityDpi;  // 屏幕密度DPI（120 / 160 / 240）
        float scaledDensity = metric.scaledDensity;
        LogUtils.e("width:"+width+",height:"+height+",density:"+density+",densityDpi:"+densityDpi+",scaledDensity:"+scaledDensity);
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);
        hideTitleBar();
    }

    private void redirectTo() {
        if (isInit) {
            isInit = false;
            boolean isLearned = PreferenceUtil.getBoolPreferences(this, "isLearned");
            if (!isLearned) {
                goGuide();
            } else {
                goHome();
            }
        }
    }

    private void goHome() {
        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        finish();
    }

    private void goGuide() {
//        startActivity(new Intent(this, GuideActivity.class));
        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        new DownloadFile(SplashActivity.this, splashIvOne, splashIvTwo)
                .execute("http://www.justing.com/baiye/config.json");
    }

    @OnClick(R.id.jump_tx)
    public void onClick() {
        timeCount.cancel();
        toMainActivity();
    }

    private void toMainActivity() {
        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        finish();
    }

    @Override
    public void onStringResponse(String data, Response response, int id) {
        super.onStringResponse(data, response, id);
        if (id == Common.NET_LOGIN_ID) {
            LoginResultModel mLoginResultModel = mGson.fromJson(data, LoginResultModel.class);
            MyBaseApplication.USERINFOR = mLoginResultModel;
            toMainActivity();
        }
    }

    @Override
    public void onError(Call call, Exception e, int code, int id) {
        super.onError(call, e, code, id);
        toMainActivity();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(Common.NET_LOGIN_ID);
    }

    @Override
    public void onTimeOut() {
        if (MyBaseApplication.PASSWORD.isEmpty()) {
            toMainActivity();
        } else {
            LoginRequestModel mLoginRequestModel = new LoginRequestModel(MyBaseApplication.USERNAME
                    , MyBaseApplication.PASSWORD, MyBaseApplication.USERTOKEN
                    , MyBaseApplication.VERSION, MyBaseApplication.PLATFORM
                    , MyBaseApplication.DEVICETYPE, MyBaseApplication.CHANNEL);
            LogUtils.e("mGson.toJson(mLoginRequestModel):"+mGson.toJson(mLoginRequestModel));
            OkHttpUtils.postString()
                    .url(Common.Url_Login).tag(Common.NET_LOGIN_ID).id(Common.NET_LOGIN_ID)
                    .content(mGson.toJson(mLoginRequestModel))
                    .addHeader("cookie",MyBaseApplication.getApplication().getCookie())
                    .build().connTimeOut(Common.timeOut).readTimeOut(Common.timeOut).writeTimeOut(Common.timeOut)
                    .execute(new MyStringCallback(mContext, "玩命登录中", this, true));
        }
    }

    @Override
    public void onBackTick(long millisUntilFinished) {
        jumpTx.setText("跳过 " + millisUntilFinished / 1000 + " s");
    }

    @OnClick({R.id.splash_iv_one, R.id.splash_iv_two})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.splash_iv_one:
            case R.id.splash_iv_two:
                timeCount.cancel();
                startActivity(MainActivity.buildIntent(SplashActivity.this,true));
                finish();
                break;
        }
    }
}
