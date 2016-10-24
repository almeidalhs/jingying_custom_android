package com.atman.jixin.ui.shop;

import android.os.Bundle;
import android.view.View;

import com.atman.jixin.R;
import com.atman.jixin.ui.base.MyBaseActivity;

import okhttp3.Response;

/**
 * Created by tangbingliang on 16/10/24.
 */

public class IntegralexchangeActivity extends MyBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_integralexchange);
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);
        setBarTitleTx("积分商城(0)");
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
}
