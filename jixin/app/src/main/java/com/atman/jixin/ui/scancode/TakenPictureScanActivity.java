package com.atman.jixin.ui.scancode;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.alan.codescanlibs.view.ScanInformationView;
import com.alan.codescanlibs.view.ScanPointView;
import com.atman.jixin.R;
import com.atman.jixin.ui.base.MyBaseActivity;
import com.atman.jixin.utils.BitmapTools;
import com.base.baselibs.util.LogUtils;
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

//        picUrl = "/storage/emulated/0/jiying/image/1481682222586.jpg";
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
                            mViewAnimationUtils.stopMove(takenpicBfFl.getWidth() / 2, takenpicBfFl.getHeight() / 2
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
        for (int i = 0; i < 4; i++) {
            ScanPointView pointView = new ScanPointView(mContext);
            takenpicBfFl.addView(pointView);
        }
        for (int i = 1; i < takenpicBfFl.getChildCount(); i++) {
            mViewAnimationUtils.stopMove(takenpicBfFl.getWidth() / 2, takenpicBfFl.getHeight() / 2
                    , takenpicBfFl.getChildAt(i));
        }
        new Handler().postDelayed(new Runnable() {
            public void run() {
                for (int i = 1; i < takenpicBfFl.getChildCount(); i++) {
                    mViewAnimationUtils.pointMoveXY(takenpicBfFl.getWidth(), takenpicBfFl.getHeight()
                            , takenpicBfFl.getChildAt(i));
                }
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        takenpicBfFl.getChildAt(0).setVisibility(View.INVISIBLE);
                        setBarTitleTx("扫描完成");

                        if (takenpicBfFl.getChildCount()>=2) {
                            for (int i=1;i<=4;i++) {
                                showImformationView(takenpicBfFl.getChildAt(i),"第"+i+"个");
                            }
                        }
                    }
                }, 1000);
            }
        }, 500);
    }

    private void showImformationView(View pointView, final String name) {
        LogUtils.e("pointView.getX():"+pointView.getX()+",pointView.getY():"+pointView.getY());

        ScanInformationView inforView = null;
        if (pointView.getX()<=takenpicBfFl.getWidth() / 2) {//点view在左半屏幕
            inforView = new ScanInformationView(mContext, true, name);
        } else {//点view在右半屏幕
            inforView = new ScanInformationView(mContext, false, name);
        }
        inforView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast(name);
            }
        });
        takenpicBfFl.addView(inforView);

        int deviationY = inforView.getViewHeight()/2 - pointView.getHeight()/2;
        int mDistance = 0;
        if (pointView.getY()-deviationY<0) {//超出上边界
            mDistance = (int) Math.abs(pointView.getY()-deviationY);
        } else if (takenpicBfFl.getHeight()-(pointView.getY()+pointView.getHeight()/2)<inforView.getViewHeight()/2) {//超出下边界
            mDistance = -(int) (inforView.getViewHeight()/2-(takenpicBfFl.getHeight()-(pointView.getY()+pointView.getHeight()/2)));
        }
        deviationY -= mDistance;

        inforView.setmDistanceY(mDistance);

        int deviationX = 0;
        if (inforView.isLeftImg()) {//点view在左半屏幕
            deviationX = pointView.getWidth()/2;
        } else {//点view在右半屏幕
            deviationX = (-1)*(inforView.getViewWidth()+pointView.getWidth());
        }

        mViewAnimationUtils.addInformationView((int) pointView.getX()+deviationX, (int) pointView.getY()-deviationY
                , inforView);
    }
}
