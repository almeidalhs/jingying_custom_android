package com.atman.jixin.model.request;

/**
 * Created by tangbingliang on 16/10/17.
 */

public class LoginRequestModel {
    /**
     * userName : 13500000043
     * password : 343b1c4a3ea721b2d640fc8700db0f36
     * idfa : idfa
     * platform : 客户端操作系统
     * language : en
     * deviceToken : token
     * isTestToken : false
     * version : 2.1.0
     * deviceType : 客户端硬件型号
     * channel : 广告来源
     */

    private String userName;
    private String password;
    private String idfa;
    private String platform;
    private String language;
    private String deviceToken;
    private boolean isTestToken;
    private String version;
    private String deviceType;
    private String channel;
    private int login_terminal;

    public LoginRequestModel(String userName, String password, String deviceToken, String version
            , String platform, String deviceType, String channel) {
        this.userName = userName;
        this.password = password;
        this.idfa = "i_am_android";
        this.platform = platform;
        this.language = "en";
        this.deviceToken = deviceToken;
        this.isTestToken = false;
        this.version = version;
        this.deviceType = deviceType;
        this.channel = channel;
        this.login_terminal = 0;
    }

    public int getLogin_terminal() {
        return login_terminal;
    }

    public void setLogin_terminal(int login_terminal) {
        this.login_terminal = login_terminal;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIdfa() {
        return idfa;
    }

    public void setIdfa(String idfa) {
        this.idfa = idfa;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public boolean isIsTestToken() {
        return isTestToken;
    }

    public void setIsTestToken(boolean isTestToken) {
        this.isTestToken = isTestToken;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }
}
