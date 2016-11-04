package com.atman.jixin.model.bean;

import com.atman.jixin.model.response.LoginResultModel;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by tangbingliang on 16/10/26.
 */
@Entity
public class ChatListModel {

    @Id(autoincrement = true)
    private Long id;
    private long targetId;
    private long loginId;
    private int targetType;//0:用户 1:商家
    private long sendTime;
    private String content;
    private int unreadNum;
    //识别编号 所在商家的识别编号，此处用于存储
    private String identifyStr;
    private String targetName;
    private String targetAvatar;
    private int type;
    private long chatId;
    public long getChatId() {
        return this.chatId;
    }
    public void setChatId(long chatId) {
        this.chatId = chatId;
    }
    public int getType() {
        return this.type;
    }
    public void setType(int type) {
        this.type = type;
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
    public String getIdentifyStr() {
        return this.identifyStr;
    }
    public void setIdentifyStr(String identifyStr) {
        this.identifyStr = identifyStr;
    }
    public int getUnreadNum() {
        return this.unreadNum;
    }
    public void setUnreadNum(int unreadNum) {
        this.unreadNum = unreadNum;
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
    public int getTargetType() {
        return this.targetType;
    }
    public void setTargetType(int targetType) {
        this.targetType = targetType;
    }
    public long getLoginId() {
        return this.loginId;
    }
    public void setLoginId(long loginId) {
        this.loginId = loginId;
    }
    public long getTargetId() {
        return this.targetId;
    }
    public void setTargetId(long targetId) {
        this.targetId = targetId;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Generated(hash = 1939797978)
    public ChatListModel(Long id, long targetId, long loginId, int targetType,
            long sendTime, String content, int unreadNum, String identifyStr,
            String targetName, String targetAvatar, int type, long chatId) {
        this.id = id;
        this.targetId = targetId;
        this.loginId = loginId;
        this.targetType = targetType;
        this.sendTime = sendTime;
        this.content = content;
        this.unreadNum = unreadNum;
        this.identifyStr = identifyStr;
        this.targetName = targetName;
        this.targetAvatar = targetAvatar;
        this.type = type;
        this.chatId = chatId;
    }
    @Generated(hash = 2047655589)
    public ChatListModel() {
    }
}
