package com.atman.jixin.ui.personal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.atman.jixin.R;
import com.atman.jixin.ui.base.MyBaseActivity;
import com.atman.jixin.ui.base.MyBaseApplication;
import com.atman.jixin.utils.Common;
import com.base.baselibs.net.BaseErrorTwoModel;
import com.base.baselibs.net.BaseNormalModel;
import com.base.baselibs.net.MyStringCallback;
import com.base.baselibs.util.LogUtils;
import com.base.baselibs.util.MD5Util;
import com.base.baselibs.util.PreferenceUtil;
import com.base.baselibs.util.StringUtils;
import com.base.baselibs.util.TimeCount;
import com.base.baselibs.widget.MyCleanEditText;
import com.tbl.okhttputils.OkHttpUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by tangbingliang on 16/10/14.
 */

public class RegisterActivity extends MyBaseActivity {

    @Bind(R.id.register_back_iv)
    ImageView registerBackIv;
    @Bind(R.id.register_username_et)
    MyCleanEditText registerUsernameEt;
    @Bind(R.id.register_password_et)
    MyCleanEditText registerPasswordEt;
    @Bind(R.id.register_code_et)
    MyCleanEditText registerCodeEt;
    @Bind(R.id.register_code_tv)
    TextView registerCodeTv;
    @Bind(R.id.register_bt)
    Button registerBt;
    @Bind(R.id.register_warn_tx)
    TextView registerWarnTx;
    @Bind(R.id.register_agreement_tx)
    TextView registerAgreementTx;

    private Context mContext = RegisterActivity.this;

