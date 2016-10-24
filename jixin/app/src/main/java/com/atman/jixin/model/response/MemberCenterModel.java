package com.atman.jixin.model.response;

import java.util.List;

/**
 * Created by tangbingliang on 16/10/24.
 */

public class MemberCenterModel {
    /**
     * result : 1
     * body : {"storeId":24,"storeName":"店铺1","storeAvatar":"Store/ed/90/ed90a295126311e6b6e674d02ba07f83.jpg","couponNum":0,"integral":0,"consumeNum":0,"userList":[{"id":1000000589,"atmanUserId":100004057,"memberName":"即客-0588","memberSex":"女性","memberAge":"1岁","memberSign":"","memberAvatar":"Avatar/b0/ad/b0adf229973111e6abe874d02ba07f83.jpg","addressDetail":"","bindTime":"37分钟前"}]}
     */

    private String result;
    /**
     * storeId : 24
     * storeName : 店铺1
     * storeAvatar : Store/ed/90/ed90a295126311e6b6e674d02ba07f83.jpg
     * couponNum : 0
     * integral : 0
     * consumeNum : 0
     * userList : [{"id":1000000589,"atmanUserId":100004057,"memberName":"即客-0588","memberSex":"女性","memberAge":"1岁","memberSign":"","memberAvatar":"Avatar/b0/ad/b0adf229973111e6abe874d02ba07f83.jpg","addressDetail":"","bindTime":"37分钟前"}]
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
        private long storeId;
        private String storeName;
        private String storeAvatar;
        private int couponNum;
        private int integral;
        private int consumeNum;
        /**
         * id : 1000000589
         * atmanUserId : 100004057
         * memberName : 即客-0588
         * memberSex : 女性
         * memberAge : 1岁
         * memberSign :
         * memberAvatar : Avatar/b0/ad/b0adf229973111e6abe874d02ba07f83.jpg
         * addressDetail :
         * bindTime : 37分钟前
         */

        private List<UserListBean> userList;

        public long getStoreId() {
            return storeId;
        }

        public void setStoreId(long storeId) {
            this.storeId = storeId;
        }

        public String getStoreName() {
            return storeName;
        }

        public void setStoreName(String storeName) {
            this.storeName = storeName;
        }

        public String getStoreAvatar() {
            return storeAvatar;
        }

        public void setStoreAvatar(String storeAvatar) {
            this.storeAvatar = storeAvatar;
        }

        public int getCouponNum() {
            return couponNum;
        }

        public void setCouponNum(int couponNum) {
            this.couponNum = couponNum;
        }

        public int getIntegral() {
            return integral;
        }

        public void setIntegral(int integral) {
            this.integral = integral;
        }

        public int getConsumeNum() {
            return consumeNum;
        }

        public void setConsumeNum(int consumeNum) {
            this.consumeNum = consumeNum;
        }

        public List<UserListBean> getUserList() {
            return userList;
        }

        public void setUserList(List<UserListBean> userList) {
            this.userList = userList;
        }

        public static class UserListBean {
            private long id;
            private int atmanUserId;
            private String memberName;
            private String memberSex;
            private String memberAge;
            private String memberSign;
            private String memberAvatar;
            private String addressDetail;
            private String bindTime;

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
            }

            public int getAtmanUserId() {
                return atmanUserId;
            }

            public void setAtmanUserId(int atmanUserId) {
                this.atmanUserId = atmanUserId;
            }

            public String getMemberName() {
                return memberName;
            }

            public void setMemberName(String memberName) {
                this.memberName = memberName;
            }

            public String getMemberSex() {
                return memberSex;
            }

            public void setMemberSex(String memberSex) {
                this.memberSex = memberSex;
            }

            public String getMemberAge() {
                return memberAge;
            }

            public void setMemberAge(String memberAge) {
                this.memberAge = memberAge;
            }

            public String getMemberSign() {
                return memberSign;
            }

            public void setMemberSign(String memberSign) {
                this.memberSign = memberSign;
            }

            public String getMemberAvatar() {
                return memberAvatar;
            }

            public void setMemberAvatar(String memberAvatar) {
                this.memberAvatar = memberAvatar;
            }

            public String getAddressDetail() {
                return addressDetail;
            }

            public void setAddressDetail(String addressDetail) {
                this.addressDetail = addressDetail;
            }

            public String getBindTime() {
                return bindTime;
            }

            public void setBindTime(String bindTime) {
                this.bindTime = bindTime;
            }
        }
    }
}
