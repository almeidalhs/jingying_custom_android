package com.atman.jixin.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.atman.jixin.model.bean.ChatMessageModel;
import com.atman.jixin.model.greendao.gen.ChatMessageModelDao;
import com.atman.jixin.model.iimp.ADChatTargetType;
import com.atman.jixin.model.iimp.ADChatType;
import com.atman.jixin.model.iimp.UpChatFileType;
import com.atman.jixin.model.response.GetChatServiceModel;
import com.atman.jixin.model.response.HeadImgResultModel;
import com.atman.jixin.model.response.MessageModel;
import com.atman.jixin.model.response.UpdateAudioResultModel;
import com.atman.jixin.model.updateChatMessageServiceEvent;
import com.atman.jixin.ui.base.MyBaseApplication;
import com.atman.jixin.utils.Common;
import com.base.baselibs.net.MyStringCallback;
import com.base.baselibs.net.httpCallBack;
import com.base.baselibs.util.LogUtils;
import com.base.baselibs.util.StringUtils;
import com.google.gson.Gson;
import com.tbl.okhttputils.OkHttpUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by tangbingliang on 16/11/8.
 */

public class SeedPersonalMessageService extends Service implements httpCallBack {

    private ChatMessageModel allTemp = new ChatMessageModel();
    private ChatMessageModelDao mChatMessageModelDao;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mChatMessageModelDao = MyBaseApplication.getApplication().getDaoSession().getChatMessageModelDao();
        checkSeedMessageList();

        return START_REDELIVER_INTENT;
    }

    private void checkSeedMessageList() {
        List<ChatMessageModel> messageList = mChatMessageModelDao.queryBuilder().where(
                ChatMessageModelDao.Properties.LoginId.eq(MyBaseApplication.USERINFOR.getBody().getAtmanUserId())
                , ChatMessageModelDao.Properties.SendStatus.eq(2)).orderAsc(ChatMessageModelDao.Properties.SendTime).build().list();
        if (messageList!=null && messageList.size()>0) {
            allTemp = messageList.get(0);
            if (messageList.get(0).getType() == ADChatType.ADChatType_Audio) {
                OkHttpUtils.post().url(Common.Url_Up_File + UpChatFileType.ChatA)
                        .addHeader("cookie",MyBaseApplication.getApplication().getCookie())
                        .addFile("files0_name", StringUtils.getFileName(messageList.get(0).getContent()), new File(messageList.get(0).getContent()))
                        .id(Common.NET_UP_AUDIO_ID).tag(Common.NET_UP_AUDIO_ID)
                        .build().execute(new MyStringCallback(this, "", this, false));
            } else if (messageList.get(0).getType() == ADChatType.ADChatType_Text) {
                seedMessage(messageList.get(0).getContent());
            } else if (messageList.get(0).getType() == ADChatType.ADChatType_Image) {
                OkHttpUtils.post().url(Common.Url_Up_File + UpChatFileType.ChatI)
                        .addParams("uploadType", "img").addHeader("cookie",MyBaseApplication.getApplication().getCookie())
                        .addFile("files0_name", StringUtils.getFileName(messageList.get(0).getContent()), new File(messageList.get(0).getContent()))
                        .id(Common.NET_UP_PIC_ID).tag(Common.NET_UP_PIC_ID)
                        .build().execute(new MyStringCallback(this, "", this, false));
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(Common.NET_UP_AUDIO_ID);
        OkHttpUtils.getInstance().cancelTag(Common.NET_UP_PIC_ID);
        OkHttpUtils.getInstance().cancelTag(Common.NET_SEED_USERCHAT_ID);
    }

    @Override
    public void clearData() {

    }

    @Override
    public void onBefore(Request request, int id) {

    }

    @Override
    public void onAfter(int id) {

    }

    @Override
    public void onError(Call call, Exception e, int code, int id) {
        EventBus.getDefault().post(new updateChatMessageServiceEvent(1, "", -1, allTemp.getId()));
    }

    @Override
    public void onStringResponse(String data, Response response, int id) {
        if (id == Common.NET_UP_AUDIO_ID) {
            UpdateAudioResultModel mUpdateAudioResultModel = new Gson().fromJson(data, UpdateAudioResultModel.class);
            String str = mUpdateAudioResultModel.getBody().get(0).getUrl();
            EventBus.getDefault().post(new updateChatMessageServiceEvent(-1, str, -1, allTemp.getId()));

            seedMessage(str);
        } else if (id == Common.NET_SEED_USERCHAT_ID) {
            EventBus.getDefault().post(new updateChatMessageServiceEvent(0, "", -1, allTemp.getId()));
            allTemp.setSendStatus(0);
            mChatMessageModelDao.update(allTemp);
            checkSeedMessageList();
        } else if (id == Common.NET_UP_PIC_ID) {
            HeadImgResultModel mHeadImgResultModel = new Gson().fromJson(data, HeadImgResultModel.class);
            String str = mHeadImgResultModel.getBody().get(0).getUrl();
            EventBus.getDefault().post(new updateChatMessageServiceEvent(-1, str, -1, allTemp.getId()));

            seedMessage(str);
        }
    }

    @Override
    public void onObjectResponse(Object data, Response response, int id) {

    }

    private void seedMessage(String str) {
        allTemp.setContent(str);
        LogUtils.e("new Gson().toJson(allTemp):"+new Gson().toJson(allTemp));
        OkHttpUtils.postString().url(Common.Url_Seed_UserChat).tag(Common.NET_SEED_USERCHAT_ID)
                .id(Common.NET_SEED_USERCHAT_ID).content(new Gson().toJson(allTemp)).mediaType(Common.JSON)
                .headers(MyBaseApplication.getApplication().getHeaderSeting())
                .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                .build().execute(new MyStringCallback(this, "发送中...", this, false));
    }
}
