package com.atman.jixin.ui.personal;

import android.os.Bundle;
import android.view.View;

import com.atman.jixin.R;
import com.atman.jixin.ui.base.MyBaseActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 描述 用户协议
 * 作者 tangbingliang
 * 时间 16/7/6 16:17
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class UserAgreementActivity extends MyBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        disableLoginCheck();
        setContentView(R.layout.activity_useragreement);
        ButterKnife.bind(this);
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);
        hideTitleBar();
    }

    @OnClick(R.id.agreement_back_iv)
    public void onClick() {
        finish();
    }
}
