package com.atman.jixin.model.response;

import com.atman.jixin.model.response.GetChatServiceModel;


import java.util.List;

/**
 * Created by tangbingliang on 16/10/26.
 */

public class MessageModel {
    private long chatId;
    private int type;
    private int targetType; //0:target为用户 1:target为店铺
    private long targetId;
    private String targetName;
    private String targetAvatar;
    private long sendTime;
    private String content; //消息内容
    private int audio_duration;//语音长度
    private String video_image_url;//视屏封面
    //图文
    private String imageT_icon;
    private String imageT_title;
    private String imageT_back;//背景图
    //C发送/B接收 识别编号 可能是任何类型消息
    private String identifyStr;
    private EventAction eventAction;
    private List<GetChatServiceModel.BodyBean.MessageBeanBean.OperaterListBean> operaterList;

    //本地存储时使用
    private boolean selfSend;//是否自己发送
    private int readed;//是否已读 0已读 1未读 暂仅用于语音
    private int sendStatus;//0正常 1error 2发送中

    public static class EventAction {
        private int actionType;
    }

    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getTargetType() {
        return targetType;
    }

    public void setTargetType(int targetType) {
        this.targetType = targetType;
    }

    public long getTargetId() {
        return targetId;
    }

    public void setTargetId(long targetId) {
        this.targetId = targetId;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public String getTargetAvatar() {
        return targetAvatar;
    }

    public void setTargetAvatar(String targetAvatar) {
        this.targetAvatar = targetAvatar;
    }

    public long getSendTime() {
        return sendTime;
    }

    public void setSendTime(long sendTime) {
        this.sendTime = sendTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getAudio_duration() {
        return audio_duration;
    }

    public void setAudio_duration(int audio_duration) {
        this.audio_duration = audio_duration;
    }

    public String getVideo_image_url() {
        return video_image_url;
    }

    public void setVideo_image_url(String video_image_url) {
        this.video_image_url = video_image_url;
    }

    public String getImageT_icon() {
        return imageT_icon;
    }

    public void setImageT_icon(String imageT_icon) {
        this.imageT_icon = imageT_icon;
    }

    public String getImageT_title() {
        return imageT_title;
    }

    public void setImageT_title(String imageT_title) {
        this.imageT_title = imageT_title;
    }

    public String getImageT_back() {
        return imageT_back;
    }

    public void setImageT_back(String imageT_back) {
        this.imageT_back = imageT_back;
    }

    public String getIdentifyStr() {
        return identifyStr;
    }

    public void setIdentifyStr(String identifyStr) {
        this.identifyStr = identifyStr;
    }

    public EventAction getEventAction() {
        return eventAction;
    }

    public void setEventAction(EventAction eventAction) {
        this.eventAction = eventAction;
    }

    public List<GetChatServiceModel.BodyBean.MessageBeanBean.OperaterListBean> getOperaterList() {
        return operaterList;
    }

    public void setOperaterList(List<GetChatServiceModel.BodyBean.MessageBeanBean.OperaterListBean> operaterList) {
        this.operaterList = operaterList;
    }

    public boolean isSelfSend() {
        return selfSend;
    }

    public void setSelfSend(boolean selfSend) {
        this.selfSend = selfSend;
    }

    public int getReaded() {
        return readed;
    }

    public void setReaded(int readed) {
        this.readed = readed;
    }

    public int getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(int sendStatus) {
        this.sendStatus = sendStatus;
    }
}
