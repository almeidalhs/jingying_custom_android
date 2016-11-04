package com.atman.jixin.receiver;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.atman.jixin.R;
import com.atman.jixin.model.MessageEvent;
import com.atman.jixin.model.bean.ChatListModel;
import com.atman.jixin.model.bean.ChatMessageModel;
import com.atman.jixin.model.greendao.gen.ChatListModelDao;
import com.atman.jixin.model.greendao.gen.ChatMessageModelDao;
import com.atman.jixin.model.iimp.ADChatType;
import com.atman.jixin.model.response.GetMessageModel;
import com.atman.jixin.ui.MainActivity;
import com.atman.jixin.ui.base.MyBaseApplication;
import com.atman.jixin.widget.ResidentNotificationHelper;
import com.base.baselibs.util.LogUtils;
import com.google.gson.Gson;
import com.igexin.sdk.PushConsts;
import com.igexin.sdk.PushManager;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

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
                    LogUtils.e("mGetMessageModel.getContent().getChatId():"+mGetMessageModel.getContent().getChatId());
                    saveGetMessage(context, mGetMessageModel);
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

    private void saveGetMessage(Context context, GetMessageModel mGetMessageModel) {

        GetMessageModel.ContentBean temp = mGetMessageModel.getContent();
        String content = temp.getTargetName()+" : ";
        String sessionContent = "";
        if (temp.getType() == ADChatType.ADChatType_Text) {
            content += temp.getContent();
            sessionContent = temp.getContent();
        } else if (temp.getType() == ADChatType.ADChatType_Image) {
            content += "[图片]";
            sessionContent = "[图片]";
        } else if (temp.getType() == ADChatType.ADChatType_ImageText) {
            content += temp.getImageT_title();
            sessionContent = temp.getImageT_title();
        } else if (temp.getType() == ADChatType.ADChatType_Audio) {
            content += "[语音]";
            sessionContent = "[语音]";
        } else if (temp.getType() == ADChatType.ADChatType_Video) {
            sessionContent = "[视频]";
        }

        LogUtils.e("temp.getChatId():"+temp.getChatId());
        //添加聊天列表
        ChatListModel mChatListModel= mChatListModelDao.queryBuilder()
                .where(ChatListModelDao.Properties.TargetId.eq(temp.getTargetId())
                , ChatListModelDao.Properties.LoginId.eq(MyBaseApplication.USERINFOR.getBody().getAtmanUserId())).build().unique();
        if (mChatListModel==null) {
            ChatListModel tempChat = new ChatListModel(null, temp.getTargetId()
                    , MyBaseApplication.USERINFOR.getBody().getAtmanUserId(), temp.getTargetType()
                    , temp.getSendTime(), temp.getContent(), 1, "", temp.getTargetName()
                    , temp.getTargetAvatar(), temp.getType(), temp.getChatId());
            mChatListModelDao.save(tempChat);
        } else {
            mChatListModel.setSendTime(temp.getSendTime());
            mChatListModel.setUnreadNum(mChatListModel.getUnreadNum()+1);

            mChatListModel.setContent(sessionContent);
            mChatListModel.setType(temp.getType());
            mChatListModelDao.update(mChatListModel);
        }
        //添加聊天记录
        ChatMessageModel tempMessage = new ChatMessageModel();
        tempMessage.setId(null);
        LogUtils.e(">>>>>temp.getChatId():"+temp.getChatId());
        tempMessage.setChatId(temp.getChatId());
        tempMessage.setTargetId(temp.getTargetId());
        tempMessage.setLoginId(MyBaseApplication.USERINFOR.getBody().getAtmanUserId());
        tempMessage.setType(temp.getType());
        tempMessage.setTargetType(temp.getTargetType());
        tempMessage.setTargetName(temp.getTargetName());
        tempMessage.setTargetAvatar(temp.getTargetAvatar());
        tempMessage.setSendTime(temp.getSendTime());
        tempMessage.setContent(temp.getContent());

        if (temp.getImageT_back()!=null) {
            tempMessage.setVideo_image_url(temp.getImageT_back());
        }
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
            tempMessage.setChatId(temp.getEventAction().getCouponId());
            tempMessage.setEnterpriseId(temp.getEventAction().getEnterpriseId());
            tempMessage.setGoodId(temp.getEventAction().getGoodId());
            tempMessage.setStoreId(temp.getEventAction().getStoreId());
        }
//
        if (temp.getOperaterList()!=null && temp.getOperaterList().size()>=1) {
            if (temp.getOperaterList().get(1).getOperaterExtra()!=null) {
                tempMessage.setOperaterExtra(temp.getOperaterList().get(1).getOperaterExtra());
                tempMessage.setOperaterId(temp.getOperaterList().get(1).getOperaterId());
                tempMessage.setOperaterName(temp.getOperaterList().get(1).getOperaterName());
                tempMessage.setOperaterType(temp.getOperaterList().get(1).getOperaterType());
            } else {
                tempMessage.setOperaterId(temp.getOperaterList().get(0).getOperaterId());
                tempMessage.setOperaterName(temp.getOperaterList().get(0).getOperaterName());
                tempMessage.setOperaterType(temp.getOperaterList().get(0).getOperaterType());
            }
        }

        tempMessage.setReaded(1);
        tempMessage.setSendStatus(0);
        tempMessage.setSelfSend(false);
        mChatMessageModelDao.save(tempMessage);
        if (!isAppOnFreground(context)) {
            ResidentNotificationHelper.sendResidentNoticeType0(context
                    , temp.getSendTime(), content, temp.getChatId());
        }
        EventBus.getDefault().post(new MessageEvent(mGetMessageModel,tempMessage));
    }

    /**
     * 是否在后台
     *
     * @return
     */
    public boolean isAppOnFreground(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        String curPackageName = MyBaseApplication.getApplication().getApplicationContext().getPackageName();
        List<ActivityManager.RunningAppProcessInfo> app = am.getRunningAppProcesses();
        if (app == null) {
            return false;
        }
        for (ActivityManager.RunningAppProcessInfo a : app) {
            if (a.processName.equals(curPackageName) &&
                    a.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }
        return false;
    }
}
