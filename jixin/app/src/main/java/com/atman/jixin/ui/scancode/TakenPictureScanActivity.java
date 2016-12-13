package com.atman.jixin.ui.scancode;

import android.content.Context;
import android.content.Intent;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.atman.jixin.R;
import com.atman.jixin.ui.base.MyBaseActivity;
import com.atman.jixin.utils.BitmapTools;
import com.base.baselibs.util.LogUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Response;

/**
 * Created by tangbingliang on 16/12/13.
 */

public class TakenPictureScanActivity extends MyBaseActivity {

    @Bind(R.id.takenpic_rl)
    RelativeLayout takenpicRl;
    @Bind(R.id.takenpic_bg_iv)
    ImageView takenpicBgIv;

    private Context mContext = TakenPictureScanActivity.this;
    private String picUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_takenpicturescan);
        ButterKnife.bind(this);
    }

    public static Intent buildIntent(Context context, String picUrl) {
        Intent intent = new Intent(context, TakenPictureScanActivity.class);
        intent.putExtra("picUrl", picUrl);
        return intent;
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);

        setBarTitleTx("扫描中...");

        picUrl = getIntent().getStringExtra("picUrl");

        try {
            picUrl = BitmapTools.revitionImage(mContext, Uri.parse("file:///" + picUrl)).getPath();
        } catch (IOException e) {
            e.printStackTrace();
        }

        LogUtils.e(">>>:"+readPictureDegree(picUrl));

        ImageLoader.getInstance().displayImage("file://" + picUrl, takenpicBgIv);
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

    private  int readPictureDegree(String path) {
        int degree  = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);

            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
                default:
                    degree = 0;
            }
        } catch (IOException e) {
            e.printStackTrace();
            LogUtils.e(">>>e:"+e.toString());
        }
        return degree;
    }
}
