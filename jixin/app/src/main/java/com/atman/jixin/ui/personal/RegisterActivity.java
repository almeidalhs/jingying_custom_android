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
import com.base.baselibs.widget.MyCleanEditText;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
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
        hideTitleBar();
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

    @OnClick({R.id.register_back_iv, R.id.register_bt, R.id.register_agreement_tx})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register_back_iv:
                finish();
                break;
            case R.id.register_bt:
                break;
            case R.id.register_agreement_tx:
                startActivity(new Intent(mContext, UserAgreementActivity.class));
                break;
        }
    }
}
