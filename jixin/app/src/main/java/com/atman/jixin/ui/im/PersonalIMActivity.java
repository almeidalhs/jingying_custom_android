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
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.atman.jixin.R;
import com.atman.jixin.adapter.P2PChatAdapter;
import com.atman.jixin.model.MessageEvent;
import com.atman.jixin.model.bean.ChatListModel;
import com.atman.jixin.model.bean.ChatMessageModel;
import com.atman.jixin.model.greendao.gen.ChatListModelDao;
import com.atman.jixin.model.greendao.gen.ChatMessageModelDao;
import com.atman.jixin.model.iimp.ADChatTargetType;
import com.atman.jixin.model.iimp.ADChatType;
import com.atman.jixin.model.iimp.EventActionType;
import com.atman.jixin.model.response.GetChatServiceModel;
import com.atman.jixin.model.response.GetMessageModel;
import com.atman.jixin.model.response.MessageModel;
import com.atman.jixin.model.response.QRScanCodeModel;
import com.atman.jixin.model.updateChatMessageServiceEvent;
import com.atman.jixin.service.SeedPersonalMessageService;
import com.atman.jixin.ui.PictureBrowsingActivity;
import com.atman.jixin.ui.base.MyBaseActivity;
import com.atman.jixin.ui.base.MyBaseApplication;
import com.atman.jixin.ui.im.chatui.CompanyIntroductionActivity;
import com.atman.jixin.ui.im.chatui.MenuPreviewActivity;
import com.atman.jixin.utils.BitmapTools;
import com.atman.jixin.utils.Common;
import com.atman.jixin.utils.MyTools;
import com.atman.jixin.utils.UiHelper;
import com.atman.jixin.widget.downfile.DownloadAudioFile;
import com.base.baselibs.iimp.AdapterInterface;
import com.base.baselibs.util.LogUtils;
import com.base.baselibs.widget.MyCleanEditText;
import com.base.baselibs.widget.localalbum.common.ImageUtils;
import com.base.baselibs.widget.localalbum.common.LocalImageHelper;
import com.base.baselibs.widget.localalbum.ui.LocalAlbum;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

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

public class PersonalIMActivity extends MyBaseActivity implements AdapterInterface
        , P2PChatAdapter.P2PAdapterInter, DownloadAudioFile.onDownInterface {

    @Bind(R.id.p2pchat_lv)
    PullToRefreshListView p2pchatLv;
    @Bind(R.id.p2pchat_add_iv)
    ImageView p2pchatAddIv;
    @Bind(R.id.blogdetail_addcomment_et)
    MyCleanEditText blogdetailAddcommentEt;
    @Bind(R.id.blogdetail_addemol_iv)
    ImageView blogdetailAddemolIv;
    @Bind(R.id.p2pchat_send_bt)
    Button p2pchatSendBt;
    @Bind(R.id.ll1)
    LinearLayout ll1;
    @Bind(R.id.p2pchat_add_picture_tv)
    TextView p2pchatAddPictureTv;
    @Bind(R.id.p2pchat_add_camera_tv)
    TextView p2pchatAddCameraTv;
    @Bind(R.id.p2pchat_add_record_tv)
    TextView p2pchatAddRecordTv;
    @Bind(R.id.p2pchat_add_ll)
    LinearLayout p2pchatAddLl;
    @Bind(R.id.vp_contains)
    ViewPager vpContains;
    @Bind(R.id.iv_image)
    LinearLayout ivImage;
    @Bind(R.id.ll_facechoose)
    RelativeLayout llFacechoose;
    @Bind(R.id.FaceRelativeLayout)
    com.atman.jixin.utils.face.FaceRelativeLayout FaceRelativeLayout;
    @Bind(R.id.item_p2pchat_root_ll)
    LinearLayout itemP2pchatRootLl;
    private QRScanCodeModel mQRScanCodeModel = new QRScanCodeModel();
    private Context mContext = PersonalIMActivity.this;
    private int adChatTypeText;

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

    private long persionId;
    private String name;
    private String avatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personalim);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
    }

    public static Intent buildIntent(Context context, long persionId, String name, String avatar) {
        Intent intent = new Intent(context, PersonalIMActivity.class);
        intent.putExtra("persionId", persionId);
        intent.putExtra("name", name);
        intent.putExtra("avatar", avatar);
        return intent;
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);

        name = getIntent().getStringExtra("name");
        avatar = getIntent().getStringExtra("avatar");
        persionId = getIntent().getLongExtra("persionId", -1);
        LogUtils.e("name:" + name + ",persionId:" + persionId + ",avatar:" + avatar);
        setBarTitleTx(name);

        mChatListModelDao = MyBaseApplication.getApplication().getDaoSession().getChatListModelDao();
        mChatMessageModelDao = MyBaseApplication.getApplication().getDaoSession().getChatMessageModelDao();

        allMessageList = mChatMessageModelDao.queryBuilder().where(ChatMessageModelDao.Properties.TargetId.eq(persionId)
                , ChatMessageModelDao.Properties.LoginId.eq(MyBaseApplication.USERINFOR.getBody().getAtmanUserId())).build().list();

        initListView();
    }

    @Subscribe(threadMode = ThreadMode.MAIN) //第2步:注册一个在后台线程执行的方法,用于接收事件
    public void onUserEvent(MessageEvent event) {//参数必须是ClassEvent类型, 否则不会调用此方法
        GetMessageModel mGetMessageModel = event.mGetMessageModel;
        ChatMessageModel mChatMessageModel = event.mChatMessageModel;
        if (mGetMessageModel.getContent().getTargetId() == persionId) {
            mAdapter.addImMessageDao(mChatMessageModel);
            ChatListModel mChatListModel = mChatListModelDao.queryBuilder()
                    .where(ChatListModelDao.Properties.TargetId.eq(mGetMessageModel.getContent().getTargetId())).build().unique();
            if (mChatListModel != null) {
                mChatListModel.setUnreadNum(0);
                mChatListModelDao.update(mChatListModel);
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN) //第2步:注册一个在后台线程执行的方法,用于接收事件
    public void onUserEvent(updateChatMessageServiceEvent event) {//参数必须是ClassEvent类型, 否则不会调用此方法
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
    }

    @Override
    public void onError(Call call, Exception e, int code, int id) {
        super.onError(call, e, code, id);
        updateChatMessage(1, "", -1, -1);
    }

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
        EventBus.getDefault().unregister(this);
    }

    @OnClick({R.id.p2pchat_add_iv, R.id.blogdetail_addemol_iv, R.id.p2pchat_send_bt, R.id.p2pchat_add_picture_tv
            , R.id.p2pchat_add_camera_tv, R.id.p2pchat_add_record_tv, R.id.item_p2pchat_root_ll})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.item_p2pchat_root_ll:
                if (isIMOpen()) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
                }
                blogdetailAddemolIv.setImageResource(R.mipmap.adchat_input_action_icon_face);
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
                p2pchatAddLl.setVisibility(View.GONE);
                handler.postDelayed(runnable, 200);
                break;
            case R.id.p2pchat_send_bt:
                if (blogdetailAddcommentEt.getText().toString().trim().isEmpty()) {
                    return;
                }
                buildMessage(ADChatType.ADChatType_Text, blogdetailAddcommentEt.getText().toString().trim()
                        , false, null);
                blogdetailAddcommentEt.setText("");
                break;
            case R.id.p2pchat_add_picture_tv:
//                Intent getAlbum = new Intent(Intent.ACTION_GET_CONTENT);
//                getAlbum.setType("image/*");
//                startActivityForResult(getAlbum, CHOOSE_BIG_PICTURE);
//                p2pchatAddLl.setVisibility(View.GONE);
                Intent intent = new Intent(mContext, LocalAlbum.class);
                startActivityForResult(intent, ImageUtils.REQUEST_CODE_GETIMAGE_BYCROP);
                break;
            case R.id.p2pchat_add_camera_tv:
                path = UiHelper.photo(mContext, path, TAKE_BIG_PICTURE);
                p2pchatAddLl.setVisibility(View.GONE);
                break;
            case R.id.p2pchat_add_record_tv:
                p2pchatAddLl.setVisibility(View.GONE);
                startActivityForResult(RecordingActivity.buildIntent(mContext, persionId), Common.TO_RECORDE);
                break;
        }
    }

    private void buildMessage(int adChatType, String content, boolean isService
            , GetChatServiceModel.BodyBean.MessageBeanBean.OperaterListBean bean) {
        adChatTypeText = adChatType;
        temp = new MessageModel();
        temp.setTargetType(ADChatTargetType.ADChatTargetType_User);
        temp.setTargetId(persionId);
        temp.setTargetName(name);
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
                if (bean.getIdentifyChangeNotice() != null) {
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
        ChatListModel mChatListModel = mChatListModelDao.queryBuilder().where(ChatListModelDao.Properties.TargetId.eq(persionId)
                , ChatListModelDao.Properties.LoginId.eq(MyBaseApplication.USERINFOR.getBody().getAtmanUserId())).build().unique();
        if (mChatListModel == null) {
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
        tempMessage.setTargetId(temp.getTargetId());
        tempMessage.setLoginId(MyBaseApplication.USERINFOR.getBody().getAtmanUserId());
        tempMessage.setType(temp.getType());
        tempMessage.setTargetType(temp.getTargetType());
        tempMessage.setTargetName(temp.getTargetName());
        tempMessage.setTargetAvatar(avatar);
        tempMessage.setSendTime(temp.getSendTime());
        tempMessage.setContent(temp.getContent());

        if (temp.getVideo_image_url() != null) {
            tempMessage.setVideo_image_url(temp.getVideo_image_url());
        }

        if (temp.getAudio_duration() > 0) {
            tempMessage.setAudio_duration(temp.getAudio_duration());
            tempMessage.setAudioLocationUrl(audioURL);
        }

        if (temp.getImageT_back() != null) {
            tempMessage.setImageT_back(temp.getImageT_back());
            tempMessage.setImageT_icon(temp.getImageT_icon());
            tempMessage.setImageT_title(temp.getImageT_title());
        }

        if (temp.getEventAction() != null) {
            tempMessage.setActionType(temp.getEventAction().getActionType());
        }

        if (temp.getOperaterList() != null && temp.getOperaterList().size() >= 1) {
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
            Intent intent = new Intent(mContext, SeedPersonalMessageService.class);
            startService(intent);
        }
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode, Bundle options) {
        super.startActivityForResult(intent, requestCode, options);
        overridePendingTransition(com.base.baselibs.R.anim.activity_bottom_in, com.base.baselibs.R.anim.activity_bottom_out);
    }

    private List<LocalImageHelper.LocalFile> files = new ArrayList<>();
    private int fileID = 0;
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

                buildMessage(ADChatType.ADChatType_Audio, audioURL, false, null);
            }
        } else {
            if (requestCode == CHOOSE_BIG_PICTURE) {//选择照片
                imageUri = data.getData();
            } else if (requestCode == TAKE_BIG_PICTURE) {
                imageUri = Uri.parse("file:///" + path);
            }
            if (imageUri != null) {
                LogUtils.e("imageUri:" + imageUri);
                try {
                    File temp = BitmapTools.revitionImage(mContext, imageUri);
                    if (temp == null) {
                        showToast("发送失败");
                        return;
                    }
                    buildMessage(ADChatType.ADChatType_Image, temp.getPath(), false, null);
                    Intent intent = new Intent(mContext, SeedPersonalMessageService.class);
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
                buildMessage(ADChatType.ADChatType_Image, temp.getPath(),false, null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        LocalImageHelper.getInstance().setCurrentSize(0);
        Intent intent = new Intent(mContext, SeedPersonalMessageService.class);
        startService(intent);
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
    public void onItem(View v, int position) {
        switch (v.getId()) {
            case R.id.item_p2pchat_root_Rl:
                if (isIMOpen()) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0); //强制隐藏键盘
                }
                p2pchatSendBt.setVisibility(View.GONE);
                blogdetailAddcommentEt.setVisibility(View.VISIBLE);
                llFacechoose.setVisibility(View.GONE);
                p2pchatAddLl.setVisibility(View.GONE);
                handler.postDelayed(runnable, 200);
                break;
            case R.id.item_p2pchat_imagetext_left_rl:
                if (mAdapter.getItem(position).getActionType()
                        == EventActionType.EventActionType_GoodList) {//菜单预览
                    startActivity(MenuPreviewActivity.buildIntent(mContext
                            , mAdapter.getItem(position).getImageT_title()
                            , mAdapter.getItem(position).getStoreId()));
                } else if (mAdapter.getItem(position).getActionType()
                        == EventActionType.EventActionType_Enterprise) {//企业介绍
                    startActivity(CompanyIntroductionActivity.buildIntent(mContext
                            , mAdapter.getItem(position).getImageT_title()
                            , mAdapter.getItem(position).getEnterpriseId()));
                } else if (mAdapter.getItem(position).getActionType()
                        == EventActionType.EventActionType_Menu) {//商品列表  (菜单,点菜)
                } else if (mAdapter.getItem(position).getActionType()
                        == EventActionType.EventActionType_Good) {//商品
                } else if (mAdapter.getItem(position).getActionType()
                        == EventActionType.EventActionType_Coupon) {//优惠券
                }
                break;
            case R.id.item_p2pchat_text_headleft_iv:
                startActivity(TAPersonalInformationActivity.buildIntent(mContext, persionId, true));
                break;
            case R.id.item_p2pchat_image_left_iv:
            case R.id.item_p2pchat_image_right_iv:
                String imagePath = "";
                int n = 0;
                for (int i = 0, j = 0; i < mAdapter.getCount(); i++) {
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
        if (mAdapter.getItem(position).getAudioLocationUrl() != null
                && (new File(mAdapter.getItem(position).getAudioLocationUrl()).exists())) {
            try {
                if (mMediaPlayer.isPlaying()) {
                    mMediaPlayer.stop();
                    if (mAnimationDrawable != null) {
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
                            mAnimationDrawable.stop();
                            mAnimationDrawable.selectDrawable(0);
                        }
                    });
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            String downUrl = Common.ImageUrl + mAdapter.getItem(position).getContent();
            if (b) {
                new DownloadAudioFile(PersonalIMActivity.this, this).execute(downUrl);
            }
        }
    }

    @Override
    public void finish(String path) {
        updateChatMessage(-2, path, mAdapter.getItem(positionAudio).getChatId(), -1);
        playAudio(positionAudio, mAnimationDrawable, false);

    }

    @Override
    public void error() {
        showToast("播放失败");
    }
}
