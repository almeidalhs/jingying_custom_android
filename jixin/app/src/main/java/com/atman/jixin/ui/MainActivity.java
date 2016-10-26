package com.atman.jixin.ui;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.alan.codescanlibs.QrCodeActivity;
import com.atman.jixin.R;
import com.atman.jixin.adapter.MessageSessionListAdapter;
import com.atman.jixin.model.bean.ChatListModel;
import com.atman.jixin.model.greendao.gen.ChatListModelDao;
import com.atman.jixin.model.iimp.ADChatTargetType;
import com.atman.jixin.model.response.QRScanCodeModel;
import com.atman.jixin.ui.base.MyBaseActivity;
import com.atman.jixin.ui.base.MyBaseApplication;
import com.atman.jixin.ui.im.ShopIMActivity;
import com.atman.jixin.ui.personal.PersonalActivity;
import com.atman.jixin.ui.scancode.QRScanCodeActivity;
import com.atman.jixin.utils.Common;
import com.atman.jixin.utils.face.FaceConversionUtil;
import com.base.baselibs.iimp.AdapterInterface;
import com.base.baselibs.net.MyStringCallback;
import com.base.baselibs.util.PreferenceUtil;
import com.base.baselibs.widget.PromptDialog;
import com.base.baselibs.widget.ShapeImageView;
import com.igexin.sdk.PushManager;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tbl.okhttputils.OkHttpUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class MainActivity extends MyBaseActivity implements AdapterInterface {

    @Bind(R.id.main_head_img)
    ShapeImageView mainHeadImg;
    @Bind(R.id.main_bottom_ll)
    LinearLayout mainBottomLl;
    @Bind(R.id.message_listview)
    ListView messageListview;

    private Context mContext = MainActivity.this;
    private String headImge = "";
    private String str = "";
    private ChatListModelDao mChatListModelDao;
    private List<ChatListModel> mChatList;
    private MessageSessionListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSwipeBackEnable(false);

        if (isLogin()) {
            PushManager.getInstance().initialize(this.getApplicationContext());
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
        new Thread(new Runnable() {
            @Override
            public void run() {
                FaceConversionUtil.getInstace().getFileText(getApplication());
            }
        }).start();

        hideTitleBar();

        initListView();
    }

    private void initListView() {
        mAdapter = new MessageSessionListAdapter(mContext, this);
        messageListview.setAdapter(mAdapter);
        messageListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ChatListModel mChatListModel = mChatListModelDao.queryBuilder()
                        .where(ChatListModelDao.Properties.TargetId.eq(mAdapter.getItem(position).getTargetId())).build().unique();
                if (mChatListModel != null) {
                    mChatListModel.setUnreadNum(0);
                    mChatListModelDao.update(mChatListModel);
                    mAdapter.clearUnreadNum(position);
                    if (mAdapter.getItem(position).getTargetType() == ADChatTargetType.ADChatTargetType_Shop) {
                        startActivity(ShopIMActivity.buildIntent(mContext, mAdapter.getItem(position).getTargetId()
                                , mAdapter.getItem(position).getTargetName(), mAdapter.getItem(position).getTargetAvatar(), true));
                    }
                }
            }
        });
        messageListview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, final long id) {
                PromptDialog.Builder builder = new PromptDialog.Builder(mContext);
                builder.setMessage("您确定要将该消息删除吗？");
                builder.setPositiveButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
//                        mImMessageDelete = mImMessageDao.queryBuilder().where(ImMessageDao.Properties.ChatId.eq(mAdapter.getItem(position).getUserId()), ImMessageDao.Properties.LoginUserId.eq(
//                                String.valueOf(MyBaseApplication.getApplication().mGetMyUserIndexModel.getBody().getUserDetailBean().getUserId()))).build().list();
//                        for (ImMessage imMessageDelete : mImMessageDelete) {
//                            mImMessageDao.delete(imMessageDelete);
//                        }

                        ChatListModel mDelete = mChatListModelDao.queryBuilder().where(ChatListModelDao.Properties.TargetId.eq(mAdapter.getItem(position).getTargetId())).build().unique();
                        if (mDelete != null) {
                            mChatListModelDao.delete(mDelete);
                            mAdapter.deleteItemById(position);
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
                return true;
            }
        });
    }

    @Override
    public void doInitBaseHttp() {
        super.doInitBaseHttp();
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

        setUnreadMessageNum();
    }

    private void setUnreadMessageNum() {
        if (mChatList!=null) {
            mChatList.clear();
        }
        if (mAdapter!=null) {
            mAdapter.clearData();
        }
        mChatList = mChatListModelDao.queryBuilder().build().list();
        if (mChatList!=null) {
            mAdapter.addBody(mChatList);
        }
    }

    @Override
    public void onStringResponse(String data, Response response, int id) {
        super.onStringResponse(data, response, id);
        if (id == Common.NET_QR_CODE_ID) {
            QRScanCodeModel mQRScanCodeModel = mGson.fromJson(data, QRScanCodeModel.class);
            if (mQRScanCodeModel.getResult().equals("1")) {
                if (mQRScanCodeModel.getBody().getType() == 1) {
                    startActivity(ShopIMActivity.buildIntent(mContext, mQRScanCodeModel, false));
                } else {
                    startActivity(QRScanCodeActivity.buildIntent(mContext, str));
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(Common.NET_QR_CODE_ID);
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
            startActivity(QRScanCodeActivity.buildIntent(mContext, str));
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

    }
}
