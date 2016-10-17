package com.atman.jixin.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.atman.jixin.R;
import com.atman.jixin.ui.base.MyBaseActivity;

public class MainActivity extends MyBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSwipeBackEnable(false);
    }

    public static Intent buildIntent(Context context, boolean isToWeb) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("isToWeb", isToWeb);
        return intent;
    }
}
