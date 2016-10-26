package com.atman.jixin.ui.im;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.atman.jixin.R;
import com.atman.jixin.ui.base.MyBaseActivity;
import com.atman.jixin.utils.SoundMeter;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Response;

/**
 * Created by tangbingliang on 16/10/26.
 */

public class RecordingActivity extends MyBaseActivity implements View.OnTouchListener {

    @Bind(R.id.recording_tip_tx)
    TextView recordingTipTx;
    @Bind(R.id.recording_iv)
    ImageView recordingIv;
    @Bind(R.id.imageView1)
    ImageView imageView1;
    @Bind(R.id.volume)
    ImageView volume;
    @Bind(R.id.voice_rcd_hint_rcding)
    LinearLayout voiceRcdHintRcding;
    @Bind(R.id.rcChat_popup)
    LinearLayout rcChatPopup;

    private Context mContext = RecordingActivity.this;

    private LinearLayout ll;
    private int flag = 1;
    private boolean isShosrt = false;
    private String voiceName;
    private long startVoiceT, endVoiceT;
    private Handler mHandler = new Handler();
    private Handler mTimeHandler = new Handler();

    private SoundMeter mSensor;
    private boolean isTouch = false;
    private boolean isCancel = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSwipeBackEnable(false);
        setContentView(R.layout.activity_recording);
        ButterKnife.bind(this);
    }

    public static Intent buildIntent(Context context, long id) {
        Intent intent = new Intent(context, RecordingActivity.class);
        intent.putExtra("id", id);
        return intent;
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);
        hideTitleBar();
        ll = getRootContentLl();
        ll.setBackgroundColor(getResources().getColor(R.color.color_00000000));

        mSensor = new SoundMeter();

        recordingIv.setOnTouchListener(this);
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

    public void backResuilt(String url) {
        Intent mIntent = new Intent();
        mIntent.putExtra("url", url);
        setResult(RESULT_OK, mIntent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(com.base.baselibs.R.anim.activity_bottom_in
                , com.base.baselibs.R.anim.activity_bottom_out);
    }

    @OnClick({R.id.recording_top_ll})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.recording_top_ll:
                if (!isTouch) {
                    finish();
                }
                break;
        }
    }

    private static final int POLL_INTERVAL = 50;
    private static final int TIME_INTERVAL = 1000;
    private static final int MAX_TIME = 60;

    private Runnable mSleepTask = new Runnable() {
        public void run() {
            stop();
        }
    };

    private Runnable mPollTask = new Runnable() {
        public void run() {
            double amp = mSensor.getAmplitude();
            updateDisplay(amp);
            mHandler.postDelayed(mPollTask, POLL_INTERVAL);
        }
    };

    private Runnable mTimeTask = new Runnable() {
        public void run() {
            endVoiceT = System.currentTimeMillis();
            int time = (int) ((endVoiceT - startVoiceT) / 1000);
            if (!isCancel) {
                recordingTipTx.setText("录音中.."+time+"s");
                if (time >= MAX_TIME) {
                    File file = recordEnd();
                    backResuilt(file.getPath());
                } else {
                    mTimeHandler.postDelayed(mTimeTask, TIME_INTERVAL);
                }
            }
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        File file = recordEnd();
        if (file.exists()) {
            file.delete();
        }
    }

    private void updateDisplay(double signalEMA) {

        switch ((int) signalEMA) {
            case 0:
            case 1:
                volume.setImageResource(R.mipmap.amp1);
                break;
            case 2:
            case 3:
                volume.setImageResource(R.mipmap.amp2);

                break;
            case 4:
            case 5:
                volume.setImageResource(R.mipmap.amp3);
                break;
            case 6:
            case 7:
                volume.setImageResource(R.mipmap.amp4);
                break;
            case 8:
            case 9:
                volume.setImageResource(R.mipmap.amp5);
                break;
            case 10:
            case 11:
                volume.setImageResource(R.mipmap.amp6);
                break;
            default:
                volume.setImageResource(R.mipmap.amp7);
                break;
        }
    }

    private void start(String name) {
        mSensor.start(name);
        mHandler.postDelayed(mPollTask, POLL_INTERVAL);
    }

    private void stop() {
        mHandler.removeCallbacks(mSleepTask);
        mHandler.removeCallbacks(mPollTask);
        mSensor.stop();
        volume.setImageResource(R.mipmap.amp1);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (!Environment.getExternalStorageDirectory().exists()) {
            showToast("SDCard不存在");
            return true;
        }

        int[] location = new int[2];
        recordingIv.getLocationInWindow(location); // 获取在当前窗口内的绝对坐标
        int recordingIv_Y = location[1];
        int recordingIv_X = location[0];

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (flag == 1 && !isTouch) {
                    rcChatPopup.setVisibility(View.VISIBLE);
                    voiceRcdHintRcding.setVisibility(View.GONE);
                    mHandler.postDelayed(new Runnable() {
                        public void run() {
                            if (!isShosrt) {
                                voiceRcdHintRcding.setVisibility(View.VISIBLE);
                            }
                        }
                    }, 0);
                    recordingTipTx.setText("录音中..0s");
                    recordingTipTx.setTextColor(getResources().getColor(R.color.color_ec5b4b));
                    mTimeHandler.postDelayed(mTimeTask, 0);
                    startVoiceT = System.currentTimeMillis();
                    voiceName = startVoiceT + ".amr";
                    start(voiceName);
                    flag = 2;
                    isTouch = true;
                    isCancel = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                if (flag == 2) {

                    File file = recordEnd();

                    if (event.getRawY() >= recordingIv_Y
                            && event.getRawY() <= recordingIv_Y + recordingIv.getHeight()
                            && event.getRawX() >= recordingIv_X
                            && event.getRawX() <= recordingIv_X + recordingIv.getWidth()) {
                        endVoiceT = System.currentTimeMillis();
                        int time = (int) ((endVoiceT - startVoiceT) / 1000);
                        if (time < 1) {
                            voiceRcdHintRcding.setVisibility(View.GONE);
                            showToast("时间太短");
                            return false;
                        } else {
                            backResuilt(file.getPath());
                        }
                    } else {
                        if (file.exists()) {
                            file.delete();
                        }
                    }
                }
                break;
        }
        return true;
    }

    private File recordEnd() {
        recordingTipTx.setText("按住说话");
        recordingTipTx.setTextColor(getResources().getColor(R.color.color_757575));
        isTouch = false;
        isCancel = true;
        rcChatPopup.setVisibility(View.GONE);
        flag = 1;
        stop();
        File file = new File(android.os.Environment.getExternalStorageDirectory()+"/"
                + voiceName);
        return file;
    }
}
