package com.atman.jixin.model.response;

/**
 * Created by tangbingliang on 16/10/17.
 */

public class LoginResultModel {
    /**
     * result : 1
     * body : {"id":1000000031,"memberName":"即客-0032","memberTrueName":"即客-0032","atmanUserId":100003926,"memberMobile":"15968890600","memberAvatar":"Avatar/6a/75/6a750d25b789420baf794de71446ca1.jpg","memberSex":0,"memberBirthday":"2011-09-26 00:00:00","memberPasswd":"343b1c4a3ea721b2d640fc8700db0f36","memberEmail":"null@jimall.com","memberLoginNum":1,"memberGoldNum":200,"memberGoldCount":0,"memberGoldNumMinus":0,"memberQqInfo":"5ea698ef943611e6a06e74d02ba07f83","memberPoints":0,"availablePredeposit":0,"freezePredeposit":0,"informAllow":1,"isBuy":1,"isAllowTalk":1,"memberState":1,"memberCredit":0,"memberSnsVisitNum":0,"getuiCid":"cb5e17831fd13b8a65cbedc5885c8264","aroundSite":"✈️✈️","maritalStatus":"恋爱中","showNear":1,"guestUuid":"33e6ba94-839a-11e6-b3b4-74d02ba07f83","mobileSign":"1D5A90C9-99E4-4220-986B-A5859FEFFB2F"}
     */

    private String result;
    /**
     * id : 1000000031
     * memberName : 即客-0032
     * memberTrueName : 即客-0032
     * atmanUserId : 100003926
     * memberMobile : 15968890600
     * memberAvatar : Avatar/6a/75/6a750d25b789420baf794de71446ca1.jpg
     * memberSex : 0
     * memberBirthday : 2011-09-26 00:00:00
     * memberPasswd : 343b1c4a3ea721b2d640fc8700db0f36
     * memberEmail : null@jimall.com
     * memberLoginNum : 1
     * memberGoldNum : 200
     * memberGoldCount : 0
     * memberGoldNumMinus : 0
     * memberQqInfo : 5ea698ef943611e6a06e74d02ba07f83
     * memberPoints : 0
     * availablePredeposit : 0
     * freezePredeposit : 0
     * informAllow : 1
     * isBuy : 1
     * isAllowTalk : 1
     * memberState : 1
     * memberCredit : 0
     * memberSnsVisitNum : 0
     * getuiCid : cb5e17831fd13b8a65cbedc5885c8264
     * aroundSite : ✈️✈️
     * maritalStatus : 恋爱中
     * showNear : 1
     * guestUuid : 33e6ba94-839a-11e6-b3b4-74d02ba07f83
     * mobileSign : 1D5A90C9-99E4-4220-986B-A5859FEFFB2F
     */

    private BodyBean body;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public BodyBean getBody() {
        return body;
    }

    public void setBody(BodyBean body) {
        this.body = body;
    }

    public static class BodyBean {
        private long id;
        private String memberName;
        private String memberTrueName;
        private int atmanUserId;
        private String memberMobile;
        private String memberAvatar;
        private int memberSex;
        private String memberBirthday;
        private String memberPasswd;
        private String memberEmail;
        private int memberLoginNum;
        private int memberGoldNum;
        private int memberGoldCount;
        private int memberGoldNumMinus;
        private String memberQqInfo;
        private int memberPoints;
        private int availablePredeposit;
        private int freezePredeposit;
        private int informAllow;
        private int isBuy;
        private int isAllowTalk;
        private int memberState;
        private int memberCredit;
        private int memberSnsVisitNum;
        private String getuiCid;
        private String aroundSite;
        private String maritalStatus;
        private int showNear;
        private String guestUuid;
        private String mobileSign;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getMemberName() {
            return memberName;
        }

        public void setMemberName(String memberName) {
            this.memberName = memberName;
        }

        public String getMemberTrueName() {
            return memberTrueName;
        }

        public void setMemberTrueName(String memberTrueName) {
            this.memberTrueName = memberTrueName;
        }

        public int getAtmanUserId() {
            return atmanUserId;
        }

        public void setAtmanUserId(int atmanUserId) {
            this.atmanUserId = atmanUserId;
        }

        public String getMemberMobile() {
            return memberMobile;
        }

        public void setMemberMobile(String memberMobile) {
            this.memberMobile = memberMobile;
        }

