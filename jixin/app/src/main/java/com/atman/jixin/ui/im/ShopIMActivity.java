package com.atman.jixin.ui.im;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.atman.jixin.R;
import com.atman.jixin.adapter.ChatServiceAdapter;
import com.atman.jixin.adapter.P2PChatAdapter;
import com.atman.jixin.model.MessageEvent;
import com.atman.jixin.model.bean.ChatListModel;
import com.atman.jixin.model.bean.ChatMessageModel;
import com.atman.jixin.model.bean.LocationNumberModel;
import com.atman.jixin.model.greendao.gen.ChatListModelDao;
import com.atman.jixin.model.greendao.gen.ChatMessageModelDao;
import com.atman.jixin.model.greendao.gen.LocationNumberModelDao;
import com.atman.jixin.model.iimp.ADChatTargetType;
import com.atman.jixin.model.iimp.ADChatType;
import com.atman.jixin.model.iimp.EventActionType;
import com.atman.jixin.model.response.GetChatServiceModel;
import com.atman.jixin.model.response.GetMessageModel;
import com.atman.jixin.model.response.MessageModel;
import com.atman.jixin.model.response.QRScanCodeModel;
import com.atman.jixin.model.updateChatMessageServiceEvent;
import com.atman.jixin.service.SeedMessageService;
import com.atman.jixin.ui.PictureBrowsingActivity;
import com.atman.jixin.ui.base.MyBaseActivity;
import com.atman.jixin.ui.base.MyBaseApplication;
import com.atman.jixin.ui.im.chatui.AnnualMeetingActivity;
import com.atman.jixin.ui.im.chatui.CompanyIntroductionActivity;
import com.atman.jixin.ui.im.chatui.GetCouponActivity;
import com.atman.jixin.ui.im.chatui.MenuPreviewActivity;
import com.atman.jixin.ui.im.chatui.StoreDetailActivity;
import com.atman.jixin.ui.shop.GoodsDetailActivity;
import com.atman.jixin.ui.shop.MemberCenterActivity;
import com.atman.jixin.utils.BitmapTools;
import com.atman.jixin.utils.Common;
import com.atman.jixin.utils.MyTools;
import com.atman.jixin.utils.UiHelper;
import com.atman.jixin.utils.face.FaceRelativeLayout;
import com.atman.jixin.widget.EditTextDialog;
import com.atman.jixin.widget.downfile.DownloadAudioFile;
import com.base.baselibs.iimp.AdapterInterface;
import com.base.baselibs.iimp.EditCheckBack;
import com.base.baselibs.iimp.MyTextWatcherTwo;
import com.base.baselibs.net.MyStringCallback;
import com.base.baselibs.util.LogUtils;
import com.base.baselibs.widget.MyCleanEditText;
import com.base.baselibs.widget.localalbum.common.ImageUtils;
import com.base.baselibs.widget.localalbum.common.LocalImageHelper;
import com.base.baselibs.widget.localalbum.ui.LocalAlbum;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.tbl.okhttputils.OkHttpUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by tangbingliang on 16/10/21.
 */

