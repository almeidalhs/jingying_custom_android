package com.atman.jixin.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.atman.jixin.model.MessageEvent;
import com.atman.jixin.model.bean.ChatListModel;
import com.atman.jixin.model.bean.ChatMessageModel;
import com.atman.jixin.model.greendao.gen.ChatListModelDao;
import com.atman.jixin.model.greendao.gen.ChatMessageModelDao;
import com.atman.jixin.model.response.GetMessageModel;
import com.atman.jixin.ui.base.MyBaseApplication;
import com.base.baselibs.util.LogUtils;
import com.google.gson.Gson;
import com.igexin.sdk.PushConsts;
import com.igexin.sdk.PushManager;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by tangbingliang on 16/10/25.
 */

public class GeTuiPushReceiver extends BroadcastReceiver {

    /**
     * 应用未启动, 个推 service已经被唤醒,保存在该时间段内离线消息(此时 GetuiSdkDemoActivity.tLogView == null)
     */
    public static StringBuilder payloadData = new StringBuilder();
    private ChatListModelDao mChatListModelDao;
    private ChatMessageModelDao mChatMessageModelDao;

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        LogUtils.e("GetuiSdkDemo===onReceive() action=" + bundle.getInt("action"));
        if (mChatListModelDao==null) {
            mChatListModelDao = MyBaseApplication.getApplication().getDaoSession().getChatListModelDao();
            mChatMessageModelDao = MyBaseApplication.getApplication().getDaoSession().getChatMessageModelDao();
        }

