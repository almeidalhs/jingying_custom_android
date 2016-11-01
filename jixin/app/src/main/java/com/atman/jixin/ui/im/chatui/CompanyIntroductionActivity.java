package com.atman.jixin.ui.im.chatui;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.atman.jixin.R;
import com.atman.jixin.adapter.CompanyIntroductionAdapter;
import com.atman.jixin.model.iimp.CommentType;
import com.atman.jixin.model.response.GetCompanyIntrodutionModel;
import com.atman.jixin.ui.base.MyBaseActivity;
import com.atman.jixin.ui.base.MyBaseApplication;
import com.atman.jixin.utils.Common;
import com.base.baselibs.iimp.AdapterInterface;
import com.base.baselibs.net.MyStringCallback;
import com.base.baselibs.util.LogUtils;
import com.tbl.okhttputils.OkHttpUtils;
import com.universalvideoview.UniversalMediaController;
import com.universalvideoview.UniversalVideoView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Response;

/**
 * Created by tangbingliang on 16/11/1.
 */

public class CompanyIntroductionActivity extends MyBaseActivity implements AdapterInterface {

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
    private String imgUrl;
    private String videoUrl;
    private long id;

    private GetCompanyIntrodutionModel mGetCompanyIntrodutionModel;
    private CompanyIntroductionAdapter mAdapter;

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

    public static Intent buildIntent(Context context, String title, long id, String imgUrl) {
        Intent intent = new Intent(context, CompanyIntroductionActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("id", id);
        intent.putExtra("imgUrl", imgUrl);
        return intent;
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);

        title = getIntent().getStringExtra("title");
        imgUrl = getIntent().getStringExtra("imgUrl");
        id = getIntent().getLongExtra("id", -1);

        LogUtils.e("id:" + id + ",title:" + title + ",imgUrl:" + imgUrl);

        setBarTitleTx(title);
        setBarRightTx("评论").setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(StoreCommentActivity.buildIntent(mContext, id, CommentType.CommentType_STORE));
            }
        });

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(getmWidth(),
                getmWidth() * 9 / 16);

        mAdapter = new CompanyIntroductionAdapter(mContext, this);
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
}
