package com.atman.jixin.ui.personal;

import android.content.Context;
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
import com.base.baselibs.util.MD5Util;
import com.base.baselibs.util.StringUtils;
import com.base.baselibs.util.TimeCount;
import com.base.baselibs.widget.MyCleanEditText;
import com.tbl.okhttputils.OkHttpUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by tangbingliang on 16/10/14.
 */

public class ForgetPassWordActivity extends MyBaseActivity {
    @Bind(R.id.forget_back_iv)
    ImageView forgetBackIv;
    @Bind(R.id.forget_username_et)
    MyCleanEditText forgetUsernameEt;
    @Bind(R.id.forget_password_et)
    MyCleanEditText forgetPasswordEt;
    @Bind(R.id.forget_code_et)
    MyCleanEditText forgetCodeEt;
    @Bind(R.id.forget_bt)
    Button forgetBt;
    @Bind(R.id.forget_code_tv)
    TextView forgetCodeTv;

    private Context mContext = ForgetPassWordActivity.this;

    private String aount;
    private String newPassword;
    private String code;
    private TimeCount timeCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        disableLoginCheck();
        setContentView(R.layout.activity_forgetpassword);
        ButterKnife.bind(this);
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);
        hideTitleBar();

        timeCount = new TimeCount(forgetCodeTv, 60 * 1000, 1000, forgetUsernameEt);
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
                OkHttpUtils.put().url(Common.Url_ResetPWD + aount + "/" + MD5Util.getMD5(newPassword))
                        .headers(MyBaseApplication.getApplication().getHeaderSeting()).requestBody("{}")
                        .addHeader("cookie",MyBaseApplication.getApplication().getCookie())
                        .tag(Common.NET_RESETPWD_ID).id(Common.NET_RESETPWD_ID).build()
                        .execute(new MyStringCallback(mContext, "", this, true));
            } else {
                BaseErrorTwoModel errorTwoModel = mGson.fromJson(data, BaseErrorTwoModel.class);
                showToast(errorTwoModel.getBody().getMessage());
            }
        } else if (id == Common.NET_RESETPWD_ID) {
            BaseNormalModel base = mGson.fromJson(data, BaseNormalModel.class);
            if (base.getResult().equals("1")) {
                showToast("密码重置成功");
                finish();
            } else {
                showToast("密码重置失败");
            }
        }
    }

    @Override
    public void onError(Call call, Exception e, int code, int id) {
        super.onError(call, e, code, id);
        timeCount.cancel();
        forgetCodeTv.setText("获取短信验证码");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timeCount.cancel();
        OkHttpUtils.getInstance().cancelTag(Common.NET_SMS_ID);
        OkHttpUtils.getInstance().cancelTag(Common.NET_CHECKCODE_ID);
        OkHttpUtils.getInstance().cancelTag(Common.NET_RESETPWD_ID);
    }

    @OnClick({R.id.forget_back_iv, R.id.forget_bt, R.id.forget_code_tv})
    public void onClick(View view) {
        aount = forgetUsernameEt.getText().toString().trim();
        newPassword = forgetPasswordEt.getText().toString();
        code = forgetCodeEt.getText().toString().trim();
        switch (view.getId()) {
            case R.id.forget_back_iv:
                finish();
                break;
            case R.id.forget_code_tv:
                if (checkInput(1)) {
                    return;
                }
                OkHttpUtils.get().url(Common.Url_FORGOT + aount + Common.SEED_MEESAGE_AFTER_FORGOT+0)
                        .headers(MyBaseApplication.getApplication().getHeaderSeting())
                        .addHeader("cookie",MyBaseApplication.getApplication().getCookie())
                        .tag(Common.NET_SMS_ID).id(Common.NET_SMS_ID).build()
                        .execute(new MyStringCallback(mContext, "", this, true));
                break;
            case R.id.forget_bt:
                if (checkInput(2)) {
                    return;
                }
                OkHttpUtils.put().url(Common.Url_FORGOT + aount + "/" + code).requestBody("{}")
                        .headers(MyBaseApplication.getApplication().getHeaderSeting())
                        .addHeader("cookie",MyBaseApplication.getApplication().getCookie())
                        .tag(Common.NET_CHECKCODE_ID).id(Common.NET_CHECKCODE_ID).build()
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
            if (newPassword.isEmpty()) {
                showToast("请输入登录密码");
                return true;
            } else if (newPassword.length()<6) {
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
