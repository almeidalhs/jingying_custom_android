package com.atman.jixin.model.bean;

import com.atman.jixin.model.response.GetChatServiceModel;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by tangbingliang on 16/10/26.
 */
@Entity
public class ChatMessageModel {
    @Id(autoincrement = true)
    private Long id;
    private long chatId;
    private long loginId;
    private int type;//消息类别
    private int targetType; //0:target为用户 1:target为店铺
    private long targetId;
    private String targetName;
    private String targetAvatar;
    private long sendTime;
    private String content; //消息内容
    private int audio_duration;//语音长度
    private String audioLocationUrl;//语音本地路径
    private String video_image_url;//视屏封面
    //图文
    private String imageT_icon;
    private String imageT_title;
    private String imageT_back;//背景图
    //C发送/B接收 识别编号 可能是任何类型消息
    private String identifyStr;

    private long operaterId;
    private String operaterName;
    private String operaterExtra;
    private int operaterType;

    private int actionType;
    private long couponId;
    private int enterpriseId;
    private long goodId;
    private long storeId;

    //本地存储时使用
    private boolean selfSend;//是否自己发送
    private int readed;//是否已读 0已读 1未读 暂仅用于语音

    private int sendStatus;//0正常 1error 2发送中

    public int getSendStatus() {
        return this.sendStatus;
    }

    public void setSendStatus(int sendStatus) {
        this.sendStatus = sendStatus;
    }

    public int getReaded() {
        return this.readed;
    }

    public void setReaded(int readed) {
        this.readed = readed;
    }

    public boolean getSelfSend() {
        return this.selfSend;
    }

    public void setSelfSend(boolean selfSend) {
        this.selfSend = selfSend;
    }

    public long getStoreId() {
        return this.storeId;
    }

    public void setStoreId(long storeId) {
        this.storeId = storeId;
    }

    public long getGoodId() {
        return this.goodId;
    }

    public void setGoodId(long goodId) {
        this.goodId = goodId;
    }

    public int getEnterpriseId() {
        return this.enterpriseId;
    }

    public void setEnterpriseId(int enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public long getCouponId() {
        return this.couponId;
    }

    public void setCouponId(long couponId) {
        this.couponId = couponId;
    }

    public int getActionType() {
        return this.actionType;
    }

    public void setActionType(int actionType) {
        this.actionType = actionType;
    }

    public int getOperaterType() {
        return this.operaterType;
    }

    public void setOperaterType(int operaterType) {
        this.operaterType = operaterType;
    }

    public String getOperaterExtra() {
        return this.operaterExtra;
    }

    public void setOperaterExtra(String operaterExtra) {
        this.operaterExtra = operaterExtra;
    }

    public String getOperaterName() {
        return this.operaterName;
    }

    public void setOperaterName(String operaterName) {
        this.operaterName = operaterName;
    }

    public long getOperaterId() {
        return this.operaterId;
    }

    public void setOperaterId(long operaterId) {
        this.operaterId = operaterId;
    }

    public String getIdentifyStr() {
        return this.identifyStr;
    }

    public void setIdentifyStr(String identifyStr) {
        this.identifyStr = identifyStr;
    }

    public String getImageT_back() {
        return this.imageT_back;
    }

    public void setImageT_back(String imageT_back) {
        this.imageT_back = imageT_back;
    }

    public String getImageT_title() {
        return this.imageT_title;
    }

    public void setImageT_title(String imageT_title) {
        this.imageT_title = imageT_title;
    }

    public String getImageT_icon() {
        return this.imageT_icon;
    }

    public void setImageT_icon(String imageT_icon) {
        this.imageT_icon = imageT_icon;
    }

    public String getVideo_image_url() {
        return this.video_image_url;
    }

    public void setVideo_image_url(String video_image_url) {
        this.video_image_url = video_image_url;
    }

    public String getAudioLocationUrl() {
        return this.audioLocationUrl;
    }

    public void setAudioLocationUrl(String audioLocationUrl) {
        this.audioLocationUrl = audioLocationUrl;
    }

    public int getAudio_duration() {
        return this.audio_duration;
    }

    public void setAudio_duration(int audio_duration) {
        this.audio_duration = audio_duration;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getSendTime() {
        return this.sendTime;
    }

    public void setSendTime(long sendTime) {
        this.sendTime = sendTime;
    }

    public String getTargetAvatar() {
        return this.targetAvatar;
    }

    public void setTargetAvatar(String targetAvatar) {
        this.targetAvatar = targetAvatar;
    }

    public String getTargetName() {
        return this.targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public long getTargetId() {
        return this.targetId;
    }

    public void setTargetId(long targetId) {
        this.targetId = targetId;
    }

    public int getTargetType() {
        return this.targetType;
    }

    public void setTargetType(int targetType) {
        this.targetType = targetType;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getLoginId() {
        return this.loginId;
    }

    public void setLoginId(long loginId) {
        this.loginId = loginId;
    }

    public long getChatId() {
        return this.chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Generated(hash = 508738304)
    public ChatMessageModel(Long id, long chatId, long loginId, int type,
            int targetType, long targetId, String targetName, String targetAvatar,
            long sendTime, String content, int audio_duration,
            String audioLocationUrl, String video_image_url, String imageT_icon,
            String imageT_title, String imageT_back, String identifyStr,
            long operaterId, String operaterName, String operaterExtra,
            int operaterType, int actionType, long couponId, int enterpriseId,
            long goodId, long storeId, boolean selfSend, int readed, int sendStatus) {
        this.id = id;
        this.chatId = chatId;
        this.loginId = loginId;
        this.type = type;
        this.targetType = targetType;
        this.targetId = targetId;
        this.targetName = targetName;
        this.targetAvatar = targetAvatar;
        this.sendTime = sendTime;
        this.content = content;
        this.audio_duration = audio_duration;
        this.audioLocationUrl = audioLocationUrl;
        this.video_image_url = video_image_url;
        this.imageT_icon = imageT_icon;
        this.imageT_title = imageT_title;
        this.imageT_back = imageT_back;
        this.identifyStr = identifyStr;
        this.operaterId = operaterId;
        this.operaterName = operaterName;
        this.operaterExtra = operaterExtra;
        this.operaterType = operaterType;
        this.actionType = actionType;
        this.couponId = couponId;
        this.enterpriseId = enterpriseId;
        this.goodId = goodId;
        this.storeId = storeId;
        this.selfSend = selfSend;
        this.readed = readed;
        this.sendStatus = sendStatus;
    }

    @Generated(hash = 1665412225)
    public ChatMessageModel() {
    }

}