        public String getMemberAvatar() {
            return memberAvatar;
        }

        public void setMemberAvatar(String memberAvatar) {
            this.memberAvatar = memberAvatar;
        }

        public int getMemberSex() {
            return memberSex;
        }

        public void setMemberSex(int memberSex) {
            this.memberSex = memberSex;
        }

        public String getMemberBirthday() {
            return memberBirthday;
        }

        public void setMemberBirthday(String memberBirthday) {
            this.memberBirthday = memberBirthday;
        }

        public String getMemberPasswd() {
            return memberPasswd;
        }

        public void setMemberPasswd(String memberPasswd) {
            this.memberPasswd = memberPasswd;
        }

        public String getMemberEmail() {
            return memberEmail;
        }

        public void setMemberEmail(String memberEmail) {
            this.memberEmail = memberEmail;
        }

        public int getMemberLoginNum() {
            return memberLoginNum;
        }

        public void setMemberLoginNum(int memberLoginNum) {
            this.memberLoginNum = memberLoginNum;
        }

        public int getMemberGoldNum() {
            return memberGoldNum;
        }

        public void setMemberGoldNum(int memberGoldNum) {
            this.memberGoldNum = memberGoldNum;
        }

        public int getMemberGoldCount() {
            return memberGoldCount;
        }

        public void setMemberGoldCount(int memberGoldCount) {
            this.memberGoldCount = memberGoldCount;
        }

        public int getMemberGoldNumMinus() {
            return memberGoldNumMinus;
        }

        public void setMemberGoldNumMinus(int memberGoldNumMinus) {
            this.memberGoldNumMinus = memberGoldNumMinus;
        }

        public String getMemberQqInfo() {
            return memberQqInfo;
        }

        public void setMemberQqInfo(String memberQqInfo) {
            this.memberQqInfo = memberQqInfo;
        }

        public int getMemberPoints() {
            return memberPoints;
        }

        public void setMemberPoints(int memberPoints) {
            this.memberPoints = memberPoints;
        }

        public int getAvailablePredeposit() {
            return availablePredeposit;
        }

        public void setAvailablePredeposit(int availablePredeposit) {
            this.availablePredeposit = availablePredeposit;
        }

        public int getFreezePredeposit() {
            return freezePredeposit;
        }

        public void setFreezePredeposit(int freezePredeposit) {
            this.freezePredeposit = freezePredeposit;
        }

        public int getInformAllow() {
            return informAllow;
        }

        public void setInformAllow(int informAllow) {
            this.informAllow = informAllow;
        }

        public int getIsBuy() {
            return isBuy;
        }

        public void setIsBuy(int isBuy) {
            this.isBuy = isBuy;
        }

        public int getIsAllowTalk() {
            return isAllowTalk;
        }

        public void setIsAllowTalk(int isAllowTalk) {
            this.isAllowTalk = isAllowTalk;
        }

        public int getMemberState() {
            return memberState;
        }

        public void setMemberState(int memberState) {
            this.memberState = memberState;
        }

        public int getMemberCredit() {
            return memberCredit;
        }

        public void setMemberCredit(int memberCredit) {
            this.memberCredit = memberCredit;
        }

        public int getMemberSnsVisitNum() {
            return memberSnsVisitNum;
        }

        public void setMemberSnsVisitNum(int memberSnsVisitNum) {
            this.memberSnsVisitNum = memberSnsVisitNum;
        }

        public String getGetuiCid() {
            return getuiCid;
        }

        public void setGetuiCid(String getuiCid) {
            this.getuiCid = getuiCid;
        }

        public String getAroundSite() {
            return aroundSite;
        }

        public void setAroundSite(String aroundSite) {
            this.aroundSite = aroundSite;
        }

        public String getMaritalStatus() {
            return maritalStatus;
        }

        public void setMaritalStatus(String maritalStatus) {
            this.maritalStatus = maritalStatus;
        }

        public int getShowNear() {
            return showNear;
        }

        public void setShowNear(int showNear) {
            this.showNear = showNear;
        }

        public String getGuestUuid() {
            return guestUuid;
        }

        public void setGuestUuid(String guestUuid) {
            this.guestUuid = guestUuid;
        }

        public String getMobileSign() {
            return mobileSign;
        }

        public void setMobileSign(String mobileSign) {
            this.mobileSign = mobileSign;
        }
    }
}