    private TimeCount timeCount;
    private String aount;
    private String password;
    private String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        disableLoginCheck();
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);
        if (isVisitors()) {
            setBarTitleTx("绑定手机号");
            registerBackIv.setVisibility(View.GONE);
        } else {
            hideTitleBar();
        }

        timeCount = new TimeCount(registerCodeTv, 60 * 1000, 1000);

        registerUsernameEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    registerUsernameEt.setHint("");
                } else {
                    registerUsernameEt.setHint("手机号");
                }
            }
        });
        registerPasswordEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    registerPasswordEt.setHint("");
                } else {
                    registerPasswordEt.setHint("密  码");
                }
            }
        });
        registerCodeEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    registerCodeEt.setHint("");
                } else {
                    registerCodeEt.setHint("验证码");
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void doInitBaseHttp() {
        super.doInitBaseHttp();
    }

    @Override
    public void onError(Call call, Exception e, int code, int id) {
        super.onError(call, e, code, id);
        timeCount.cancel();
        registerCodeTv.setText("获取短信验证码");
    }

    @Override
    public void onStringResponse(String data, Response response, int id) {
        super.onStringResponse(data, response, id);
        if (id == Common.NET_SMS_ID) {
            BaseNormalModel base = mGson.fromJson(data, BaseNormalModel.class);
            if (base.getResult().equals("1")) {
                showToast("验证码发送成功！");
                timeCount.start();
            } else {
                BaseErrorTwoModel errorTwoModel = mGson.fromJson(data, BaseErrorTwoModel.class);
                showToast(errorTwoModel.getBody().getMessage());
            }
        } else if (id == Common.NET_CHECKCODE_ID) {
            BaseNormalModel base = mGson.fromJson(data, BaseNormalModel.class);
            if (base.getResult().equals("1")) {
                if (isVisitors()) {
                    Map<String, Object> p = new HashMap<>();
                    p.put("userName", aount);
                    p.put("password", MD5Util.getMD5(password));
                    OkHttpUtils.postString()
                            .url(Common.Url_Bind_Phone).tag(Common.NET_BING_PHONE_ID).id(Common.NET_BING_PHONE_ID)
                            .content(mGson.toJson(p)).addHeader("cookie",MyBaseApplication.getApplication().getCookie())
                            .mediaType(Common.JSON).headers(MyBaseApplication.getApplication().getHeaderSeting())
                            .build().connTimeOut(Common.timeOut).readTimeOut(Common.timeOut).writeTimeOut(Common.timeOut)
                            .execute(new MyStringCallback(mContext, "绑定中...", this, true, false));
                } else {
                    Map<String, Object> p = new HashMap<>();
                    p.put("deviceToken", PreferenceUtil.getPreferences(getApplicationContext(), PreferenceUtil.PARM_USER_KEY));
                    p.put("idfa", "");
                    p.put("isTestToken", false);
                    p.put("language", "zh_CN");
                    p.put("platform", "android");
                    p.put("version", MyBaseApplication.VERSION);
                    p.put("userName", aount);
                    p.put("password", MD5Util.getMD5(password));
                    p.put("login_terminal", 0);
                    String str = mGson.toJson(p);
                    OkHttpUtils.postString().url(Common.Url_Register).content(str).mediaType(Common.JSON)
                            .headers(MyBaseApplication.getApplication().getHeaderSeting())
                            .addHeader("cookie",MyBaseApplication.getApplication().getCookie())
                            .tag(Common.NET_REGISTER_ID).id(Common.NET_REGISTER_ID).build()
                            .execute(new MyStringCallback(mContext, "注册中...", this, true));
                }
            } else {
                BaseErrorTwoModel errorTwoModel = mGson.fromJson(data, BaseErrorTwoModel.class);
                showToast(errorTwoModel.getBody().getMessage());
            }
        } else if (id == Common.NET_REGISTER_ID) {
            BaseNormalModel base = mGson.fromJson(data, BaseNormalModel.class);
            if (base.getResult().equals("1")) {
                showToast("注册成功！");
                Intent mIntent = new Intent();
                setResult(RESULT_OK,mIntent);
                mIntent.putExtra("name", aount);
                mIntent.putExtra("password", password);
                finish();
            }
        } else if (id == Common.NET_BING_PHONE_ID) {
            showToast("绑定成功,请重新登录！");
            clearData();
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timeCount.cancel();
        OkHttpUtils.getInstance().cancelTag(Common.NET_SMS_ID);
        OkHttpUtils.getInstance().cancelTag(Common.NET_CHECKCODE_ID);
        OkHttpUtils.getInstance().cancelTag(Common.NET_REGISTER_ID);
        OkHttpUtils.getInstance().cancelTag(Common.NET_BING_PHONE_ID);
    }

    @OnClick({R.id.register_back_iv, R.id.register_bt, R.id.register_agreement_tx, R.id.register_code_tv})
    public void onClick(View view) {
        aount = registerUsernameEt.getText().toString().trim();
        password = registerPasswordEt.getText().toString();
        code = registerCodeEt.getText().toString().trim();
        switch (view.getId()) {
            case R.id.register_back_iv:
                finish();
                break;
            case R.id.register_bt:
                if (checkInput(2)) {
                    return;
                }
                OkHttpUtils.put().url(Common.Url_FORGOT + aount + "/" + code).requestBody("{}")
                        .headers(MyBaseApplication.getApplication().getHeaderSeting())
                        .addHeader("cookie",MyBaseApplication.getApplication().getCookie())
                        .tag(Common.NET_CHECKCODE_ID).id(Common.NET_CHECKCODE_ID).build()
                        .execute(new MyStringCallback(mContext, "", this, true));
                break;
            case R.id.register_agreement_tx:
                startActivity(new Intent(mContext, UserAgreementActivity.class));
                break;
            case R.id.register_code_tv:
                if (checkInput(1)) {
                    return;
                }
                OkHttpUtils.get().url(Common.Url_FORGOT + aount + Common.SEED_MEESAGE_AFTER_FORGOT+1)
                        .headers(MyBaseApplication.getApplication().getHeaderSeting())
                        .addHeader("cookie",MyBaseApplication.getApplication().getCookie())
                        .tag(Common.NET_SMS_ID).id(Common.NET_SMS_ID).build()
                        .execute(new MyStringCallback(mContext, "", this, true));
                break;
        }
    }

    private boolean checkInput(int num) {
        if (aount.isEmpty()) {
            showToast("请输入手机号");
            return true;
        } else if (!StringUtils.isPhone(aount)) {
            showToast("请输入正确的手机号");
            return true;
        }
        if (num != 1) {
            if (password.isEmpty()) {
                showToast("请输入登录密码");
                return true;
            } else if (password.length()<6) {
                showToast("密码长度为 6-16位");
                return true;
            } else if (code.length()!=6) {
                showToast("请输入6位数字的验证码");
                return true;
            }
        }
        return false;
    }
}
