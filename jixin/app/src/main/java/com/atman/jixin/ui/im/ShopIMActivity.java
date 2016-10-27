package com.atman.jixin.ui.im;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
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
import com.atman.jixin.model.bean.ChatListModel;
import com.atman.jixin.model.bean.ChatMessageModel;
import com.atman.jixin.model.greendao.gen.ChatListModelDao;
import com.atman.jixin.model.greendao.gen.ChatMessageModelDao;
import com.atman.jixin.model.iimp.ADChatTargetType;
import com.atman.jixin.model.iimp.ADChatType;
import com.atman.jixin.model.response.GetChatServiceModel;
import com.atman.jixin.model.response.MessageModel;
import com.atman.jixin.model.response.QRScanCodeModel;
import com.atman.jixin.ui.base.MyBaseActivity;
import com.atman.jixin.ui.base.MyBaseApplication;
import com.atman.jixin.ui.shop.MemberCenterActivity;
import com.atman.jixin.utils.Common;
import com.atman.jixin.utils.face.FaceRelativeLayout;
import com.base.baselibs.iimp.AdapterInterface;
import com.base.baselibs.iimp.EditCheckBack;
import com.base.baselibs.iimp.MyTextWatcherTwo;
import com.base.baselibs.net.MyStringCallback;
import com.base.baselibs.util.LogUtils;
import com.base.baselibs.widget.MyCleanEditText;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.tbl.okhttputils.OkHttpUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by tangbingliang on 16/10/21.
 */

public class ShopIMActivity extends MyBaseActivity
        implements AdapterInterface, EditCheckBack, P2PChatAdapter.P2PAdapterInter {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopim);
        ButterKnife.bind(this);
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

                Map<String, String> p = new HashMap<>();

                OkHttpUtils.postString().url(Common.Url_Seed_UserChat).tag(Common.NET_SEED_USERCHAT_ID)
                        .id(Common.NET_SEED_USERCHAT_ID).content(mGson.toJson(p)).mediaType(Common.JSON)
                        .headers(MyBaseApplication.getApplication().getHeaderSeting())
                        .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                        .build().execute(new MyStringCallback(mContext, "", ShopIMActivity.this, false));
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
        super.onStringResponse(data, response, id);
        if (id == Common.NET_GET_USERCHAT_ID) {
            mGetChatServiceModel = mGson.fromJson(data, GetChatServiceModel.class);

            mGVAdapter.updateListView(mGetChatServiceModel.getBody().getMessageBean().getOperaterList());
        } else if (id == Common.NET_SEED_USERCHAT_ID) {
            if (adChatTypeText == ADChatType.ADChatType_Text
                    || adChatTypeText == ADChatType.ADChatType_ImageText) {
                blogdetailAddcommentEt.setText("");
            }
            updateChatMessage(0);
        }
    }

    @Override
    public void onError(Call call, Exception e, int code, int id) {
        super.onError(call, e, code, id);
        updateChatMessage(1);
    }

    private void updateChatMessage(int i) {
        if (allMessage!=null) {
            ChatMessageModel upMessage = mChatMessageModelDao.queryBuilder().where(
                    ChatMessageModelDao.Properties.Id.eq(allMessage.getId())).build().unique();
            if (upMessage!=null) {
                upMessage.setSendStatus(i);
                mChatMessageModelDao.update(upMessage);
                mAdapter.setImMessageStatus(upMessage.getId(), i);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(Common.NET_GET_USERCHAT_ID);
        OkHttpUtils.getInstance().cancelTag(Common.NET_SEED_USERCHAT_ID);
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
                seedMessage(ADChatType.ADChatType_Text);
                break;
            case R.id.p2pchat_add_picture_tv:
                break;
            case R.id.p2pchat_add_camera_tv:
                break;
            case R.id.p2pchat_add_record_tv:
                p2pchatAddLl.setVisibility(View.GONE);
                startActivityForResult(RecordingActivity.buildIntent(mContext, storeId), Common.TO_RECORDE);
                break;
        }
    }

    private void seedMessage(int adChatType_text) {
        String str = "";
        adChatTypeText = adChatType_text;
        MessageModel temp = new MessageModel();
        temp.setTargetType(ADChatTargetType.ADChatTargetType_Shop);
        temp.setTargetId(storeId);
        temp.setTargetName(name);
        temp.setTargetAvatar(MyBaseApplication.USERINFOR.getBody().getMemberAvatar());
        temp.setSendTime(System.currentTimeMillis());
        if (adChatType_text == ADChatType.ADChatType_Text) {
            str = blogdetailAddcommentEt.getText().toString().trim();
        } else if (adChatType_text == ADChatType.ADChatType_Image
                || adChatType_text == ADChatType.ADChatType_ImageText) {
            str = "[图片]";
        } else if (adChatType_text == ADChatType.ADChatType_Audio) {
            str = "[语音]";
        } else if (adChatType_text == ADChatType.ADChatType_Video) {
            str = "[视屏]";
        }
        if (str.isEmpty()) {
            return;
        }
        temp.setContent(str);
        temp.setType(adChatType_text);
        OkHttpUtils.postString().url(Common.Url_Seed_UserChat).tag(Common.NET_SEED_USERCHAT_ID)
                .id(Common.NET_SEED_USERCHAT_ID).content(mGson.toJson(temp)).mediaType(Common.JSON)
                .headers(MyBaseApplication.getApplication().getHeaderSeting())
                .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                .build().execute(new MyStringCallback(mContext, "发送中...", ShopIMActivity.this, true));

        //添加聊天列表
        ChatListModel mChatListModel= mChatListModelDao.queryBuilder().where(ChatListModelDao.Properties.TargetId.eq(storeId)
                , ChatListModelDao.Properties.LoginId.eq(MyBaseApplication.USERINFOR.getBody().getAtmanUserId())).build().unique();
        if (mChatListModel==null) {
            if (avatar.isEmpty() || temp.getTargetName().isEmpty()) {
                return;
            }
            ChatListModel tempChat = new ChatListModel(null, temp.getTargetId()
                    , MyBaseApplication.USERINFOR.getBody().getAtmanUserId(), temp.getTargetType()
                    , temp.getSendTime(), temp.getContent(), 0, "", temp.getTargetName(), avatar);
            mChatListModelDao.save(tempChat);
        } else {
            mChatListModel.setSendTime(temp.getSendTime());
            mChatListModel.setUnreadNum(0);
            mChatListModel.setContent(temp.getContent());
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
            LogUtils.e("ShopIMActivity_URL:"+data.getStringExtra("url"));
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
        if (TextUtils.isEmpty(blogdetailAddcommentEt.getText().toString())) {
            p2pchatSendBt.setVisibility(View.GONE);
            p2pchatServiceOrKeyboardIv.setVisibility(View.VISIBLE);
        } else {
            p2pchatSendBt.setVisibility(View.VISIBLE);
            p2pchatServiceOrKeyboardIv.setVisibility(View.GONE);
        }
    }

    @Override
    public void onItem(View v, int position) {

    }

    @Override
    public void onItemAudio(View v, int position, AnimationDrawable animationDrawable) {

    }
}
