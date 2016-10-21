package com.atman.jixin.ui.scancode;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.atman.jixin.R;
import com.atman.jixin.ui.base.MyBaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by tangbingliang on 16/10/21.
 */

public class QRScanCodeActivity extends MyBaseActivity {

    @Bind(R.id.qr_code_result_tx)
    TextView qrCodeResultTx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrscancode);
        ButterKnife.bind(this);
    }

    public static Intent buildIntent (Context context, String str) {
        Intent intent = new Intent(context, QRScanCodeActivity.class);
        intent.putExtra("str", str);
        return intent;
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);
        setBarTitleTx("扫描结果");
        qrCodeResultTx.setText(getIntent().getStringExtra("str"));
    }
}
