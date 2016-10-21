package com.atman.jixin.ui.im;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.atman.jixin.R;
import com.atman.jixin.model.response.QRScanCodeModel;
import com.atman.jixin.ui.base.MyBaseActivity;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by tangbingliang on 16/10/21.
 */

public class ShopIMActivity extends MyBaseActivity {

    private QRScanCodeModel mQRScanCodeModel = new QRScanCodeModel();
    private Context mContext = ShopIMActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopim);
    }

    public static Intent buildIntent (Context context, QRScanCodeModel mQRScanCodeModel) {
        Intent intent = new Intent(context, ShopIMActivity.class);
        Bundle b = new Bundle();
        b.putSerializable("bean", mQRScanCodeModel);
        intent.putExtras(b);
        return intent;
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);
        mQRScanCodeModel = (QRScanCodeModel) getIntent().getSerializableExtra("bean");
        if (mQRScanCodeModel!=null) {
            setBarTitleTx(mQRScanCodeModel.getBody().getStoreBean().getStoreName());
        }
        setBarRightIv(R.mipmap.shop_member_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast(">>>>");
            }
        });
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