public class ShopIMActivity extends MyBaseActivity
        implements AdapterInterface, EditCheckBack, P2PChatAdapter.P2PAdapterInter
        , DownloadAudioFile.onDownInterface, EditTextDialog.ETOnClick {

    @Bind(R.id.p2pchat_lv)
    PullToRefreshListView p2pchatLv;
    @Bind(R.id.p2pchat_add_iv)
    ImageView p2pchatAddIv;
    @Bind(R.id.blogdetail_addcomment_et)
    MyCleanEditText blogdetailAddcommentEt;
    @Bind(R.id.blogdetail_addemol_iv)
    ImageView blogdetailAddemolIv;
    @Bind(R.id.p2pchat_service_or_keyboard_iv)
    ImageView p2pchatServiceOrKeyboardIv;
    @Bind(R.id.p2pchat_send_bt)
    Button p2pchatSendBt;
    @Bind(R.id.ll1)
    LinearLayout ll1;
    @Bind(R.id.p2pchat_add_ll)
    LinearLayout p2pchatAddLl;
    @Bind(R.id.vp_contains)
    ViewPager vpContains;
    @Bind(R.id.iv_image)
    LinearLayout ivImage;
    @Bind(R.id.ll_facechoose)
    RelativeLayout llFacechoose;
    @Bind(R.id.FaceRelativeLayout)
    FaceRelativeLayout FaceRelativeLayout;
    @Bind(R.id.p2pchat_service_gv)
    GridView p2pchatServiceGv;
    @Bind(R.id.p2pchat_service_ll)
    LinearLayout p2pchatServiceLl;

    private QRScanCodeModel mQRScanCodeModel = new QRScanCodeModel();
    private Context mContext = ShopIMActivity.this;
    private boolean isFromList = false;
    private long storeId = -1;
    private String name;
    private String avatar;
    private int adChatTypeText;

    private GetChatServiceModel mGetChatServiceModel;
    private ChatServiceAdapter mGVAdapter;

    private ChatListModelDao mChatListModelDao;
    private ChatMessageModelDao mChatMessageModelDao;
    private LocationNumberModelDao mLocationNumberModelDao;
    private ChatMessageModel allMessage;
    private LocationNumberModel mNumberModel;
    private List<ChatMessageModel> allMessageList = new ArrayList<>();

    private P2PChatAdapter mAdapter;

    private MessageModel temp;

    private final int CHOOSE_BIG_PICTURE = 444;
    private final int TAKE_BIG_PICTURE = 555;
    private Uri imageUri;
    private String path;

    private int mTime;
    private String audioURL;
    private int positionAudio;
    private MediaPlayer mMediaPlayer = new MediaPlayer();
    private AnimationDrawable mAnimationDrawable;
    private boolean isChange = false;

    private EditTextDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopim);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
    }

    public static Intent buildIntent(Context context, QRScanCodeModel mQRScanCodeModel, boolean isFromList) {
        Intent intent = new Intent(context, ShopIMActivity.class);
        Bundle b = new Bundle();
        b.putSerializable("bean", mQRScanCodeModel);
        intent.putExtras(b);
        intent.putExtra("isFromList", isFromList);
        return intent;
    }

    public static Intent buildIntent(Context context, long id, String name, String avatar, boolean isFromList) {
        Intent intent = new Intent(context, ShopIMActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("name", name);
        intent.putExtra("avatar", avatar);
        intent.putExtra("isFromList", isFromList);
        return intent;
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);

        isFromList = getIntent().getBooleanExtra("isFromList", false);
        if (isFromList) {
            name = getIntent().getStringExtra("name");
            avatar = getIntent().getStringExtra("avatar");
            storeId = getIntent().getLongExtra("id", -1);
        } else {//扫描跳转的
            mQRScanCodeModel = (QRScanCodeModel) getIntent().getSerializableExtra("bean");
            if (mQRScanCodeModel != null) {
                storeId = mQRScanCodeModel.getBody().getStoreBean().getId();
                name = mQRScanCodeModel.getBody().getStoreBean().getStoreName();
                avatar = mQRScanCodeModel.getBody().getStoreBean().getMessageBean().getTargetAvatar();
            }
        }
        LogUtils.e("name:"+name+",storeId:"+storeId+",avatar:"+avatar);
        setBarTitleTx(name);

        setBarRightIv(R.mipmap.shop_member_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(MemberCenterActivity.buildIntent(mContext, storeId));
            }
        });

        blogdetailAddcommentEt.addTextChangedListener(new MyTextWatcherTwo(this));

        mChatListModelDao = MyBaseApplication.getApplication().getDaoSession().getChatListModelDao();
        mChatMessageModelDao = MyBaseApplication.getApplication().getDaoSession().getChatMessageModelDao();
        mLocationNumberModelDao = MyBaseApplication.getApplication().getDaoSession().getLocationNumberModelDao();

        allMessageList = mChatMessageModelDao.queryBuilder().where(ChatMessageModelDao.Properties.TargetId.eq(storeId)
                , ChatMessageModelDao.Properties.LoginId.eq(MyBaseApplication.USERINFOR.getBody().getAtmanUserId())).build().list();

        initGridView();
        initListView();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUserEvent(MessageEvent event) {
        //收到消息
        GetMessageModel mGetMessageModel = event.mGetMessageModel;
        ChatMessageModel mChatMessageModel = event.mChatMessageModel;
        if (mChatMessageModel.getTargetId() == storeId) {
            mAdapter.addImMessageDao(mChatMessageModel);

            ChatListModel mChatListModel = mChatListModelDao.queryBuilder()
                    .where(ChatListModelDao.Properties.TargetId.eq(mGetMessageModel.getContent().getTargetId())
                            , ChatListModelDao.Properties.LoginId.eq(MyBaseApplication.USERINFOR.getBody().getAtmanUserId())).build().unique();
            if (mChatListModel != null) {
                mChatListModel.setUnreadNum(0);
                mChatListModelDao.update(mChatListModel);
            }

            if (mGetMessageModel.getContent().getOperaterList()!=null && !isChange) {
                isChange = true;
                mGVAdapter.updateListView(mGetMessageModel.getContent().getOperaterList());
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUserEvent(updateChatMessageServiceEvent event) {
        //发送完毕事件接收
        updateChatMessage(event.i, event.str, event.chatId, event.Id);
    }

    private void initListView() {
        initRefreshView(PullToRefreshBase.Mode.DISABLED, p2pchatLv);
        mAdapter = new P2PChatAdapter(mContext, getmWidth(), p2pchatLv, handler, runnable, this);
        p2pchatLv.setAdapter(mAdapter);
        mAdapter.setLeftChange(true);
        mAdapter.setLeftImageUrl(avatar);

        mAdapter.addImMessageDao(allMessageList);
        p2pchatLv.getRefreshableView().post(new Runnable() {
            @Override
            public void run() {
                p2pchatLv.getRefreshableView().setSelection(mAdapter.getCount());
            }
        });
        p2pchatLv.getRefreshableView().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (isIMOpen()) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0); //强制隐藏键盘
                }
                p2pchatServiceOrKeyboardIv.setImageResource(R.mipmap.adchat_input_action_icon_struct);
                p2pchatSendBt.setVisibility(View.GONE);
                p2pchatServiceOrKeyboardIv.setVisibility(View.VISIBLE);
                blogdetailAddcommentEt.setVisibility(View.VISIBLE);
                llFacechoose.setVisibility(View.GONE);
                p2pchatAddLl.setVisibility(View.GONE);
                p2pchatServiceLl.setVisibility(View.GONE);
                return false;
            }
        });
    }

    private void initGridView() {
        mGVAdapter = new ChatServiceAdapter(mContext, this);
        p2pchatServiceGv.setAdapter(mGVAdapter);
        p2pchatServiceGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mGVAdapter.getItem(position).getOperaterType()==7) {//返回上一级
                    isChange = false;
                    mGVAdapter.updateListView(mGetChatServiceModel.getBody().getMessageBean().getOperaterList());
                } else {
                    LocationNumberModel locationNumberModel = mLocationNumberModelDao.queryBuilder()
                            .where(LocationNumberModelDao.Properties.TargetId.eq(storeId)
                                    , LocationNumberModelDao.Properties.LoginId.eq(MyBaseApplication.USERINFOR.getBody().getAtmanUserId())).build().unique();
                    if (mGVAdapter.getItem(position).getIdentifyChange()==1) {//更改桌号
                        String content = "";
                        if (locationNumberModel!=null && locationNumberModel.getLocation()!=null) {
                            content = locationNumberModel.getLocation();
                        }
                        showLactionDialog(position, content);
                    } else {
                        String loactionNum = "";
                        if (mGVAdapter.getItem(position).getIdentifyNeed()==1) {//是否需要桌号
                            if (locationNumberModel==null || locationNumberModel.getLocation().isEmpty()) {
                                showLactionDialog(position, "");
                                return;
                            } else {
                                loactionNum = locationNumberModel.getLocation();
                            }
                        }
                        buildMessage(ADChatType.ADChatType_Text
                                , mGVAdapter.getItem(position).getOperaterName(), loactionNum, true
                                , mGVAdapter.getItem(position));
                    }
                }
            }
        });
    }

    //设置桌号
    private void showLactionDialog(int position, String content) {
        dialog = new EditTextDialog(mContext
                , mGVAdapter.getItem(position).getIdentifyChangeNotice(), "", content
                , false, 0, ShopIMActivity.this);
        if (!dialog.isShowing()) {
            dialog.show();
            p2pchatServiceOrKeyboardIv.setImageResource(R.mipmap.adchat_input_action_icon_struct);
            p2pchatServiceOrKeyboardIv.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void doInitBaseHttp() {
        super.doInitBaseHttp();
        OkHttpUtils.get().url(Common.Url_Get_UserChat + storeId)
                .headers(MyBaseApplication.getApplication().getHeaderSeting())
                .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                .tag(Common.NET_GET_USERCHAT_ID).id(Common.NET_GET_USERCHAT_ID).build()
                .execute(new MyStringCallback(mContext, "", this, true));
    }

    @Override
    public void onStringResponse(String data, Response response, int id) {
        if (id == Common.NET_GET_USERCHAT_ID) {
            super.onStringResponse(data, response, id);
            mGetChatServiceModel = mGson.fromJson(data, GetChatServiceModel.class);

            mGVAdapter.updateListView(mGetChatServiceModel.getBody().getMessageBean().getOperaterList());
            AddWelcome();
            if (mGVAdapter.getCount()==0) {
                setNotService();
            }
            if (!isFromList) {
                if (mQRScanCodeModel!=null && mQRScanCodeModel.getBody().getMessageBean()!=null
                        && !isChange) {

                    //添加聊天列表
                    ChatListModel mChatListModel= mChatListModelDao.queryBuilder().where(ChatListModelDao.Properties.TargetId.eq(storeId)
                            , ChatListModelDao.Properties.LoginId.eq(MyBaseApplication.USERINFOR.getBody().getAtmanUserId())).build().unique();
                    if (mChatListModel==null) {
                        if (avatar.isEmpty() || mQRScanCodeModel.getBody().getMessageBean().getTargetName().isEmpty()) {
                            return;
                        }
                        ChatListModel tempChat = new ChatListModel(null, mQRScanCodeModel.getBody().getMessageBean().getTargetId()
                                , MyBaseApplication.USERINFOR.getBody().getAtmanUserId(), mQRScanCodeModel.getBody().getMessageBean().getTargetType()
                                , mQRScanCodeModel.getBody().getMessageBean().getSendTime(), mQRScanCodeModel.getBody().getMessageBean().getContent(), 0, "", mQRScanCodeModel.getBody().getMessageBean().getTargetName(), avatar
                                , mQRScanCodeModel.getBody().getMessageBean().getType(),mQRScanCodeModel.getBody().getMessageBean().getChatId());
                        mChatListModelDao.save(tempChat);
                    } else {
                        mChatListModel.setSendTime(mQRScanCodeModel.getBody().getMessageBean().getSendTime());
                        mChatListModel.setUnreadNum(0);
                        if (mQRScanCodeModel.getBody().getMessageBean().getContent()==null) {
                            mChatListModel.setContent(mQRScanCodeModel.getBody().getStoreBean().getStoreName());
                        } else {
                            mChatListModel.setContent(mQRScanCodeModel.getBody().getMessageBean().getContent());
                        }
                        mChatListModel.setType(mQRScanCodeModel.getBody().getMessageBean().getType());
                        mChatListModelDao.update(mChatListModel);
                    }

                    //添加聊天记录
                    ChatMessageModel tempMessage = new ChatMessageModel();
                    tempMessage.setId(null);
                    tempMessage.setTargetId(mQRScanCodeModel.getBody().getMessageBean().getTargetId());
                    tempMessage.setLoginId(MyBaseApplication.USERINFOR.getBody().getAtmanUserId());
                    tempMessage.setType(mQRScanCodeModel.getBody().getMessageBean().getType());
                    tempMessage.setTargetType(mQRScanCodeModel.getBody().getMessageBean().getTargetType());
                    tempMessage.setTargetName(mQRScanCodeModel.getBody().getMessageBean().getTargetName());
                    tempMessage.setTargetAvatar(avatar);
                    tempMessage.setSendTime(mQRScanCodeModel.getBody().getMessageBean().getSendTime());
                    tempMessage.setContent(mQRScanCodeModel.getBody().getMessageBean().getContent());

                    if (mQRScanCodeModel.getBody().getMessageBean().getImageT_back()!=null) {
                        tempMessage.setImageT_back(mQRScanCodeModel.getBody().getMessageBean().getImageT_back());
                        tempMessage.setImageT_icon(mQRScanCodeModel.getBody().getMessageBean().getImageT_icon());
                        tempMessage.setImageT_title(mQRScanCodeModel.getBody().getMessageBean().getImageT_title());
                    }

                    if (mQRScanCodeModel.getBody().getMessageBean().getEventAction()!=null) {
                        tempMessage.setActionType(mQRScanCodeModel.getBody().getMessageBean().getEventAction().getActionType());
                        tempMessage.setCouponId(mQRScanCodeModel.getBody().getMessageBean().getEventAction().getCouponId());
                        tempMessage.setEnterpriseId(mQRScanCodeModel.getBody().getMessageBean().getEventAction().getEnterpriseId());
                        tempMessage.setGoodId(mQRScanCodeModel.getBody().getMessageBean().getEventAction().getGoodId());
                        tempMessage.setStoreId(mQRScanCodeModel.getBody().getMessageBean().getEventAction().getStoreId());
                    }

                    if (mQRScanCodeModel.getBody().getMessageBean().getOperaterList()!=null
                            && mQRScanCodeModel.getBody().getMessageBean().getOperaterList().size()>=1) {
                        tempMessage.setOperaterId(mQRScanCodeModel.getBody().getMessageBean().getOperaterList().get(0).getOperaterId());
                        tempMessage.setOperaterName(mQRScanCodeModel.getBody().getMessageBean().getOperaterList().get(0).getOperaterName());
                        tempMessage.setOperaterType(mQRScanCodeModel.getBody().getMessageBean().getOperaterList().get(0).getOperaterType());
                    }

                    tempMessage.setReaded(0);
                    tempMessage.setSendStatus(0);
                    tempMessage.setSelfSend(false);
                    mAdapter.addImMessageDao(tempMessage);
                    mChatMessageModelDao.save(tempMessage);

                    if (mQRScanCodeModel.getBody().getMessageBean().getType() == ADChatType.ADChatType_ImageText) {
                        if (mQRScanCodeModel.getBody().getMessageBean().getEventAction().getActionType()
                                ==EventActionType.EventActionType_GoodList) {//菜单预览
                            startActivity(MenuPreviewActivity.buildIntent(mContext
                                    , mQRScanCodeModel.getBody().getMessageBean().getImageT_title()
                                    , mQRScanCodeModel.getBody().getMessageBean().getTargetId()));
                        } else if (mQRScanCodeModel.getBody().getMessageBean().getEventAction().getActionType()
                                ==EventActionType.EventActionType_Enterprise) {//企业介绍
                            startActivity(CompanyIntroductionActivity.buildIntent(mContext
                                    , mQRScanCodeModel.getBody().getMessageBean().getImageT_title()
                                    , mQRScanCodeModel.getBody().getMessageBean().getEventAction().getEnterpriseId()));
                        } else if (mQRScanCodeModel.getBody().getMessageBean().getEventAction().getActionType()
                                ==EventActionType.EventActionType_Menu) {//商品列表  (菜单,点菜)
                        } else if (mQRScanCodeModel.getBody().getMessageBean().getEventAction().getActionType()
                                ==EventActionType.EventActionType_Good) {//商品
                            startActivity(GoodsDetailActivity.buildIntent(mContext
                                    , mQRScanCodeModel.getBody().getMessageBean().getEventAction().getGoodId()
                                    , mQRScanCodeModel.getBody().getMessageBean().getImageT_title(), 0, 0, null));
                        } else if (mQRScanCodeModel.getBody().getMessageBean().getEventAction().getActionType()
                                ==EventActionType.EventActionType_Coupon) {//优惠券
                            startActivity(GetCouponActivity.buildIntent(mContext
                                    , mQRScanCodeModel.getBody().getMessageBean().getEventAction().getCouponId()));
                        } else  if (mQRScanCodeModel.getBody().getMessageBean().getEventAction().getActionType()
                                ==EventActionType.EventActionType_AnnualMeeting) {//年会
                            startActivity(new Intent(mContext, AnnualMeetingActivity.class));
                        }
                    }

                    List<QRScanCodeModel.BodyBean.MessageBeanBean.OperaterListBean> tempList =
                            mQRScanCodeModel.getBody().getMessageBean().getOperaterList();
                    if (tempList==null) {
                        return;
                    }
                    isChange = true;
                    List<GetChatServiceModel.BodyBean.MessageBeanBean.OperaterListBean> allList = new ArrayList<>();
                    for (int i=0;i<tempList.size();i++) {
                        GetChatServiceModel.BodyBean.MessageBeanBean.OperaterListBean temp
                                = new GetChatServiceModel.BodyBean.MessageBeanBean.OperaterListBean();
                        temp.setOperaterId(tempList.get(i).getOperaterId());
                        temp.setOperaterName(tempList.get(i).getOperaterName());
                        temp.setOperaterType(tempList.get(i).getOperaterType());
                        if (tempList.get(i).getOperaterExtra()!=null) {
                            temp.setOperaterExtra(tempList.get(i).getOperaterExtra());
                        }
                        if (tempList.get(i).getIdentifyChangeNotice()!=null) {
                            temp.setIdentifyChangeNotice(tempList.get(i).getIdentifyChangeNotice());
                        }
                        temp.setIdentifyChange(tempList.get(i).getIdentifyChange());
                        temp.setIdentifyNeed(tempList.get(i).getIdentifyNeed());
                        allList.add(temp);
                    }
                    GetMessageModel.ContentBean operaterList = new GetMessageModel.ContentBean();
                    operaterList.setOperaterList(allList);

                    mGVAdapter.updateListView(operaterList.getOperaterList());
                }
            }
        }
    }

    //添加欢迎语
    private void AddWelcome() {
        ChatListModel mChatListModel= mChatListModelDao.queryBuilder().where(ChatListModelDao.Properties.TargetId.eq(storeId)
                , ChatListModelDao.Properties.LoginId.eq(MyBaseApplication.USERINFOR.getBody().getAtmanUserId())).build().unique();
        if (mChatListModel==null) {
            //添加欢迎语
            ChatMessageModel tempMessage = new ChatMessageModel();
            tempMessage.setId(null);
            tempMessage.setTargetId(mGetChatServiceModel.getBody().getMessageBean().getTargetId());
            tempMessage.setLoginId(MyBaseApplication.USERINFOR.getBody().getAtmanUserId());
            tempMessage.setType(mGetChatServiceModel.getBody().getMessageBean().getType());
            tempMessage.setTargetType(mGetChatServiceModel.getBody().getMessageBean().getTargetType());
            tempMessage.setTargetName(mGetChatServiceModel.getBody().getMessageBean().getTargetName());
            tempMessage.setTargetAvatar(avatar);
            tempMessage.setSendTime(mGetChatServiceModel.getBody().getMessageBean().getSendTime());
            tempMessage.setContent(mGetChatServiceModel.getBody().getMessageBean().getContent());
            tempMessage.setReaded(0);
            tempMessage.setSendStatus(0);
            tempMessage.setSelfSend(false);
            mAdapter.addImMessageDao(tempMessage);
            mChatMessageModelDao.save(tempMessage);

            ChatListModel tempChat = new ChatListModel(null, mGetChatServiceModel.getBody().getMessageBean().getTargetId()
                    , MyBaseApplication.USERINFOR.getBody().getAtmanUserId(), mGetChatServiceModel.getBody().getMessageBean().getTargetType()
                    , mGetChatServiceModel.getBody().getMessageBean().getSendTime(), mGetChatServiceModel.getBody().getMessageBean().getContent()
                    , 0, "", mGetChatServiceModel.getBody().getMessageBean().getTargetName(), avatar
                    , mGetChatServiceModel.getBody().getMessageBean().getType(),-1);
            mChatListModelDao.save(tempChat);
        }
    }

    //没有返回服务时的设置
    private void setNotService() {
        p2pchatServiceLl.setVisibility(View.GONE);
        p2pchatServiceOrKeyboardIv.setVisibility(View.GONE);
        p2pchatSendBt.setVisibility(View.VISIBLE);
    }

    @Override
    public void onError(Call call, Exception e, int code, int id) {
        super.onError(call, e, code, id);
        updateChatMessage(1, "", -1, -1);
    }

    //更新本地聊天记录
    private void updateChatMessage(int i, String str, long chatId, long Id) {
        if (i==-2) {//更新接收语音本地下载
            ChatMessageModel upMessage = mChatMessageModelDao.queryBuilder().where(
                    ChatMessageModelDao.Properties.ChatId.eq(chatId)).build().unique();
            upMessage.setAudioLocationUrl(str);
            mAdapter.setImMessageAudio(upMessage.getId(), str);
        } else {
            long xId = 0;
            if (Id != -1) {
                xId = Id;
            } else {
                if (allMessage!=null) {
                    xId = allMessage.getId();
                }
            }
            ChatMessageModel upMessage = mChatMessageModelDao.queryBuilder().where(
                    ChatMessageModelDao.Properties.Id.eq(xId)).build().unique();
            if (upMessage!=null) {
                if (i==-1) {//更新Content
                    upMessage.setContent(str);
                    mAdapter.setImMessageContent(upMessage.getId(), str);
                } else {//更新发送消息状态
                    upMessage.setSendStatus(i);
                    mAdapter.setImMessageStatus(upMessage.getId(), i);
                }
                mChatMessageModelDao.update(upMessage);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(Common.NET_GET_USERCHAT_ID);
        EventBus.getDefault().unregister(this);
    }

    @OnClick({R.id.p2pchat_add_iv, R.id.blogdetail_addemol_iv, R.id.p2pchat_service_or_keyboard_iv, R.id.p2pchat_send_bt
            , R.id.p2pchat_add_picture_tv, R.id.p2pchat_add_camera_tv, R.id.p2pchat_add_record_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.p2pchat_add_iv://其他
                if (p2pchatAddLl.getVisibility() == View.VISIBLE) {
                    p2pchatAddLl.setVisibility(View.GONE);
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(0, InputMethodManager.RESULT_SHOWN);
                } else {
                    if (isIMOpen()) {
                        cancelIM(view);
                    }
                    p2pchatAddLl.setVisibility(View.VISIBLE);
                }
                p2pchatServiceLl.setVisibility(View.GONE);
                p2pchatServiceOrKeyboardIv.setImageResource(R.mipmap.adchat_input_action_icon_struct);
                blogdetailAddemolIv.setImageResource(R.mipmap.adchat_input_action_icon_face);
                llFacechoose.setVisibility(View.GONE);
                handler.postDelayed(runnable, 200);
                break;
            case R.id.blogdetail_addemol_iv://表情
                if (llFacechoose.getVisibility() == View.GONE) {
                    if (isIMOpen()) {
                        cancelIM(view);
                    }
                    llFacechoose.setVisibility(View.VISIBLE);
                    blogdetailAddemolIv.setImageResource(R.mipmap.adchat_input_action_icon_keyboard);
                } else {
                    llFacechoose.setVisibility(View.GONE);
                    blogdetailAddemolIv.setImageResource(R.mipmap.adchat_input_action_icon_face);
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(0, InputMethodManager.RESULT_SHOWN);
                }
                p2pchatServiceLl.setVisibility(View.GONE);
                p2pchatServiceOrKeyboardIv.setImageResource(R.mipmap.adchat_input_action_icon_struct);
                p2pchatAddLl.setVisibility(View.GONE);
                handler.postDelayed(runnable, 200);
                break;
            case R.id.p2pchat_service_or_keyboard_iv://切换
                if (p2pchatServiceLl.getVisibility() == View.VISIBLE) {
                    p2pchatServiceLl.setVisibility(View.GONE);
                    p2pchatServiceOrKeyboardIv.setImageResource(R.mipmap.adchat_input_action_icon_struct);
                    blogdetailAddcommentEt.setFocusable(true);
                    blogdetailAddcommentEt.setFocusableInTouchMode(true);
                    //打开软键盘
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                } else {
                    if (isIMOpen()) {
                        cancelIM(view);
                    }
                    p2pchatServiceLl.setVisibility(View.VISIBLE);
                    p2pchatServiceOrKeyboardIv.setImageResource(R.mipmap.adchat_input_action_icon_keyboard);
                }
                llFacechoose.setVisibility(View.GONE);
                p2pchatAddLl.setVisibility(View.GONE);
                blogdetailAddemolIv.setImageResource(R.mipmap.adchat_input_action_icon_face);
                handler.postDelayed(runnable, 200);
                break;
            case R.id.p2pchat_send_bt://发送按钮
                if (blogdetailAddcommentEt.getText().toString().trim().isEmpty()) {
                    return;
                }
                buildMessage(ADChatType.ADChatType_Text, blogdetailAddcommentEt.getText().toString().trim()
                        , "", false, null);
                blogdetailAddcommentEt.setText("");
                break;
            case R.id.p2pchat_add_picture_tv://其他--图片
//                Intent getAlbum = new Intent(Intent.ACTION_GET_CONTENT);
//                getAlbum.setType("image/*");
//                startActivityForResult(getAlbum, CHOOSE_BIG_PICTURE);
//                p2pchatAddLl.setVisibility(View.GONE);
                Intent intent = new Intent(mContext, LocalAlbum.class);
                startActivityForResult(intent, ImageUtils.REQUEST_CODE_GETIMAGE_BYCROP);
                break;
            case R.id.p2pchat_add_camera_tv://其他--相机
                path = UiHelper.photo(mContext, path, TAKE_BIG_PICTURE);
                p2pchatAddLl.setVisibility(View.GONE);
                break;
            case R.id.p2pchat_add_record_tv://其他--语音
                p2pchatAddLl.setVisibility(View.GONE);
                startActivityForResult(RecordingActivity.buildIntent(mContext, storeId), Common.TO_RECORDE);
                break;
        }
    }

    //构建消息数据
    private void buildMessage(int adChatType, String content, String loactionNum, boolean isService
            , GetChatServiceModel.BodyBean.MessageBeanBean.OperaterListBean bean) {

        mNumberModel = mLocationNumberModelDao.queryBuilder()
                .where(LocationNumberModelDao.Properties.TargetId.eq(storeId)
                        , LocationNumberModelDao.Properties.LoginId.eq(MyBaseApplication.USERINFOR.getBody().getAtmanUserId())).build().unique();

        adChatTypeText = adChatType;
        temp = new MessageModel();
        temp.setTargetType(ADChatTargetType.ADChatTargetType_Shop);
        temp.setTargetId(storeId);
        temp.setTargetName(name);
        if (mNumberModel!=null) {
            LogUtils.e("mNumberModel.getLocation():"+mNumberModel.getLocation());
            temp.setIdentifyStr(mNumberModel.getLocation());
        }
        temp.setTargetAvatar(MyBaseApplication.USERINFOR.getBody().getMemberAvatar());
        temp.setSendTime(System.currentTimeMillis());
        temp.setContent(content);
        temp.setType(adChatType);
        if (adChatType == ADChatType.ADChatType_Image) {
        } else if (adChatType == ADChatType.ADChatType_Text) {
            if (isService) {//服务
                List<GetChatServiceModel.BodyBean.MessageBeanBean.OperaterListBean> operaterList = new ArrayList<>();
                GetChatServiceModel.BodyBean.MessageBeanBean.OperaterListBean tempOper = new GetChatServiceModel.BodyBean.MessageBeanBean.OperaterListBean();
                tempOper.setOperaterId(bean.getOperaterId());
                tempOper.setOperaterType(bean.getOperaterType());
                tempOper.setOperaterName(bean.getOperaterName());
                tempOper.setIdentifyChange(bean.getIdentifyChange());
                tempOper.setIdentifyNeed(bean.getIdentifyNeed());
                tempOper.setStructLanguage(bean.isStructLanguage());
                if (bean.getIdentifyChangeNotice()!=null) {
                    tempOper.setIdentifyChangeNotice(bean.getIdentifyChangeNotice());
                }
                operaterList.add(tempOper);
                temp.setOperaterList(operaterList);
                temp.setContent(bean.getOperaterName());
            } else {
                temp.setContent(content);
            }
        } else if (adChatType == ADChatType.ADChatType_ImageText) {
        } else if (adChatType == ADChatType.ADChatType_Audio) {
            temp.setAudio_duration(mTime);
        } else if (adChatType == ADChatType.ADChatType_Video) {
        }

        //添加聊天列表
        ChatListModel mChatListModel= mChatListModelDao.queryBuilder().where(ChatListModelDao.Properties.TargetId.eq(storeId)
                , ChatListModelDao.Properties.LoginId.eq(MyBaseApplication.USERINFOR.getBody().getAtmanUserId())).build().unique();
        if (mChatListModel==null) {
            if (avatar.isEmpty() || temp.getTargetName().isEmpty()) {
                return;
            }
            ChatListModel tempChat = new ChatListModel(null, temp.getTargetId()
                    , MyBaseApplication.USERINFOR.getBody().getAtmanUserId(), temp.getTargetType()
                    , temp.getSendTime(), temp.getContent(), 0, "", temp.getTargetName(), avatar, adChatType, temp.getChatId());
            mChatListModelDao.save(tempChat);
        } else {
            mChatListModel.setSendTime(temp.getSendTime());
            mChatListModel.setUnreadNum(0);
            mChatListModel.setContent(temp.getContent());
            mChatListModel.setType(adChatType);
            mChatListModelDao.update(mChatListModel);
        }
        //添加聊天记录
        ChatMessageModel tempMessage = new ChatMessageModel();
        tempMessage.setId(null);
        tempMessage.setIdentifyStr(temp.getIdentifyStr());
        tempMessage.setTargetId(temp.getTargetId());
        tempMessage.setLoginId(MyBaseApplication.USERINFOR.getBody().getAtmanUserId());
        tempMessage.setType(temp.getType());
        tempMessage.setTargetType(temp.getTargetType());
        tempMessage.setTargetName(temp.getTargetName());
        tempMessage.setTargetAvatar(avatar);
        tempMessage.setSendTime(temp.getSendTime());
        tempMessage.setContent(temp.getContent());

        if (temp.getVideo_image_url()!=null) {
            tempMessage.setVideo_image_url(temp.getVideo_image_url());
        }

        if (temp.getAudio_duration()>0) {
            tempMessage.setAudio_duration(temp.getAudio_duration());
            tempMessage.setAudioLocationUrl(audioURL);
        }

        if (temp.getImageT_back()!=null) {
            tempMessage.setImageT_back(temp.getImageT_back());
            tempMessage.setImageT_icon(temp.getImageT_icon());
            tempMessage.setImageT_title(temp.getImageT_title());
        }

        if (temp.getEventAction()!=null) {
            tempMessage.setActionType(temp.getEventAction().getActionType());
        }

        if (temp.getOperaterList()!=null && temp.getOperaterList().size()>=1) {
            tempMessage.setOperaterId(temp.getOperaterList().get(0).getOperaterId());
            tempMessage.setOperaterName(temp.getOperaterList().get(0).getOperaterName());
            tempMessage.setOperaterType(temp.getOperaterList().get(0).getOperaterType());
        }

        tempMessage.setReaded(0);
        tempMessage.setSendStatus(2);
        tempMessage.setSelfSend(true);
        allMessage = tempMessage;
        mAdapter.addImMessageDao(allMessage);
        mChatMessageModelDao.save(tempMessage);

        if (adChatType != ADChatType.ADChatType_Image) {
            Intent intent = new Intent(mContext, SeedMessageService.class);
            startService(intent);
        }

    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode, Bundle options) {
        super.startActivityForResult(intent, requestCode, options);
        overridePendingTransition(com.base.baselibs.R.anim.activity_bottom_in, com.base.baselibs.R.anim.activity_bottom_out);
    }

    private List<LocalImageHelper.LocalFile> files = new ArrayList<>();
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ImageUtils.REQUEST_CODE_GETIMAGE_BYCROP) {
            if (LocalImageHelper.getInstance().isResultOk()) {
                LocalImageHelper.getInstance().setResultOk(false);
                //获取选中的图片
                files.addAll(0,LocalImageHelper.getInstance().getCheckedItems());
                seedMorePicMessage();
            }
            //清空选中的图片
            LocalImageHelper.getInstance().getCheckedItems().clear();
            files.clear();
        }
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == Common.TO_RECORDE) {
            audioURL = data.getStringExtra("url");
            mTime = data.getIntExtra("time", 0);
            if (audioURL.isEmpty()) {
                showToast("语音录制失败!");
            } else {
                File f = new File(audioURL);
                if (!f.exists()) {
                    showToast("音频不存在");
                    return;
                }

                buildMessage(ADChatType.ADChatType_Audio, audioURL, "", false, null);
            }
        } else {
            if (requestCode == CHOOSE_BIG_PICTURE) {//选择照片
                imageUri = data.getData();
            } else if (requestCode == TAKE_BIG_PICTURE) {
                imageUri = Uri.parse("file:///" + path);
            }
            if (imageUri != null) {
                LogUtils.e("imageUri:"+imageUri);
                try {
                    File temp = BitmapTools.revitionImage(mContext, imageUri);
                    if (temp==null) {
                        showToast("发送失败");
                        return;
                    }
                    buildMessage(ADChatType.ADChatType_Image, temp.getPath(), "",false, null);
                    Intent intent = new Intent(mContext, SeedMessageService.class);
                    startService(intent);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void seedMorePicMessage() {
        for (int i = 0; i < files.size(); i++) {
            imageUri = Uri.parse(files.get(i).getOriginalUri());
            LogUtils.e("imageUri:"+imageUri.toString());
            try {
                File temp = BitmapTools.revitionImage(mContext, imageUri);
                if (temp==null) {
                    showToast("发送失败");
                    return;
                }
                buildMessage(ADChatType.ADChatType_Image, temp.getPath(), "",false, null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        LocalImageHelper.getInstance().setCurrentSize(0);
        Intent intent = new Intent(mContext, SeedMessageService.class);
        startService(intent);
    }

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
//            p2pchatLv.getRefreshableView().smoothScrollToPosition(mAdapter.getCount());
        }
    };

    @Override
    public void onItemClick(View view, int position) {

    }

    @Override
    public void isNull() {
        if (TextUtils.isEmpty(blogdetailAddcommentEt.getText().toString())
                && mGVAdapter.getCount()!=0) {
            p2pchatSendBt.setVisibility(View.GONE);
            p2pchatServiceOrKeyboardIv.setVisibility(View.VISIBLE);
        } else {
            p2pchatSendBt.setVisibility(View.VISIBLE);
            p2pchatServiceOrKeyboardIv.setVisibility(View.GONE);
        }
    }

    @Override
    public void onItem(View v, int position) {
        switch (v.getId()) {
            case R.id.item_p2pchat_root_Rl:
                if (isIMOpen()) {
                    cancelIM(v);
                }
                p2pchatServiceOrKeyboardIv.setImageResource(R.mipmap.adchat_input_action_icon_struct);
                p2pchatSendBt.setVisibility(View.GONE);
                p2pchatServiceOrKeyboardIv.setVisibility(View.VISIBLE);
                blogdetailAddcommentEt.setVisibility(View.VISIBLE);
                llFacechoose.setVisibility(View.GONE);
                p2pchatAddLl.setVisibility(View.GONE);
                p2pchatServiceLl.setVisibility(View.GONE);
                handler.postDelayed(runnable, 200);
                break;
            case R.id.item_p2pchat_imagetext_left_rl:
                if (mAdapter.getItem(position).getActionType()
                        ==EventActionType.EventActionType_GoodList) {//菜单预览
                    startActivity(MenuPreviewActivity.buildIntent(mContext
                            , mAdapter.getItem(position).getImageT_title()
                            , mAdapter.getItem(position).getStoreId()));
                } else if (mAdapter.getItem(position).getActionType()
                        ==EventActionType.EventActionType_Enterprise) {//企业介绍
                    startActivity(CompanyIntroductionActivity.buildIntent(mContext
                            , mAdapter.getItem(position).getImageT_title()
                            , mAdapter.getItem(position).getEnterpriseId()));
                } else if (mAdapter.getItem(position).getActionType()
                        ==EventActionType.EventActionType_Menu) {//商品列表  (菜单,点菜)
                } else if (mAdapter.getItem(position).getActionType()
                        ==EventActionType.EventActionType_Good) {//商品
                    startActivity(GoodsDetailActivity.buildIntent(mContext
                            , mAdapter.getItem(position).getGoodId()
                            , mAdapter.getItem(position).getImageT_title(), 0, 0, null));
                } else if (mAdapter.getItem(position).getActionType()
                        ==EventActionType.EventActionType_Coupon) {//优惠券
                    startActivity(GetCouponActivity.buildIntent(mContext
                            , mAdapter.getItem(position).getCouponId()));
                } else  if (mAdapter.getItem(position).getActionType()
                        ==EventActionType.EventActionType_AnnualMeeting) {//年会
                    startActivity(new Intent(mContext, AnnualMeetingActivity.class));
                }
                break;
            case R.id.item_p2pchat_text_headleft_iv:
                startActivity(StoreDetailActivity.buildIntent(mContext, storeId, 1));
                break;
            case R.id.item_p2pchat_image_left_iv:
            case R.id.item_p2pchat_image_right_iv:
                String imagePath = "";
                int n = 0;
                for (int i=0,j=0;i<mAdapter.getCount();i++) {
                    if (mAdapter.getItem(i).getType() == ADChatType.ADChatType_Image) {
                        if (!imagePath.equals("")) {
                            imagePath += ",";
                        }
                        if (mAdapter.getItem(i).getContent().startsWith("/")) {
                            imagePath += mAdapter.getItem(i).getContent();
                        } else {
                            imagePath += MyTools.getHttpUrl(mAdapter.getItem(i).getContent());
                        }
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

    @Override
    public void onItemAudio(View v, int position, AnimationDrawable animationDrawable) {
        switch (v.getId()) {
            case R.id.item_p2pchat_audio_right_ll:
            case R.id.item_p2pchat_audio_left_ll:
                if (position == positionAudio) {
                    if (mMediaPlayer.isPlaying()) {
                        stopAnim();
                    } else {
                        mAnimationDrawable = animationDrawable;
                        positionAudio = position;
                        playAudio(position, true);
                    }
                } else {
                    stopAnim();
                    mAnimationDrawable = animationDrawable;
                    positionAudio = position;
                    playAudio(position, true);
                }
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopAnim();
    }

    private void stopAnim() {
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
            if (mAnimationDrawable!=null) {
                mAnimationDrawable.stop();
                mAnimationDrawable.selectDrawable(0);
            }
        }
    }

    private void playAudio(int position, boolean b) {
        if (mAdapter.getItem(position).getAudioLocationUrl()!=null
                && (new File(mAdapter.getItem(position).getAudioLocationUrl()).exists())) {
            try {
                if (!mMediaPlayer.isPlaying()) {
                    mMediaPlayer.reset();
                    mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mMediaPlayer.setDataSource(mAdapter.getItem(position).getAudioLocationUrl());
                    mMediaPlayer.prepare();
                    mMediaPlayer.start();
                    mAnimationDrawable.start();
                    mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        public void onCompletion(MediaPlayer mp) {
                            mAnimationDrawable.stop();
                            mAnimationDrawable.selectDrawable(0);
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            if (b) {
                String downUrl = Common.ImageUrl + mAdapter.getItem(position).getContent();
                new DownloadAudioFile(ShopIMActivity.this, this).execute(downUrl);
            }
        }
    }

    @Override
    public void finish(String path) {
        updateChatMessage(-2, path, mAdapter.getItem(positionAudio).getChatId(), -1);
        playAudio(positionAudio, false);

    }

    @Override
    public void error() {
        showToast("播放失败");
    }

    @Override
    public void onItemClick(View view, String str, int tagId) {
        if (isIMOpen() && view.getWindowToken()!=null) {
            cancelIM(view);
        }
        switch (view.getId()) {
            case R.id.edittext_dialog_cancel_tx:
                dialog.dismiss();
                break;
            case R.id.edittext_dialog_ok_tx:
                dialog.dismiss();
                LocationNumberModel locationNumberModel = mLocationNumberModelDao.queryBuilder()
                        .where(LocationNumberModelDao.Properties.TargetId.eq(storeId)
                                , LocationNumberModelDao.Properties.LoginId.eq(MyBaseApplication.USERINFOR.getBody().getAtmanUserId())).build().unique();
                if (locationNumberModel==null) {
                    LocationNumberModel locationTemp = new LocationNumberModel(null, storeId
                            , MyBaseApplication.USERINFOR.getBody().getAtmanUserId(), str);
                    mLocationNumberModelDao.save(locationTemp);
                } else {
                    locationNumberModel.setLocation(str);
                    mLocationNumberModelDao.update(locationNumberModel);
                }
                if (str.isEmpty()) {
                    showToast("您已取消设置");
                } else {
                    showToast("修改成功");
                }
                break;
        }
    }

    @Override
    public void onTouchOutside(EditText edittextDialogEt) {
        if (isIMOpen() && edittextDialogEt.getWindowToken()!=null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(edittextDialogEt.getWindowToken(), 0); //强制隐藏键盘
        } else {
            dialog.dismiss();
        }
    }
}
