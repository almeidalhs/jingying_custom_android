package com.atman.jixin.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.alan.codescanlibs.QrCodeActivity;
import com.atman.jixin.R;
import com.atman.jixin.ui.base.MyBaseActivity;
import com.atman.jixin.ui.base.MyBaseApplication;
import com.atman.jixin.ui.personal.PersonalActivity;
import com.atman.jixin.utils.Common;
import com.base.baselibs.util.LogUtils;
import com.base.baselibs.util.PreferenceUtil;
import com.base.baselibs.widget.ShapeImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Response;

public class MainActivity extends MyBaseActivity {

    @Bind(R.id.main_head_img)
    ShapeImageView mainHeadImg;
    @Bind(R.id.main_bottom_ll)
    LinearLayout mainBottomLl;

    private Context mContext = MainActivity.this;
    private String headImge = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSwipeBackEnable(false);
    }

    public static Intent buildIntent(Context context, boolean isToWeb) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("isToWeb", isToWeb);
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
        headImge = PreferenceUtil.getPreferences(mContext, PreferenceUtil.PARM_USER_IMG);
        if (!headImge.isEmpty()) {
            ImageLoader.getInstance().displayImage(Common.ImageUrl+headImge, mainHeadImg
                    , MyBaseApplication.getApplication().optionsHead);
        }
    }

    @Override
    public void onStringResponse(String data, Response response, int id) {
        super.onStringResponse(data, response, id);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @OnClick({R.id.main_head_img, R.id.main_bottom_ll})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_head_img:
                startActivity(new Intent(mContext, PersonalActivity.class));
                break;
            case R.id.main_bottom_ll:
                startActivityForResult(new Intent(mContext, QrCodeActivity.class), Common.TO_CODESCAN);
                break;
        }
    }
}
