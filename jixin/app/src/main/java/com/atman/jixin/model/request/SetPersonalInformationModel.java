package com.atman.jixin.model.request;

/**
 * Created by tangbingliang on 16/10/18.
 */

public class SetPersonalInformationModel {
    /**
     * memberName : 猴子请来的逗逼
     * aroundSite : 上海滩
     * memberSign : 我的签名最个性
     * interest : 我的兴趣爱好是互相伤害
     * job : 我的工作时看别人互相伤害
     * maritalStatus : 单身汪
     * memberSex : 1
     * memberBirthday : 1284452971
     * showNear : 0
     */

    private String memberName;
    private String aroundSite;
    private String memberSign;
    private String interest;
    private String job;
    private String maritalStatus;
    private int memberSex;
    private long memberBirthday;
    private int showNear;

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getAroundSite() {
        return aroundSite;
    }

    public void setAroundSite(String aroundSite) {
        this.aroundSite = aroundSite;
    }

    public String getMemberSign() {
        return memberSign;
    }

    public void setMemberSign(String memberSign) {
        this.memberSign = memberSign;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public int getMemberSex() {
        return memberSex;
    }

    public void setMemberSex(int memberSex) {
        this.memberSex = memberSex;
    }

    public long getMemberBirthday() {
        return memberBirthday;
    }

    public void setMemberBirthday(long memberBirthday) {
        this.memberBirthday = memberBirthday;
    }

    public int getShowNear() {
        return showNear;
    }

    public void setShowNear(int showNear) {
        this.showNear = showNear;
    }
}
