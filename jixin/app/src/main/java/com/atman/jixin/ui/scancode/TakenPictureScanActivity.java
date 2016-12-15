package com.atman.jixin.ui.scancode;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.alan.codescanlibs.utils.ViewAnimationUtils;
import com.alan.codescanlibs.view.ScanEyesFindView;
import com.alan.codescanlibs.view.ScanPointView;
import com.atman.jixin.R;
import com.atman.jixin.ui.base.MyBaseActivity;
import com.atman.jixin.utils.BitmapTools;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Response;

/**
 * Created by tangbingliang on 16/12/13.
 */

public class TakenPictureScanActivity extends MyBaseActivity implements ViewAnimationUtils.viewAnimInterface {

    @Bind(R.id.takenpic_rl)
    RelativeLayout takenpicRl;
    @Bind(R.id.takenpic_bg_iv)
    ImageView takenpicBgIv;
    @Bind(R.id.takenpic_bf_fl)
    FrameLayout takenpicBfFl;

    private Context mContext = TakenPictureScanActivity.this;
    private String picUrl;
    private ViewAnimationUtils mViewAnimationUtils;

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

        picUrl = "/storage/emulated/0/jiying/image/1481682222586.jpg";
        mViewAnimationUtils = new ViewAnimationUtils(this);

        ImageLoader.getInstance().displayImage("file://" + picUrl, takenpicBgIv, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {

            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                initSEV();
            }

            @Override
            public void onLoadingCancelled(String s, View view) {

            }
        });
    }

    private void initSEV() {
        ScanEyesFindView eyesView = new ScanEyesFindView(mContext);
        ScanPointView pointView = new ScanPointView(mContext);

        takenpicBfFl.removeAllViews();
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT
                , FrameLayout.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.CENTER;
        takenpicBfFl.addView(eyesView, lp);

        takenpicBfFl.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        break;
                    case MotionEvent.ACTION_UP:
                        if (mViewAnimationUtils.isRandomReStart()) {
                            mViewAnimationUtils.stopMove(takenpicBfFl.getWidth()/2, takenpicBfFl.getHeight()/2
                                    , takenpicBfFl.getChildAt(0));
                        }
                        break;
                }
                return true;
            }
        });

        mViewAnimationUtils.randomMoveXY(takenpicBfFl.getWidth(), takenpicBfFl.getHeight()
                , takenpicBfFl.getChildAt(0));
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

    @Override
    public void moveFinsh() {
        for (int i=0;i<4;i++) {
            ScanPointView pointView = new ScanPointView(mContext);
            takenpicBfFl.addView(pointView);
        }
        for (int i=1;i<takenpicBfFl.getChildCount();i++) {
            mViewAnimationUtils.stopMove(takenpicBfFl.getWidth()/2, takenpicBfFl.getHeight()/2
                    , takenpicBfFl.getChildAt(i));
        }
        new Handler().postDelayed(new Runnable(){
            public void run() {
                for (int i=1;i<takenpicBfFl.getChildCount();i++) {
                    mViewAnimationUtils.pointMoveXY(takenpicBfFl.getWidth(), takenpicBfFl.getHeight()
                            , takenpicBfFl.getChildAt(i));
                }
                new Handler().postDelayed(new Runnable(){
                    public void run() {
                        takenpicBfFl.getChildAt(0).setVisibility(View.INVISIBLE);
                        setBarTitleTx("扫描完成");
                    }
                },1000);
            }
        }, 500);
    }
}
