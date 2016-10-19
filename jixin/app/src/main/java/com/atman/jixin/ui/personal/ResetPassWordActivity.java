package com.atman.jixin.ui.personal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.atman.jixin.R;
import com.atman.jixin.model.response.ResetPassWordResultModel;
import com.atman.jixin.ui.MainActivity;
import com.atman.jixin.ui.base.MyBaseActivity;
import com.atman.jixin.ui.base.MyBaseApplication;
import com.atman.jixin.utils.Common;
import com.base.baselibs.net.MyStringCallback;
import com.base.baselibs.util.MD5Util;
import com.base.baselibs.util.PreferenceUtil;
import com.base.baselibs.widget.MyCleanEditText;
import com.tbl.okhttputils.OkHttpUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Response;

/**
 * Created by tangbingliang on 16/10/19.
 */

public class ResetPassWordActivity extends MyBaseActivity {

    @Bind(R.id.reserPW_old_password_et)
    MyCleanEditText reserPWOldPasswordEt;
    @Bind(R.id.reserPW_new_password_et)
    MyCleanEditText reserPWNewPasswordEt;
    @Bind(R.id.reserPW_conf_password_et)
    MyCleanEditText reserPWConfPasswordEt;

    private Context mContext = ResetPassWordActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        disableLoginCheck();
        setContentView(R.layout.activity_resetpassword);
        ButterKnife.bind(this);
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);
        setBarTitleTx("修改密码");
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
        if (id == Common.NET_RESET_PASSWORD_ID) {
            ResetPassWordResultModel temp = mGson.fromJson(data, ResetPassWordResultModel.class);
            if (temp.getResult().equals("1")) {
                showToast("密码修改成功，请重新登录");
                MyBaseApplication.USERINFOR = null;
                PreferenceUtil.savePreference(mContext, PreferenceUtil.PARM_PW, "");
                Intent intent = new Intent();
                intent.setClass(mContext, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            } else {
                showToast(temp.getBody().getMessage());
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(Common.NET_RESET_PASSWORD_ID);
    }

    @OnClick({R.id.resetPW_commit_bt})
    public void onClick(View view) {
        String mOldPW = reserPWOldPasswordEt.getText().toString().trim();
        String mNewPW = reserPWNewPasswordEt.getText().toString().trim();
        String mConfPW = reserPWConfPasswordEt.getText().toString().trim();
        switch (view.getId()) {
            case R.id.resetPW_commit_bt:
                if(!chcek(mOldPW, mNewPW, mConfPW)){
                    return;
                }
                OkHttpUtils.put().url(Common.Url_Reset_PassWord + MD5Util.getMD5(mOldPW) + "/" + MD5Util.getMD5(mConfPW))
                        .requestBody("{}").headers(MyBaseApplication.getApplication().getHeaderSeting())
                        .addHeader("cookie",MyBaseApplication.getApplication().getCookie())
                        .tag(Common.NET_RESET_PASSWORD_ID).id(Common.NET_RESET_PASSWORD_ID).build()
                        .execute(new MyStringCallback(mContext, "重置中...", ResetPassWordActivity.this, true));

//                OkHttpUtils.put().url(Common.Url_Reset_PassWord
//                        + MD5Util.getMD5(mOldPW) + "/" + MD5Util.getMD5(mConfPW)).requestBody("{}")
//                        .headers(MyBaseApplication.getApplication().getHeaderSeting())
//                        .addHeader("cookie",MyBaseApplication.getApplication().getCookie())
//                        .tag(Common.NET_RESET_PASSWORD_ID).id(Common.NET_RESET_PASSWORD_ID).build()
//                        .execute(new MyStringCallback(mContext, "重置中...", ResetPassWordActivity.this, true));
                break;
        }
    }

    private boolean chcek(String mOldPW, String mNewPW, String mConfPW) {
        if (mOldPW.isEmpty()) {
            showToast("请输入旧密码");
            return false;
        } else if (mNewPW.isEmpty()) {
            showToast("请输入新密码");
            return false;
        } else if (mConfPW.isEmpty()) {
            showToast("请输入重复新密码");
            return false;
        } else if (mOldPW.length()<6 || mNewPW.length()<6 || mConfPW.length()<6) {
            showToast("密码长度为6-17位");
            return false;
        }else if (!mConfPW.equals(mNewPW)) {
            showToast("两次输入的新密码不一致，请重新输入");
            return false;
        }
        return true;
    }
}
