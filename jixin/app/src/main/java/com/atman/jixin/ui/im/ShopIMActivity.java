package com.atman.jixin.ui.im;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
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
import com.atman.jixin.model.greendao.gen.ChatListModelDao;
import com.atman.jixin.model.greendao.gen.ChatMessageModelDao;
import com.atman.jixin.model.iimp.ADChatTargetType;
import com.atman.jixin.model.iimp.ADChatType;
import com.atman.jixin.model.iimp.EventActionType;
import com.atman.jixin.model.iimp.UpChatFileType;
import com.atman.jixin.model.response.GetChatServiceModel;
import com.atman.jixin.model.response.GetMessageModel;
import com.atman.jixin.model.response.HeadImgResultModel;
import com.atman.jixin.model.response.MessageModel;
import com.atman.jixin.model.response.QRScanCodeModel;
import com.atman.jixin.model.response.UpdateAudioResultModel;
import com.atman.jixin.ui.PictureBrowsingActivity;
import com.atman.jixin.ui.base.MyBaseActivity;
import com.atman.jixin.ui.base.MyBaseApplication;
import com.atman.jixin.ui.im.chatui.CompanyIntroductionActivity;
import com.atman.jixin.ui.im.chatui.MenuPreviewActivity;
import com.atman.jixin.ui.im.chatui.StoreDetailActivity;
import com.atman.jixin.ui.shop.MemberCenterActivity;
import com.atman.jixin.utils.BitmapTools;
import com.atman.jixin.utils.Common;
import com.atman.jixin.utils.MyTools;
import com.atman.jixin.utils.UiHelper;
import com.atman.jixin.utils.face.FaceRelativeLayout;
import com.atman.jixin.widget.downfile.DownloadAudioFile;
import com.base.baselibs.iimp.AdapterInterface;
import com.base.baselibs.iimp.EditCheckBack;
import com.base.baselibs.iimp.MyTextWatcherTwo;
import com.base.baselibs.net.MyStringCallback;
import com.base.baselibs.util.LogUtils;
import com.base.baselibs.util.StringUtils;
import com.base.baselibs.widget.MyCleanEditText;
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
        implements AdapterInterface, EditCheckBack, P2PChatAdapter.P2PAdapterInter, DownloadAudioFile.onDownInterface {

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
    private ChatMessageModel allMessage;
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
        } else {
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

        allMessageList = mChatMessageModelDao.queryBuilder().where(ChatMessageModelDao.Properties.TargetId.eq(storeId)
                , ChatMessageModelDao.Properties.LoginId.eq(MyBaseApplication.USERINFOR.getBody().getAtmanUserId())).build().list();

        initGridView();
        initListView();
    }

    @Subscribe(threadMode = ThreadMode.MAIN) //第2步:注册一个在后台线程执行的方法,用于接收事件
    public void onUserEvent(MessageEvent event) {//参数必须是ClassEvent类型, 否则不会调用此方法
        GetMessageModel mGetMessageModel = event.mGetMessageModel;
        LogUtils.e("storeId:"+storeId);
        if (mGetMessageModel.getContent().getTargetId() == storeId) {
            LogUtils.e("mGetMessageModel.getContent().getTargetId():"+mGetMessageModel.getContent().getTargetId());
            LogUtils.e("mGetMessageModel.getContent().getChatId():"+mGetMessageModel.getContent().getChatId());
            ChatMessageModel upMessage = mChatMessageModelDao.queryBuilder().where(
                    ChatMessageModelDao.Properties.ChatId.eq(mGetMessageModel.getContent().getChatId()))
                    .build().unique();
            LogUtils.e("upMessage:"+upMessage);
            if (upMessage!=null) {
                mAdapter.addImMessageDao(upMessage);
            }
            ChatListModel mChatListModel = mChatListModelDao.queryBuilder()
                    .where(ChatListModelDao.Properties.TargetId.eq(mGetMessageModel.getContent().getTargetId())).build().unique();
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
    }

    private void initGridView() {
        mGVAdapter = new ChatServiceAdapter(mContext, this);
        p2pchatServiceGv.setAdapter(mGVAdapter);
        p2pchatServiceGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mGVAdapter.getItem(position).getOperaterType()==7) {
                    isChange = false;
                    mGVAdapter.updateListView(mGetChatServiceModel.getBody().getMessageBean().getOperaterList());
                } else {
                    buildMessage(ADChatType.ADChatType_Text
                            , mGVAdapter.getItem(position).getOperaterName(), true, mGVAdapter.getItem(position));
                }
            }
        });
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
            if (mGVAdapter.getCount()==0) {
                setNotService();
            }
        } else if (id == Common.NET_SEED_USERCHAT_ID) {
            super.onStringResponse(data, response, id);
            if (adChatTypeText == ADChatType.ADChatType_Text
                    || adChatTypeText == ADChatType.ADChatType_ImageText) {
                blogdetailAddcommentEt.setText("");
            }
            updateChatMessage(0, "", -1);
        } else if (id == Common.NET_UP_PIC_ID) {

            HeadImgResultModel mHeadImgResultModel = mGson.fromJson(data, HeadImgResultModel.class);
            String str = mHeadImgResultModel.getBody().get(0).getUrl();
            updateChatMessage(-1, str, -1);

            seedMessage(str);

        } else if (id == Common.NET_UP_AUDIO_ID) {
            UpdateAudioResultModel mUpdateAudioResultModel = mGson.fromJson(data, UpdateAudioResultModel.class);
            String str = mUpdateAudioResultModel.getBody().get(0).getUrl();
            updateChatMessage(-1, str, -1);

            seedMessage(str);
        }
    }

    private void setNotService() {
        p2pchatServiceLl.setVisibility(View.GONE);
        p2pchatServiceOrKeyboardIv.setVisibility(View.GONE);
        p2pchatSendBt.setVisibility(View.VISIBLE);
    }

    private void seedMessage(String str) {
        temp.setContent(str);
        OkHttpUtils.postString().url(Common.Url_Seed_UserChat).tag(Common.NET_SEED_USERCHAT_ID)
                .id(Common.NET_SEED_USERCHAT_ID).content(mGson.toJson(temp)).mediaType(Common.JSON)
                .headers(MyBaseApplication.getApplication().getHeaderSeting())
                .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                .build().execute(new MyStringCallback(mContext, "发送中...", ShopIMActivity.this, false));
    }

    @Override
    public void onError(Call call, Exception e, int code, int id) {
        super.onError(call, e, code, id);
        updateChatMessage(1, "", -1);
    }

    private void updateChatMessage(int i, String str, long chatId) {
        if (i==-2) {//更新接收语音本地下载
            ChatMessageModel upMessage = mChatMessageModelDao.queryBuilder().where(
                    ChatMessageModelDao.Properties.ChatId.eq(chatId)).build().unique();
            upMessage.setAudioLocationUrl(str);
            mAdapter.setImMessageAudio(upMessage.getId(), str);
        } else {
            if (allMessage!=null) {
                ChatMessageModel upMessage = mChatMessageModelDao.queryBuilder().where(
                        ChatMessageModelDao.Properties.Id.eq(allMessage.getId())).build().unique();
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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(Common.NET_GET_USERCHAT_ID);
        OkHttpUtils.getInstance().cancelTag(Common.NET_SEED_USERCHAT_ID);
        OkHttpUtils.getInstance().cancelTag(Common.NET_UP_PIC_ID);
        OkHttpUtils.getInstance().cancelTag(Common.NET_UP_AUDIO_ID);
        EventBus.getDefault().unregister(this);
    }

    @OnClick({R.id.p2pchat_add_iv, R.id.blogdetail_addemol_iv, R.id.p2pchat_service_or_keyboard_iv, R.id.p2pchat_send_bt
            , R.id.p2pchat_add_picture_tv, R.id.p2pchat_add_camera_tv, R.id.p2pchat_add_record_tv, R.id.item_p2pchat_root_ll})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.item_p2pchat_root_ll:
                if (isIMOpen()) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
                }
                p2pchatServiceOrKeyboardIv.setImageResource(R.mipmap.adchat_input_action_icon_struct);
                blogdetailAddemolIv.setImageResource(R.mipmap.adchat_input_action_icon_face);
                p2pchatServiceLl.setVisibility(View.GONE);
                llFacechoose.setVisibility(View.GONE);
                p2pchatAddLl.setVisibility(View.GONE);
                handler.postDelayed(runnable, 200);
                break;
            case R.id.p2pchat_add_iv:
                if (p2pchatAddLl.getVisibility() == View.VISIBLE) {
                    p2pchatAddLl.setVisibility(View.GONE);
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(0, InputMethodManager.RESULT_SHOWN);
                } else {
                    if (isIMOpen()) {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
                    }
                    p2pchatAddLl.setVisibility(View.VISIBLE);
                }
                p2pchatServiceLl.setVisibility(View.GONE);
                p2pchatServiceOrKeyboardIv.setImageResource(R.mipmap.adchat_input_action_icon_struct);
                blogdetailAddemolIv.setImageResource(R.mipmap.adchat_input_action_icon_face);
                llFacechoose.setVisibility(View.GONE);
                handler.postDelayed(runnable, 200);
                break;
            case R.id.blogdetail_addemol_iv:
                if (llFacechoose.getVisibility() == View.GONE) {
                    if (isIMOpen()) {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
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
            case R.id.p2pchat_service_or_keyboard_iv:
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
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
                    }
                    p2pchatServiceLl.setVisibility(View.VISIBLE);
                    p2pchatServiceOrKeyboardIv.setImageResource(R.mipmap.adchat_input_action_icon_keyboard);
                }
                llFacechoose.setVisibility(View.GONE);
                p2pchatAddLl.setVisibility(View.GONE);
                blogdetailAddemolIv.setImageResource(R.mipmap.adchat_input_action_icon_face);
                handler.postDelayed(runnable, 200);
                break;
            case R.id.p2pchat_send_bt:
                buildMessage(ADChatType.ADChatType_Text, blogdetailAddcommentEt.getText().toString().trim()
                        , false, null);
                break;
            case R.id.p2pchat_add_picture_tv:
                Intent getAlbum = new Intent(Intent.ACTION_GET_CONTENT);
                getAlbum.setType("image/*");
                startActivityForResult(getAlbum, CHOOSE_BIG_PICTURE);
                p2pchatAddLl.setVisibility(View.GONE);
                break;
            case R.id.p2pchat_add_camera_tv:
                path = UiHelper.photo(mContext, path, TAKE_BIG_PICTURE);
                p2pchatAddLl.setVisibility(View.GONE);
                break;
            case R.id.p2pchat_add_record_tv:
                p2pchatAddLl.setVisibility(View.GONE);
                startActivityForResult(RecordingActivity.buildIntent(mContext, storeId), Common.TO_RECORDE);
                break;
        }
    }

    private void buildMessage(int adChatType, String content, boolean isService
            , GetChatServiceModel.BodyBean.MessageBeanBean.OperaterListBean bean) {
        adChatTypeText = adChatType;
        temp = new MessageModel();
        temp.setTargetType(ADChatTargetType.ADChatTargetType_Shop);
        temp.setTargetId(storeId);
        temp.setTargetName(name);
        temp.setTargetAvatar(MyBaseApplication.USERINFOR.getBody().getMemberAvatar());
        temp.setSendTime(System.currentTimeMillis());
        temp.setContent(content);
        temp.setType(adChatType);
        if (adChatType == ADChatType.ADChatType_Image) {
            OkHttpUtils.post().url(Common.Url_Up_File + UpChatFileType.ChatI)
                    .addParams("uploadType", "img").addHeader("cookie",MyBaseApplication.getApplication().getCookie())
                    .addFile("files0_name", StringUtils.getFileName(imageUri.getPath()), new File(content))
                    .id(Common.NET_UP_PIC_ID).tag(Common.NET_UP_PIC_ID)
                    .build().execute(new MyStringCallback(ShopIMActivity.this, "", this, false));
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
                seedMessage(bean.getOperaterName());
            } else {
                seedMessage(content);
            }
        } else if (adChatType == ADChatType.ADChatType_ImageText) {
        } else if (adChatType == ADChatType.ADChatType_Audio) {
            temp.setAudio_duration(mTime);
            OkHttpUtils.post().url(Common.Url_Up_File + UpChatFileType.ChatA)
                    .addHeader("cookie",MyBaseApplication.getApplication().getCookie())
                    .addFile("files0_name", StringUtils.getFileName(audioURL), new File(content))
                    .id(Common.NET_UP_AUDIO_ID).tag(Common.NET_UP_AUDIO_ID)
                    .build().execute(new MyStringCallback(ShopIMActivity.this, "", this, false));
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
                    , temp.getSendTime(), temp.getContent(), 0, "", temp.getTargetName(), avatar, adChatType);
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
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode, Bundle options) {
        super.startActivityForResult(intent, requestCode, options);
        overridePendingTransition(com.base.baselibs.R.anim.activity_bottom_in, com.base.baselibs.R.anim.activity_bottom_out);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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

                buildMessage(ADChatType.ADChatType_Audio, audioURL, false, null);
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
                    buildMessage(ADChatType.ADChatType_Image, temp.getPath(), false, null);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            p2pchatLv.getRefreshableView().smoothScrollToPosition(mAdapter.getCount());
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
                            , mAdapter.getItem(position).getEnterpriseId()
                            , mAdapter.getItem(position).getVideo_image_url()));
                } else if (mAdapter.getItem(position).getActionType()
                        ==EventActionType.EventActionType_Menu) {//商品列表  (菜单,点菜)
                } else if (mAdapter.getItem(position).getActionType()
                        ==EventActionType.EventActionType_Good) {//商品
                } else if (mAdapter.getItem(position).getActionType()
                        ==EventActionType.EventActionType_Coupon) {//优惠券
                }
                break;
            case R.id.item_p2pchat_text_headleft_iv:
                startActivity(StoreDetailActivity.buildIntent(mContext, storeId));
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
                positionAudio = position;
                playAudio(position, animationDrawable, true);
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
            if (mAnimationDrawable!=null) {
                mAnimationDrawable.stop();
                mAnimationDrawable.selectDrawable(0);
            }
        }
    }

    private void playAudio(int position, AnimationDrawable animationDrawable, boolean b) {
        if (mAdapter.getItem(position).getAudioLocationUrl()!=null
                && (new File(mAdapter.getItem(position).getAudioLocationUrl()).exists())) {
            try {
                if (mMediaPlayer.isPlaying()) {
                    mMediaPlayer.stop();
                    if (mAnimationDrawable!=null) {
                        mAnimationDrawable.stop();
                        mAnimationDrawable.selectDrawable(0);
                    }
                } else {
                    mAnimationDrawable = animationDrawable;
                    mMediaPlayer.reset();
                    mMediaPlayer.setDataSource(mAdapter.getItem(position).getAudioLocationUrl());
                    mMediaPlayer.prepare();
                    mMediaPlayer.start();
                    mAnimationDrawable.start();
                    mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        public void onCompletion(MediaPlayer mp) {
                            if (!mp.isPlaying()) {
                                mAnimationDrawable.stop();
                                mAnimationDrawable.selectDrawable(0);
                            }
                        }
                    });
                }

            } catch (Exception e) {
                showToast("播放失败");
                e.printStackTrace();
            }
        } else {
            String downUrl = Common.ImageUrl + mAdapter.getItem(position).getContent();
            if (b) {
                new DownloadAudioFile(ShopIMActivity.this, this).execute(downUrl);
            }
        }
    }

    @Override
    public void finish(String path) {
        updateChatMessage(-2, path, mAdapter.getItem(positionAudio).getChatId());
        playAudio(positionAudio, mAnimationDrawable, false);

    }

    @Override
    public void error() {
        showToast("播放失败");
    }
}
