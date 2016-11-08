package com.atman.jixin.ui.im.chatui;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.atman.jixin.R;
import com.atman.jixin.adapter.CompanyIntroductionAdapter;
import com.atman.jixin.model.iimp.CommentType;
import com.atman.jixin.model.response.GetCompanyIntrodutionModel;
import com.atman.jixin.ui.PictureBrowsingActivity;
import com.atman.jixin.ui.base.MyBaseActivity;
import com.atman.jixin.ui.base.MyBaseApplication;
import com.atman.jixin.utils.Common;
import com.atman.jixin.utils.FileUtils;
import com.atman.jixin.utils.MyTools;
import com.atman.jixin.widget.downfile.DownloadAudioFile;
import com.base.baselibs.iimp.AdapterInterface;
import com.base.baselibs.net.MyStringCallback;
import com.base.baselibs.util.LogUtils;
import com.tbl.okhttputils.OkHttpUtils;
import com.universalvideoview.UniversalMediaController;
import com.universalvideoview.UniversalVideoView;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Response;

/**
 * Created by tangbingliang on 16/11/1.
 */

public class CompanyIntroductionActivity extends MyBaseActivity implements AdapterInterface
        , CompanyIntroductionAdapter.AdapterAnimInter, DownloadAudioFile.onDownInterface {

    @Bind(R.id.videoView)
    UniversalVideoView videoView;
    @Bind(R.id.media_controller)
    UniversalMediaController mediaController;
    @Bind(R.id.video_layout)
    FrameLayout videoLayout;
    @Bind(R.id.company_listView)
    ListView companyListView;
    @Bind(R.id.company_video_close_iv)
    TextView companyVideoCloseIv;

    private Context mContext = CompanyIntroductionActivity.this;

    private String title;
    private String videoUrl;
    private long id;

    private GetCompanyIntrodutionModel mGetCompanyIntrodutionModel;
    private CompanyIntroductionAdapter mAdapter;

    private int positionAudio;
    private MediaPlayer mMediaPlayer = new MediaPlayer();
    private AnimationDrawable mAnimationDrawable;
    private int mSeekPosition;
    private int cachedHeight;
    private boolean isFullscreen;
    private static final String SEEK_POSITION_KEY = "SEEK_POSITION_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_companyintroduction);
        ButterKnife.bind(this);
    }

    public static Intent buildIntent(Context context, String title, long id) {
        Intent intent = new Intent(context, CompanyIntroductionActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("id", id);
        return intent;
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);

        title = getIntent().getStringExtra("title");
        id = getIntent().getLongExtra("id", -1);

        LogUtils.e("id:" + id + ",title:" + title);

        setBarTitleTx(title);
        setBarRightTx("评论").setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(StoreCommentActivity.buildIntent(mContext, id, CommentType.CommentType_STORE));
            }
        });

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(getmWidth(),
                getmWidth() * 9 / 16);

        mAdapter = new CompanyIntroductionAdapter(mContext, this, this);
        companyListView.setAdapter(mAdapter);
    }

    private void initVideo() {
        videoView.setMediaController(mediaController);
        setVideoAreaSize();
        videoView.setVideoViewCallback(callback);
    }

    private UniversalVideoView.VideoViewCallback callback = new UniversalVideoView.VideoViewCallback() {
        @Override
        public void onScaleChange(boolean b) {
            isFullscreen = b;
            if (isFullscreen) {
                ViewGroup.LayoutParams layoutParams = videoLayout.getLayoutParams();
                layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                layoutParams.height = getmHight();
                videoLayout.setLayoutParams(layoutParams);
                //设置全屏时,无关的View消失,以便为视频控件和控制器控件留出最大化的位置
                companyListView.setVisibility(View.GONE);
                companyVideoCloseIv.setVisibility(View.GONE);
                hideTitleBar();
            } else {
                ViewGroup.LayoutParams layoutParams = videoLayout.getLayoutParams();
                layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                layoutParams.height = cachedHeight;
                videoLayout.setLayoutParams(layoutParams);
                companyListView.setVisibility(View.VISIBLE);
                companyVideoCloseIv.setVisibility(View.VISIBLE);
                showTitleBar();
            }
        }

        @Override
        public void onPause(MediaPlayer mediaPlayer) { // 视频暂停
            LogUtils.e("onPause UniversalVideoView callback");
        }

        @Override
        public void onStart(MediaPlayer mediaPlayer) { // 视频开始播放或恢复播放
            LogUtils.e("onStart UniversalVideoView callback");
        }

        @Override
        public void onBufferingStart(MediaPlayer mediaPlayer) {// 视频开始缓冲
            LogUtils.e("onBufferingStart UniversalVideoView callback");
        }

        @Override
        public void onBufferingEnd(MediaPlayer mediaPlayer) {// 视频结束缓冲
            LogUtils.e("onBufferingEnd UniversalVideoView callback");
        }

    };

    /**
     * 置视频区域大小
     */
    private void setVideoAreaSize() {
        LogUtils.e("videoUrl:" + videoUrl);
        videoLayout.post(new Runnable() {
            @Override
            public void run() {
                int width = videoLayout.getWidth();
                cachedHeight = (int) (width * 405f / 720f);
                ViewGroup.LayoutParams videoLayoutParams = videoLayout.getLayoutParams();
                videoLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                videoLayoutParams.height = cachedHeight;
                videoLayout.setLayoutParams(videoLayoutParams);
                videoView.setVideoPath(videoUrl);
                videoView.requestFocus();
            }
        });
    }

    @Override
    public void doInitBaseHttp() {
        super.doInitBaseHttp();
        OkHttpUtils.get().url(Common.Url_Get_Store_Introduction + id)
                .headers(MyBaseApplication.getApplication().getHeaderSeting())
                .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                .tag(Common.NET_GET_STORE_INRRODUCTION_ID).id(Common.NET_GET_STORE_INRRODUCTION_ID).build()
                .execute(new MyStringCallback(mContext, "", this, true));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onStringResponse(String data, Response response, int id) {
        super.onStringResponse(data, response, id);
        if (id == Common.NET_GET_STORE_INRRODUCTION_ID) {
            mGetCompanyIntrodutionModel = mGson.fromJson(data, GetCompanyIntrodutionModel.class);

            mAdapter.addBody(mGetCompanyIntrodutionModel.getBody().getFileList());
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mMediaPlayer!=null && mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
            if (mAnimationDrawable!=null) {
                mAnimationDrawable.stop();
                mAnimationDrawable.selectDrawable(0);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(Common.NET_GET_STORE_INRRODUCTION_ID);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        LogUtils.e("onSaveInstanceState Position=" + videoView.getCurrentPosition());
        outState.putInt(SEEK_POSITION_KEY, mSeekPosition);
    }

    @Override
    protected void onRestoreInstanceState(Bundle outState) {
        super.onRestoreInstanceState(outState);
        mSeekPosition = outState.getInt(SEEK_POSITION_KEY);
        LogUtils.e("onRestoreInstanceState Position=" + mSeekPosition);
    }

    @Override
    public void onBackPressed() {
        if (this.isFullscreen) {
            videoView.setFullscreen(false);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        positionAudio = position;
        switch (view.getId()) {
            case R.id.item_company_video_start_iv:
                videoUrl = mAdapter.getItem(position).getFileUrl();
                if (!videoUrl.startsWith("http")) {
                    videoUrl = Common.ImageUrl + videoUrl;
                }
                videoLayout.setVisibility(View.VISIBLE);
                initVideo();
                videoView.start();
                break;
            case R.id.item_company_pictrue_iv:
                String imagePath = "";
                int n = 0;
                for (int i=0,j=0;i<mAdapter.getCount();i++) {
                    if (mAdapter.getItem(i).getType() == 1) {
                        if (!imagePath.equals("")) {
                            imagePath += ",";
                        }
                        imagePath += MyTools.getHttpUrl(mAdapter.getItem(i).getFileUrl());
                        if (i == position) {
                            n = j;
                        }
                        j++;
                    }
                }

                Intent intent = new Intent();
                intent.putExtra("image", imagePath);
                intent.putExtra("num", n);
                intent.setClass(mContext, PictureBrowsingActivity.class);
                startActivity(intent);
                break;
        }
    }

    @OnClick({R.id.company_video_close_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.company_video_close_iv:
                videoLayout.setVisibility(View.GONE);
                videoView.closePlayer();
                break;
        }
    }

    @Override
    public void onItemAudio(View v, int position, AnimationDrawable animationDrawable, ImageView imageView) {
        positionAudio = position;
        switch (v.getId()) {
            case R.id.item_company_audio_start_iv:
                playAudio(position, animationDrawable, true);
                break;
        }
    }

    private void playAudio(final int position, AnimationDrawable animationDrawable, boolean b) {
        if (mAdapter.getItem(position).getFileUrl()!=null) {
            String path = FileUtils.SDPATH_AUDIO
                    + DownloadAudioFile.getFileName(mAdapter.getItem(position).getFileUrl()+".aac");
            LogUtils.e("path:"+path);
            if ((new File(path).exists())) {
                cancelLoading();
                try {
                    if (mMediaPlayer.isPlaying()) {
//                        mMediaPlayer.stop();
                        mMediaPlayer.pause();
                        mAdapter.updataView(position, companyListView, 0, false, String.valueOf(displayTime*1000-n));
                        if (mAnimationDrawable!=null) {
                            mAnimationDrawable.stop();
                            mAnimationDrawable.selectDrawable(0);
                        }
                    } else {
                        mAnimationDrawable = animationDrawable;
                        handler.postDelayed(runnable, 0);
                        mMediaPlayer.reset();
                        mMediaPlayer.setDataSource(path);
                        mMediaPlayer.prepare();
                        mMediaPlayer.start();
                        if ((displayTime*1000-n)>0) {
                            LogUtils.e("(displayTime*1000-n):"+(displayTime*1000-n));
                            mMediaPlayer.seekTo(n);
                        }
                        mAnimationDrawable.start();
                        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            public void onCompletion(MediaPlayer mp) {
                                if (!mp.isPlaying()) {
                                    mAnimationDrawable.stop();
                                    mAnimationDrawable.selectDrawable(0);
                                    n = 0;
                                    mAdapter.updataView(position, companyListView, 0, false, mAdapter.getItem(position).getLength());
                                }
                            }
                        });
                    }

                } catch (Exception e) {
                    showToast("播放失败");
                    e.printStackTrace();
                }
            } else {
                showLoading("加载中...", false);
                String downUrl = Common.ImageUrl + mAdapter.getItem(position).getFileUrl();
                LogUtils.e("downUrl:"+downUrl);
                if (b) {
                    new DownloadAudioFile(CompanyIntroductionActivity.this, this).execute(downUrl);
                }
            }
        }
    }

    @Override
    public void finish(String path) {
        playAudio(positionAudio, mAnimationDrawable, false);
    }

    @Override
    public void error() {
        showToast("播放失败");
    }

    private int n = 0;
    private int displayTime = 0;
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (mMediaPlayer!=null && mMediaPlayer.isPlaying()) {
                if (displayTime==0) {
                    displayTime = Integer.parseInt(mAdapter.getItem(positionAudio).getLength());
                }
                n = mMediaPlayer.getCurrentPosition();
                LogUtils.e("mMediaPlayer.getCurrentPosition():"+mMediaPlayer.getCurrentPosition());
                mAdapter.updataView(positionAudio, companyListView, 0, true, String.valueOf(displayTime*1000-n));
                handler.postDelayed(runnable, 1000);
            }
        }
    };
}
