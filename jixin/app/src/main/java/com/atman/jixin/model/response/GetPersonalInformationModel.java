package com.atman.jixin.model.response;

/**
 * Created by tangbingliang on 16/10/18.
 */

public class GetPersonalInformationModel {
    /**
     * result : 1
     * body : {"id":1000000594,"memberName":"即客-0551","memberTrueName":"即客-0551","atmanUserId":100003902,"memberMobile":"18200000002","memberAvatar":"Avatar/6a/75/6a750d25b789420baf794de71446ca1.jpg","memberSex":0,"memberBirthday":631123200000,"memberPasswd":"123456","memberEmail":"18200000002@jimall.com","memberLoginNum":1,"memberGoldNum":200,"memberGoldCount":0,"memberGoldNumMinus":0,"memberPoints":0,"availablePredeposit":0,"freezePredeposit":0,"informAllow":1,"isBuy":1,"isAllowTalk":1,"memberState":1,"memberCredit":0,"memberSnsVisitNum":0,"getuiCid":"0ec30363b0c6e83946545a1e1c0d4e8e","showNear":1,"guestUuid":"13761f35-94f7-11e6-90c0-74d02ba07f83","likeNum":0,"favNum":0,"couponNum":2,"consumeNum":2}
     */

    private String result;
    /**
     * id : 1000000594
     * memberName : 即客-0551
     * memberTrueName : 即客-0551
     * atmanUserId : 100003902
     * memberMobile : 18200000002
     * memberAvatar : Avatar/6a/75/6a750d25b789420baf794de71446ca1.jpg
     * memberSex : 0
     * memberBirthday : 631123200000
     * memberPasswd : 123456
     * memberEmail : 18200000002@jimall.com
     * memberLoginNum : 1
     * memberGoldNum : 200
     * memberGoldCount : 0
     * memberGoldNumMinus : 0
     * memberPoints : 0
     * availablePredeposit : 0
     * freezePredeposit : 0
     * informAllow : 1
     * isBuy : 1
     * isAllowTalk : 1
     * memberState : 1
     * memberCredit : 0
     * memberSnsVisitNum : 0
     * getuiCid : 0ec30363b0c6e83946545a1e1c0d4e8e
     * showNear : 1
     * guestUuid : 13761f35-94f7-11e6-90c0-74d02ba07f83
     * likeNum : 0
     * favNum : 0
     * couponNum : 2
     * consumeNum : 2
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
        private long atmanUserId;
        private String memberMobile;
        private String memberAvatar;
        private int memberSex;
        private long memberBirthday;
        private String memberPasswd;
        private String memberEmail;
        private int memberLoginNum;
        private int memberGoldNum;
        private int memberGoldCount;
        private int memberGoldNumMinus;
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
        private int showNear;
        private String guestUuid;
        private int likeNum;
        private int favNum;
        private int couponNum;
        private int consumeNum;

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

        public long getAtmanUserId() {
            return atmanUserId;
        }

        public void setAtmanUserId(long atmanUserId) {
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

        public long getMemberBirthday() {
            return memberBirthday;
        }

        public void setMemberBirthday(long memberBirthday) {
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

        public int getLikeNum() {
            return likeNum;
        }

        public void setLikeNum(int likeNum) {
            this.likeNum = likeNum;
        }

        public int getFavNum() {
            return favNum;
        }

        public void setFavNum(int favNum) {
            this.favNum = favNum;
        }

        public int getCouponNum() {
            return couponNum;
        }

        public void setCouponNum(int couponNum) {
            this.couponNum = couponNum;
        }

        public int getConsumeNum() {
            return consumeNum;
        }

        public void setConsumeNum(int consumeNum) {
            this.consumeNum = consumeNum;
        }
    }
}
