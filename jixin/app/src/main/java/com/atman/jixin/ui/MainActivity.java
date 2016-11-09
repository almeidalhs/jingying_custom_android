package com.atman.jixin.ui;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alan.codescanlibs.QrCodeActivity;
import com.atman.jixin.R;
import com.atman.jixin.adapter.ChatSessionListAdapter;
import com.atman.jixin.model.MessageEvent;
import com.atman.jixin.model.bean.ChatListModel;
import com.atman.jixin.model.bean.ChatMessageModel;
import com.atman.jixin.model.greendao.gen.ChatListModelDao;
import com.atman.jixin.model.greendao.gen.ChatMessageModelDao;
import com.atman.jixin.model.iimp.ADChatTargetType;
import com.atman.jixin.model.iimp.ToAppType;
import com.atman.jixin.model.response.CheckVersionModel;
import com.atman.jixin.model.response.QRScanCodeModel;
import com.atman.jixin.service.SeedMessageService;
import com.atman.jixin.service.SeedPersonalMessageService;
import com.atman.jixin.ui.base.MyBaseActivity;
import com.atman.jixin.ui.base.MyBaseApplication;
import com.atman.jixin.ui.im.PersonalIMActivity;
import com.atman.jixin.ui.im.ShopIMActivity;
import com.atman.jixin.ui.personal.PersonalActivity;
import com.atman.jixin.ui.personal.PersonalSettingActivity;
import com.atman.jixin.ui.scancode.QRScanCodeActivity;
import com.atman.jixin.ui.shop.MemberCenterActivity;
import com.atman.jixin.utils.Common;
import com.atman.jixin.utils.MyTools;
import com.atman.jixin.utils.face.FaceConversionUtil;
import com.atman.jixin.widget.ResidentNotificationHelper;
import com.base.baselibs.net.MyStringCallback;
import com.base.baselibs.util.PreferenceUtil;
import com.base.baselibs.widget.PromptDialog;
import com.base.baselibs.widget.ShapeImageView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.extras.recyclerview.PullToRefreshRecyclerView;
import com.igexin.sdk.PushManager;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tbl.okhttputils.OkHttpUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class MainActivity extends MyBaseActivity implements ChatSessionListAdapter.IonSlidingViewClickListener {

    @Bind(R.id.main_head_img)
    ShapeImageView mainHeadImg;
    @Bind(R.id.main_bottom_ll)
    LinearLayout mainBottomLl;
    @Bind(R.id.message_empty_tv)
    TextView messageEmptyTv;
    @Bind(R.id.pull_refresh_recycler)
    PullToRefreshRecyclerView pullRefreshRecycler;

    private Context mContext = MainActivity.this;
    private String headImge = "";
    private String str = "";
    private ChatListModelDao mChatListModelDao;
    private ChatMessageModelDao mChatMessageModelDao;
    private List<ChatListModel> mChatList;
    private ChatSessionListAdapter mAdapter;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSwipeBackEnable(false);

        if (isLogin()) {
            PushManager.getInstance().initialize(this.getApplicationContext());
            String CliendId = PushManager.getInstance().getClientid(mContext);

            OkHttpUtils.postString().url(Common.Url_Update_ClienId + CliendId).content("{}")
                    .headers(MyBaseApplication.getApplication().getHeaderSeting())
                    .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                    .mediaType(Common.JSON).id(Common.NET_UP_GETTUI_ID).tag(Common.NET_UP_GETTUI_ID)
                    .build().execute(new MyStringCallback(mContext, "链接中...", this, true, false));
            EventBus.getDefault().register(this);
        }
    }

    public static Intent buildIntent(Context context, boolean isToWeb) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("isToWeb", isToWeb);
        return intent;
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);

        int noticeId = getIntent().getIntExtra(ResidentNotificationHelper.NOTICE_ID_KEY, -1);
        ResidentNotificationHelper.clearNotification(mContext, noticeId);

        messageEmptyTv.setText(Html.fromHtml(
                "您还没有聊天记录<p>点击下方[<font color=\"#16c5ee\">一扫即应</font>]开始扫描二维码吧"));

        hideTitleBar();

        initListView();
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        super.onPullDownToRefresh(refreshView);
        onLoad(PullToRefreshBase.Mode.PULL_FROM_START, pullRefreshRecycler);
        startActivityForResult(new Intent(mContext, QrCodeActivity.class), Common.TO_CODESCAN);
    }

    private void initListView() {

        initRefreshView(PullToRefreshBase.Mode.PULL_FROM_START, pullRefreshRecycler);
        // 下拉刷新时的提示文本设置
        pullRefreshRecycler.getLoadingLayoutProxy(true, false).setPullLabel("下拉进行扫一扫");
        pullRefreshRecycler.getLoadingLayoutProxy(true, false).setRefreshingLabel("下拉进行扫一扫");
        pullRefreshRecycler.getLoadingLayoutProxy(true, false).setReleaseLabel("松开来扫一扫");

        mAdapter = new ChatSessionListAdapter(mContext, getmWidth(), this);

        mRecyclerView = pullRefreshRecycler.getRefreshableView();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));//这里用线性显示 类似于listview
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void doInitBaseHttp() {
        super.doInitBaseHttp();
        OkHttpUtils.get().url(Common.Url_Get_Version+"?version="+MyBaseApplication.VERSION)
                .tag(Common.NET_GET_VERSION_ID).id(Common.NET_GET_VERSION_ID)
                .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                .build().execute(new MyStringCallback(mContext, "", MainActivity.this, false));
    }

    @Override
    protected void onResume() {
        super.onResume();
        headImge = PreferenceUtil.getPreferences(mContext, PreferenceUtil.PARM_USER_IMG);
        if (!headImge.isEmpty()) {
            ImageLoader.getInstance().displayImage(Common.ImageUrl + headImge, mainHeadImg
                    , MyBaseApplication.getApplication().optionsHead);
        }

        mChatListModelDao = MyBaseApplication.getApplication().getDaoSession().getChatListModelDao();
        mChatMessageModelDao = MyBaseApplication.getApplication().getDaoSession().getChatMessageModelDao();

        setUnreadMessageNum();
    }

    //在注册了的Activity中,声明处理事件的方法
    @Subscribe(threadMode = ThreadMode.MAIN) //第2步:注册一个在后台线程执行的方法,用于接收事件
    public void onUserEvent(MessageEvent event) {//参数必须是ClassEvent类型, 否则不会调用此方法
        setUnreadMessageNum();
    }

    private void setUnreadMessageNum() {
        if (!isLogin()) {
            return;
        }
        if (mChatList != null) {
            mChatList.clear();
        }
        if (mAdapter != null) {
            mAdapter.clearData();
        }
        mChatList = mChatListModelDao.queryBuilder().where(ChatListModelDao.Properties
                .LoginId.eq(MyBaseApplication.USERINFOR.getBody().getAtmanUserId()))
                .orderDesc(ChatListModelDao.Properties.SendTime).build().list();
        if (mChatList == null || mChatList.size() == 0) {
            messageEmptyTv.setVisibility(View.VISIBLE);
            pullRefreshRecycler.setVisibility(View.GONE);
        } else {
            messageEmptyTv.setVisibility(View.GONE);
            pullRefreshRecycler.setVisibility(View.VISIBLE);
            mAdapter.addData(mChatList);
        }
    }

    @Override
    public void onStringResponse(String data, Response response, int id) {
        super.onStringResponse(data, response, id);
        if (id == Common.NET_QR_CODE_ID) {
            QRScanCodeModel mQRScanCodeModel = mGson.fromJson(data, QRScanCodeModel.class);
            if (mQRScanCodeModel.getResult().equals("1")) {
                startActivity(ShopIMActivity.buildIntent(mContext, mQRScanCodeModel, false));
            }
        } else if (id == Common.NET_UP_GETTUI_ID) {
        } else if (id == Common.NET_GET_VERSION_ID) {
            final CheckVersionModel mCheckVersionModel = mGson.fromJson(data, CheckVersionModel.class);
            if (mCheckVersionModel.getResult().equals("1") && mCheckVersionModel.getBody()!=null) {
                PromptDialog.Builder builder = new PromptDialog.Builder(mContext);
                builder.setMessage(mCheckVersionModel.getBody().getWarn());
                builder.setPositiveButton("升级", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        final File updateFile = createFile(MyBaseApplication.VERSION);
                        if (updateFile != null) {
                            /**调用系统浏览器在页面中下载*/
                            Uri uri = Uri.parse(mCheckVersionModel.getBody().getUrl());
                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            startActivity(intent);
                        } else {
                            showToast("SD卡路径错误，无法下载");
                        }
                    }
                });
                if (mCheckVersionModel.getBody().getForce().equals("0")) {
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                } else {
                    builder.setCancelable(false);
                }
                builder.show();
            } else {
                showToast("已是最新版本");
            }
        }
    }

    private void showWranning(final String content, final int to) {
        PromptDialog.Builder builder = new PromptDialog.Builder(MainActivity.this);
        builder.setTitle("提示");
        builder.setMessage("可能存在风险,是否打开此链接?\n"+content);
        builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("打开", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (to == ToAppType.ToAppType_WEIXIN) {
//                    Intent intent = new Intent();
//                    ComponentName cmp = new ComponentName(" com.tencent.mm ","com.tencent.mm.ui.LauncherUI");
//                    intent.setAction(Intent.ACTION_MAIN);
//                    intent.addCategory(Intent.CATEGORY_LAUNCHER);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    intent.setComponent(cmp);
                    Intent intent = getPackageManager().getLaunchIntentForPackage("com.tencent.mm");
                    startActivity(intent);
                } else if (to == ToAppType.ToAppType_QQ) {
                    Intent intent = getPackageManager().getLaunchIntentForPackage("com.tencent.mobileqq");
                    startActivity(intent);
                } else {
                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.VIEW");
                    Uri content_url = Uri.parse(content);
                    intent.setData(content_url);
                    startActivity(intent);
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Intent stopIntent = new Intent(this, SeedMessageService.class);
        stopService(stopIntent);
        Intent stopPersonalIntent = new Intent(this, SeedPersonalMessageService.class);
        stopService(stopPersonalIntent);

        OkHttpUtils.getInstance().cancelTag(Common.NET_QR_CODE_ID);
        OkHttpUtils.getInstance().cancelTag(Common.NET_UP_GETTUI_ID);
        EventBus.getDefault().unregister(this);
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
            int to = ToAppType.ToAppType_WEB;
            if (str.startsWith("http://weixin.qq.com")) {
                if (!MyTools.isPkgInstalled(mContext, "com.tencent.mm")) {
                    showToast("尚未安装微信客户端");
                    return;
                }
                to = ToAppType.ToAppType_WEIXIN;
            } else if (str.startsWith("http://qm.qq.com")) {
                if (!MyTools.isPkgInstalled(mContext, "com.tencent.mobileqq")) {
                    showToast("尚未安装QQ客户端");
                    return;
                }
                to = ToAppType.ToAppType_QQ;
            }
            showWranning(str, to);
        } else if (id == Common.NET_UP_GETTUI_ID) {
            clearData();
            showWraning("链接失败,请重新登录!");
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
                    .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                    .mediaType(Common.JSON).id(Common.NET_QR_CODE_ID).tag(Common.NET_QR_CODE_ID)
                    .build().execute(new MyStringCallback(mContext, "", this, true));
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        ChatListModel mChatListModel = mChatListModelDao.queryBuilder()
                .where(ChatListModelDao.Properties.TargetId.eq(mAdapter.getItem(position).getTargetId())
                        , ChatListModelDao.Properties.LoginId.eq(MyBaseApplication.USERINFOR.getBody().getAtmanUserId())).build().unique();
        if (mChatListModel != null) {
            ResidentNotificationHelper.clearNotification(mContext, (int) mChatListModel.getChatId());
            mChatListModel.setUnreadNum(0);
            mChatListModelDao.update(mChatListModel);
            mAdapter.clearUnreadNum(position);
            if (mAdapter.getItem(position).getTargetType() == ADChatTargetType.ADChatTargetType_Shop) {
                startActivity(ShopIMActivity.buildIntent(mContext, mAdapter.getItem(position).getTargetId()
                        , mAdapter.getItem(position).getTargetName(), mAdapter.getItem(position).getTargetAvatar(), true));
            } else {
                startActivity(PersonalIMActivity.buildIntent(mContext, mAdapter.getItem(position).getTargetId()
                        , mAdapter.getItem(position).getTargetName(), mAdapter.getItem(position).getTargetAvatar()));
            }
        }
    }

    @Override
    public void onDeleteBtnCilck(View view, final int position) {
        switch (view.getId()) {
            case R.id.member_ll:
                startActivity(MemberCenterActivity.buildIntent(mContext, mAdapter.getItem(position).getTargetId()));
                break;
            case R.id.delete_ll:
                PromptDialog.Builder builder = new PromptDialog.Builder(mContext);
                builder.setMessage("您确定要将该消息删除吗？");
                builder.setPositiveButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        List<ChatMessageModel> mImMessageDelete = mChatMessageModelDao.queryBuilder().where(ChatMessageModelDao.Properties.TargetId.eq(mAdapter.getItem(position).getTargetId())
                                , ChatMessageModelDao.Properties.LoginId.eq(MyBaseApplication.USERINFOR.getBody().getAtmanUserId())).build().list();
                        for (ChatMessageModel imMessageDelete : mImMessageDelete) {
                            mChatMessageModelDao.delete(imMessageDelete);
                        }

                        ChatListModel mDelete = mChatListModelDao.queryBuilder()
                                .where(ChatListModelDao.Properties.TargetId.eq(mAdapter.getItem(position).getTargetId())
                                        , ChatListModelDao.Properties.LoginId.eq(MyBaseApplication.USERINFOR.getBody().getAtmanUserId())).build().unique();
                        if (mDelete != null) {
                            mChatListModelDao.delete(mDelete);
                            mAdapter.removeData(position);
                        }
                        if (mAdapter.getItemCount() == 0) {
                            messageEmptyTv.setVisibility(View.VISIBLE);
                            pullRefreshRecycler.setVisibility(View.GONE);
                        }
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
                break;
        }
    }
}
