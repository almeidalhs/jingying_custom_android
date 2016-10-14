package com.atman.jixin.ui.personal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.atman.jixin.R;
import com.atman.jixin.ui.base.MyBaseActivity;
import com.base.baselibs.widget.MyCleanEditText;
import com.base.baselibs.widget.ShapeImageView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        disableLoginCheck();
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @OnClick({R.id.login_register_tx, R.id.login_bt, R.id.login_forget_pw_tx})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_register_tx:
                break;
            case R.id.login_bt:
                break;
            case R.id.login_forget_pw_tx:
                break;
        }
    }
}
