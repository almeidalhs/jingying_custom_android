package com.atman.jixin.ui.shop;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.atman.jixin.R;
import com.atman.jixin.ui.base.MyBaseActivity;

import okhttp3.Response;

/**
 * Created by tangbingliang on 16/10/24.
 */

public class ExchangeRecordActivity extends MyBaseActivity {

    private Context mContext = ExchangeRecordActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchangerecord);
    }

    public static Intent buildIntent (Context context, long storeId) {
        Intent intent = new Intent(context, ExchangeRecordActivity.class);
        intent.putExtra("storeId", storeId);
        return intent;
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);
        setBarTitleTx("兑换记录");
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
}
