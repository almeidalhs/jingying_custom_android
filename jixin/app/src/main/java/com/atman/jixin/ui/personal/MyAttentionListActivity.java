package com.atman.jixin.ui.personal;

import android.os.Bundle;
import android.view.View;

import com.atman.jixin.R;
import com.atman.jixin.ui.base.MyBaseActivity;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by tangbingliang on 16/10/21.
 */

public class MyAttentionListActivity extends MyBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myattentionlist);
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);
        setBarTitleTx("我的关注");
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
    public void onError(Call call, Exception e, int code, int id) {
        super.onError(call, e, code, id);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
