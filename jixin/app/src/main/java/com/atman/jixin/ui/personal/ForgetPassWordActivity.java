package com.atman.jixin.ui.personal;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.atman.jixin.R;
import com.atman.jixin.ui.base.MyBaseActivity;
import com.base.baselibs.widget.MyCleanEditText;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
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

    private Context mContext = ForgetPassWordActivity.this;

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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @OnClick({R.id.forget_back_iv, R.id.forget_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.forget_back_iv:
                break;
            case R.id.forget_bt:
                break;
        }
    }
}
