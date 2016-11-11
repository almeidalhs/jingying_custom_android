package com.atman.jixin.ui.im;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.atman.jixin.R;
import com.atman.jixin.ui.base.MyBaseActivity;
import com.base.baselibs.util.LogUtils;
import com.base.baselibs.widget.audiorecord.aac.AAC;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by tangbingliang on 16/10/26.
 */

public class RecordingActivity extends MyBaseActivity implements View.OnTouchListener, AAC.AACListener {

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
    private String path = Environment.getExternalStorageDirectory()+"/jiying/audio";
    private AAC aac;
    private int sampleRateInHz = 16000;
    private int mTime;
    private long startTime;
    private long endTime;
    private static final int MAX_TIME = 60;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //不显示程序的标题栏
        requestWindowFeature( Window.FEATURE_NO_TITLE );
        setContentView(R.layout.activity_recording);
        ButterKnife.bind(this);

        recordingIv.setOnTouchListener(this);
        recordingTipTx.setText("按住说话");
        recordingTipTx.setTextColor(getResources().getColor(R.color.color_757575));
    }

    public static Intent buildIntent(Context context, long id) {
        Intent intent = new Intent(context, RecordingActivity.class);
        intent.putExtra("id", id);
        return intent;
    }

    @Override
    protected void onResume() {
        super.onResume();
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
                if (aac==null || aac.getStatus()!= AAC.AACStatus.Type_Ing) {
                    finish();
                }
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void updateDisplay(double signalEMA) {
        int n = (int) signalEMA;
        if (n<=28) {
            volume.setImageResource(R.mipmap.amp1);
        } else if (n>28 && n<=34) {

            switch ((int) signalEMA) {
                case 29:
                case 30:
                    volume.setImageResource(R.mipmap.amp2);
                    break;
                case 31:
                    volume.setImageResource(R.mipmap.amp3);
                    break;
                case 32:
                    volume.setImageResource(R.mipmap.amp4);
                    break;
                case 33:
                    volume.setImageResource(R.mipmap.amp5);
                    break;
                case 34:
                    volume.setImageResource(R.mipmap.amp6);
                    break;
                default:
                    volume.setImageResource(R.mipmap.amp1);
                    break;
            }
        } else if (n>=35) {
            volume.setImageResource(R.mipmap.amp7);
        } else {
            volume.setImageResource(R.mipmap.amp1);
        }

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (!Environment.getExternalStorageDirectory().exists()) {
            showToast("SDCard不存在");
            return true;
        }
        File f = new File(path);
        if (!f.exists()) {
            f.mkdirs();
        }

        int[] location = new int[2];
        recordingIv.getLocationInWindow(location); // 获取在当前窗口内的绝对坐标
        int recordingIv_Y = location[1];
        int recordingIv_X = location[0];

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                LogUtils.e("ACTION_DOWN"+android.os.Build.MODEL);
                int time = (int) ((System.currentTimeMillis() - startTime) / 1000);
                if (time<1) {
                    return true;
                }
                recordingTipTx.setText("录音中..0s");
                recordingTipTx.setTextColor(getResources().getColor(R.color.color_ec5b4b));
                aac = new AAC(path + System.currentTimeMillis() +".aac", android.os.Build.MODEL, RecordingActivity.this);
                aac.sampleRateInHz(sampleRateInHz);
                aac.start();
                break;
            case MotionEvent.ACTION_UP:
                LogUtils.e("ACTION_UP");
                if (event.getRawY() >= recordingIv_Y
                        && event.getRawY() <= recordingIv_Y + recordingIv.getHeight()
                        && event.getRawX() >= recordingIv_X
                        && event.getRawX() <= recordingIv_X + recordingIv.getWidth()) {
                    aac.end();
                } else {
                    aac.cancel();
                }
                recordingTipTx.setText("按住说话");
                break;
            case MotionEvent.ACTION_CANCEL:
                LogUtils.e("ACTION_CANCEL");
                aac.cancel();
                break;
        }
        return true;
    }

    @Override
    public void onRecordStart() {
        startTime = System.currentTimeMillis();
        rcChatPopup.setVisibility(View.VISIBLE);
    }

    @Override
    public void onRecording(double volume) {
        updateDisplay(volume);
        int time = (int) ((System.currentTimeMillis() - startTime) / 1000);
        if (time>=MAX_TIME) {
            aac.end();
        } else {
            recordingTipTx.setText("录音中.."+time+"s");
        }
    }

    @Override
    public void onRecordEnd(String filePath) {
        endTime = System.currentTimeMillis();
        recordingTipTx.setText("按住说话");
        recordingTipTx.setTextColor(getResources().getColor(R.color.color_757575));
        rcChatPopup.setVisibility(View.GONE);
        mTime = (int) (endTime - startTime);
        if (mTime<1000) {
            showToast("录音时间太短");
            return;
        }
        File file = new File(filePath);
        if (file.exists()) {
            if (file.length()==0 || mTime/1000==0) {
                return;
            }
            Intent mIntent = new Intent();
            mIntent.putExtra("url", file.getPath());
            mIntent.putExtra("time", mTime/1000);
            setResult(RESULT_OK, mIntent);
            finish();
        } else {
            showToast("文件不存在");
        }
    }

    @Override
    public void onRecordCancel(String filePath) {
        rcChatPopup.setVisibility(View.GONE);
        recordingTipTx.setText("按住说话");
        recordingTipTx.setTextColor(getResources().getColor(R.color.color_757575));
        File f = new File(filePath);
        if (f.exists()) {
            f.delete();
        }
        showToast("取消录音");
    }

    @Override
    public void onRecordFaild(String str) {
        recordingTipTx.setText("按住说话");
        rcChatPopup.setVisibility(View.GONE);
        recordingTipTx.setTextColor(getResources().getColor(R.color.color_757575));
        showWraning(str);
    }

}