        switch (bundle.getInt(PushConsts.CMD_ACTION)) {
            case PushConsts.GET_MSG_DATA:
                // 获取透传数据
                // String appid = bundle.getString("appid");
                byte[] payload = bundle.getByteArray("payload");

                String taskid = bundle.getString("taskid");
                String messageid = bundle.getString("messageid");

                // smartPush第三方回执调用接口，actionid范围为90000-90999，可根据业务场景执行
                boolean result = PushManager.getInstance().sendFeedbackMessage(context, taskid, messageid, 90001);
                System.out.println("第三方回执接口调用" + (result ? "成功" : "失败"));

                if (payload != null) {
                    String data = new String(payload);

                    LogUtils.e("GetuiSdkDemo===receiver payload : " + data);

                    payloadData.append(data);
                    payloadData.append("\n");

                    LogUtils.e("data:"+data);
                    GetMessageModel mGetMessageModel = new Gson().fromJson(data, GetMessageModel.class);
                    saveGetMessage(mGetMessageModel);
                }
                break;

            case PushConsts.GET_CLIENTID:
                // 获取ClientID(CID)
                // 第三方应用需要将CID上传到第三方服务器，并且将当前用户帐号和CID进行关联，以便日后通过用户帐号查找CID进行消息推送
                String cid = bundle.getString("clientid");
                LogUtils.e("clientid:"+cid);
                break;
            case PushConsts.GET_SDKONLINESTATE:
                boolean online = bundle.getBoolean("onlineState");
                LogUtils.e("GetuiSdkDemo===online = " + online);
                break;

            case PushConsts.SET_TAG_RESULT:
                String sn = bundle.getString("sn");
                String code = bundle.getString("code");

                String text = "设置标签失败, 未知异常";
                switch (Integer.valueOf(code)) {
                    case PushConsts.SETTAG_SUCCESS:
                        text = "设置标签成功";
                        break;

                    case PushConsts.SETTAG_ERROR_COUNT:
                        text = "设置标签失败, tag数量过大, 最大不能超过200个";
                        break;

                    case PushConsts.SETTAG_ERROR_FREQUENCY:
                        text = "设置标签失败, 频率过快, 两次间隔应大于1s";
                        break;

                    case PushConsts.SETTAG_ERROR_REPEAT:
                        text = "设置标签失败, 标签重复";
                        break;

                    case PushConsts.SETTAG_ERROR_UNBIND:
                        text = "设置标签失败, 服务未初始化成功";
                        break;

                    case PushConsts.SETTAG_ERROR_EXCEPTION:
                        text = "设置标签失败, 未知异常";
                        break;

                    case PushConsts.SETTAG_ERROR_NULL:
                        text = "设置标签失败, tag 为空";
                        break;

                    case PushConsts.SETTAG_NOTONLINE:
                        text = "还未登陆成功";
                        break;

                    case PushConsts.SETTAG_IN_BLACKLIST:
                        text = "该应用已经在黑名单中,请联系售后支持!";
                        break;

                    case PushConsts.SETTAG_NUM_EXCEED:
                        text = "已存 tag 超过限制";
                        break;

                    default:
                        break;
                }

                LogUtils.e("GetuiSdkDemo===settag result sn = " + sn + ", code = " + code);
                LogUtils.e("GetuiSdkDemo===settag result sn = " + text);
                break;
            case PushConsts.THIRDPART_FEEDBACK:
                /*
                 * String appid = bundle.getString("appid"); String taskid =
                 * bundle.getString("taskid"); String actionid = bundle.getString("actionid");
                 * String result = bundle.getString("result"); long timestamp =
                 * bundle.getLong("timestamp");
                 *
                 * Log.d("GetuiSdkDemo", "appid = " + appid); Log.d("GetuiSdkDemo", "taskid = " +
                 * taskid); Log.d("GetuiSdkDemo", "actionid = " + actionid); Log.d("GetuiSdkDemo",
                 * "result = " + result); Log.d("GetuiSdkDemo", "timestamp = " + timestamp);
                 */
                break;

            default:
                break;
        }
    }

    private void saveGetMessage(GetMessageModel mGetMessageModel) {
        GetMessageModel.ContentBean temp = mGetMessageModel.getContent();
        //添加聊天列表
        ChatListModel mChatListModel= mChatListModelDao.queryBuilder()
                .where(ChatListModelDao.Properties.TargetId.eq(temp.getTargetId())
                , ChatListModelDao.Properties.LoginId.eq(MyBaseApplication.USERINFOR.getBody().getAtmanUserId())).build().unique();
        if (mChatListModel==null) {
            ChatListModel tempChat = new ChatListModel(null, temp.getTargetId()
                    , MyBaseApplication.USERINFOR.getBody().getAtmanUserId(), temp.getTargetType()
                    , temp.getSendTime(), temp.getContent(), 0, "", temp.getTargetName()
                    , temp.getTargetAvatar(), temp.getType());
            mChatListModelDao.save(tempChat);
        } else {
            mChatListModel.setSendTime(temp.getSendTime());
            mChatListModel.setUnreadNum(mChatListModel.getUnreadNum()+1);
            mChatListModel.setContent(temp.getContent());
            mChatListModel.setType(temp.getType());
            mChatListModelDao.update(mChatListModel);
        }
        //添加聊天记录
        ChatMessageModel tempMessage = new ChatMessageModel();
        tempMessage.setId(null);
        tempMessage.setChatId(temp.getChatId());
        tempMessage.setTargetId(temp.getTargetId());
        tempMessage.setLoginId(MyBaseApplication.USERINFOR.getBody().getAtmanUserId());
        tempMessage.setType(temp.getType());
        tempMessage.setTargetType(temp.getTargetType());
        tempMessage.setTargetName(temp.getTargetName());
        tempMessage.setTargetAvatar(temp.getTargetAvatar());
        tempMessage.setSendTime(temp.getSendTime());
        tempMessage.setContent(temp.getContent());

//        if (temp.getVideo_image_url()!=null) {
//            tempMessage.setVideo_image_url(temp.getVideo_image_url());
//        }
//
        if (temp.getAudio_duration()>0) {
            tempMessage.setAudio_duration(temp.getAudio_duration());
//            tempMessage.setAudioLocationUrl(audioURL);
        }
//
        if (temp.getImageT_back()!=null) {
            tempMessage.setImageT_back(temp.getImageT_back());
            tempMessage.setImageT_icon(temp.getImageT_icon());
            tempMessage.setImageT_title(temp.getImageT_title());
        }
//
        if (temp.getEventAction()!=null) {
            tempMessage.setActionType(temp.getEventAction().getActionType());
        }
//
//        if (temp.getOperaterList()!=null && temp.getOperaterList().size()>=1) {
//            tempMessage.setOperaterId(temp.getOperaterList().get(0).getOperaterId());
//            tempMessage.setOperaterName(temp.getOperaterList().get(0).getOperaterName());
//            tempMessage.setOperaterType(temp.getOperaterList().get(0).getOperaterType());
//        }

        tempMessage.setReaded(1);
        tempMessage.setSendStatus(0);
        tempMessage.setSelfSend(false);
        mChatMessageModelDao.save(tempMessage);
        EventBus.getDefault().post(new MessageEvent(mGetMessageModel));
    }
}
