package com.atman.jixin.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.alan.codescanlibs.QrCodeActivity;
import com.atman.jixin.R;
import com.atman.jixin.model.response.QRScanCodeModel;
import com.atman.jixin.ui.base.MyBaseActivity;
import com.atman.jixin.ui.base.MyBaseApplication;
import com.atman.jixin.ui.im.ShopIMActivity;
import com.atman.jixin.ui.personal.PersonalActivity;
import com.atman.jixin.ui.scancode.QRScanCodeActivity;
import com.atman.jixin.utils.Common;
import com.base.baselibs.net.MyStringCallback;
import com.base.baselibs.util.LogUtils;
import com.base.baselibs.util.PreferenceUtil;
import com.base.baselibs.widget.ShapeImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tbl.okhttputils.OkHttpUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class MainActivity extends MyBaseActivity {

    @Bind(R.id.main_head_img)
    ShapeImageView mainHeadImg;
    @Bind(R.id.main_bottom_ll)
    LinearLayout mainBottomLl;

    private Context mContext = MainActivity.this;
    private String headImge = "";
    private String str = "";

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
        if (id == Common.NET_QR_CODE_ID) {
            QRScanCodeModel mQRScanCodeModel = mGson.fromJson(data, QRScanCodeModel.class);
            if (mQRScanCodeModel.getResult().equals("1")) {
                if (mQRScanCodeModel.getBody().getType() == 1) {
                    startActivity(ShopIMActivity.buildIntent(mContext, mQRScanCodeModel));
                } else {
                    startActivity(QRScanCodeActivity.buildIntent(mContext, str));
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(Common.NET_QR_CODE_ID);
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

    @Override
    public void onError(Call call, Exception e, int code, int id) {
        super.onError(call, e, code, id);
        if (id == Common.NET_QR_CODE_ID) {
            startActivity(QRScanCodeActivity.buildIntent(mContext, str));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == Common.TO_CODESCAN) {
            str = data.getStringExtra("ScanResult");
            Map<String, String> p = new HashMap<>();
            p.put("content", str);
            OkHttpUtils.postString().url(Common.Url_QRCode).content(mGson.toJson(p))
                    .headers(MyBaseApplication.getApplication().getHeaderSeting())
                    .addHeader("cookie",MyBaseApplication.getApplication().getCookie())
                    .mediaType(Common.JSON).id(Common.NET_QR_CODE_ID).tag(Common.NET_QR_CODE_ID)
                    .build().execute(new MyStringCallback(mContext, "", this, true));
        }
    }
}
