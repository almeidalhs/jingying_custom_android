package com.atman.jixin.ui.personal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.atman.jixin.R;
import com.atman.jixin.model.request.LoginRequestModel;
import com.atman.jixin.model.response.LoginResultModel;
import com.atman.jixin.ui.MainActivity;
import com.atman.jixin.ui.base.MyBaseActivity;
import com.atman.jixin.ui.base.MyBaseApplication;
import com.atman.jixin.utils.Common;
import com.base.baselibs.net.MyStringCallback;
import com.base.baselibs.util.LogUtils;
import com.base.baselibs.util.MD5Util;
import com.base.baselibs.util.PreferenceUtil;
import com.base.baselibs.util.StringUtils;
import com.base.baselibs.widget.MyCleanEditText;
import com.base.baselibs.widget.ShapeImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tbl.okhttputils.OkHttpUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Response;

/**
 * Created by tangbingliang on 16/10/14.
 */

public class LoginActivity extends MyBaseActivity {

    @Bind(R.id.login_register_tx)
    TextView loginRegisterTx;
    @Bind(R.id.part_store_preview_head_img)
    ShapeImageView partStorePreviewHeadImg;
    @Bind(R.id.login_username_et)
    MyCleanEditText loginUsernameEt;
    @Bind(R.id.login_password_et)
    MyCleanEditText loginPasswordEt;
    @Bind(R.id.login_bt)
    Button loginBt;
    @Bind(R.id.login_forget_pw_tx)
    TextView loginForgetPwTx;

    private Context mContext = LoginActivity.this;
    private LoginResultModel mLoginResultModel;
    private String username;
    private String password;
    private String headImge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        disableLoginCheck();
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        setSwipeBackEnable(false);
    }

    /**
     * 获取Intent
     *
     * @param context
     * @param successIntent 登陆成功后跳转的activity 的Intent
     * @return
     */
    public static Intent createIntent(Context context, Intent successIntent) {
        Intent intent = new Intent(context, LoginActivity.class);
        if (successIntent != null) {
            intent.putExtra("successIntent", successIntent);
        }
        return intent;
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);
        hideTitleBar();
        String acount = PreferenceUtil.getPreferences(mContext, PreferenceUtil.PARM_US);
        try {
            Long dCheckValue = Long.parseLong(acount);
            if (dCheckValue instanceof Long == true) {
                loginUsernameEt.setText(acount);
            }
        } catch(NumberFormatException e) {
        }
        headImge = PreferenceUtil.getPreferences(mContext, PreferenceUtil.PARM_USER_IMG);
        if (!headImge.isEmpty()) {
            ImageLoader.getInstance().displayImage(Common.ImageUrl+headImge, partStorePreviewHeadImg
                    , MyBaseApplication.getApplication().optionsHead);
        }

        loginPasswordEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    loginPasswordEt.setHint("");
                } else {
                    loginPasswordEt.setHint("密 码");
                }
            }
        });
        loginUsernameEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    loginUsernameEt.setHint("");
                } else {
                    loginUsernameEt.setHint("手机号");
                }
            }
        });
    }

    @Override
    public void doInitBaseHttp() {
        super.doInitBaseHttp();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onStringResponse(String data, Response response, int id) {
        super.onStringResponse(data, response, id);
        if (id == Common.NET_LOGIN_ID) {
            mLoginResultModel = mGson.fromJson(data, LoginResultModel.class);
            MyBaseApplication.USERINFOR = mLoginResultModel;
            PreferenceUtil.savePreference(mContext,PreferenceUtil.PARM_PW, MD5Util.getMD5(password));
            PreferenceUtil.savePreference(mContext,PreferenceUtil.PARM_USERID, mLoginResultModel.getBody().getAtmanUserId()+"");
            PreferenceUtil.savePreference(mContext,PreferenceUtil.PARM_USER_IMG, mLoginResultModel.getBody().getMemberAvatar());
            showToast("登录成功");
            toMainActivity();
        }
    }

    private void toMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(Common.NET_LOGIN_ID);
    }

    @OnClick({R.id.login_register_tx, R.id.login_bt, R.id.login_forget_pw_tx})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_register_tx:
                startActivityForResult(new Intent(mContext, RegisterActivity.class), Common.TO_REGISTER);
                break;
            case R.id.login_bt:
                username = loginUsernameEt.getText().toString().trim();
                password = loginPasswordEt.getText().toString();
                if (checkInput()) {
                    return;
                }
                PreferenceUtil.savePreference(mContext,PreferenceUtil.PARM_US, username);
                LoginRequestModel mLoginRequestModel = new LoginRequestModel(username, MD5Util.getMD5(password)
                        , MyBaseApplication.USERTOKEN, MyBaseApplication.VERSION, MyBaseApplication.PLATFORM
                        , MyBaseApplication.DEVICETYPE, MyBaseApplication.CHANNEL);
                LogUtils.e("mGson.toJson(mLoginRequestModel):"+mGson.toJson(mLoginRequestModel));
                OkHttpUtils.postString()
                        .url(Common.Url_Login).tag(Common.NET_LOGIN_ID).id(Common.NET_LOGIN_ID)
                        .content(mGson.toJson(mLoginRequestModel))
                        .addHeader("cookie",MyBaseApplication.getApplication().getCookie())
                        .build().connTimeOut(Common.timeOut).readTimeOut(Common.timeOut).writeTimeOut(Common.timeOut)
                        .execute(new MyStringCallback(mContext, "Loading...", this, true));
                break;
            case R.id.login_forget_pw_tx:
                startActivity(new Intent(mContext, ForgetPassWordActivity.class));
                break;
        }
    }

    private boolean checkInput() {
        if (username.isEmpty()) {
            showToast("请输入手机号");
            return true;
        } else if (!StringUtils.isPhone(username)) {
            showToast("请输入正确的手机号");
            return true;
        } else if (password.isEmpty()) {
            showToast("请输入登录密码");
            return true;
        } else if (password.length()<6) {
            showToast("密码长度为 6-16位");
            return true;
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == Common.TO_REGISTER) {
            String name = data.getStringExtra("name");
            String pw = data.getStringExtra("password");
            password = pw;
            LoginRequestModel mLoginRequestModel = new LoginRequestModel(name, MD5Util.getMD5(pw)
                    , MyBaseApplication.USERTOKEN, MyBaseApplication.VERSION, MyBaseApplication.PLATFORM
                    , MyBaseApplication.DEVICETYPE, MyBaseApplication.CHANNEL);
            LogUtils.e("mGson.toJson(mLoginRequestModel):"+mGson.toJson(mLoginRequestModel));
            OkHttpUtils.postString()
                    .url(Common.Url_Login).tag(Common.NET_LOGIN_ID).id(Common.NET_LOGIN_ID)
                    .content(mGson.toJson(mLoginRequestModel))
                    .addHeader("cookie",MyBaseApplication.getApplication().getCookie())
                    .build().connTimeOut(Common.timeOut).readTimeOut(Common.timeOut).writeTimeOut(Common.timeOut)
                    .execute(new MyStringCallback(mContext, "Loading...", this, true));
        }
    }
}
