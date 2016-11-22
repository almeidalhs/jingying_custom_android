package com.atman.jixin.model.response;

/**
 * Created by tangbingliang on 16/11/22.
 */

public class GetCouponModel {
    /**
     * result : 1
     * body : {"storeId":139,"storeName":"95","couponId":385,"phone":"15555555533","resource":4,"status":0,"addTime":1479795441,"needBindPhone":0}
     */

    private String result;
    /**
     * storeId : 139
     * storeName : 95
     * couponId : 385
     * phone : 15555555533
     * resource : 4
     * status : 0
     * addTime : 1479795441
     * needBindPhone : 0
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
        private long couponId;
        private String phone;
        private int resource;
        private int status;
        private int addTime;
        private int needBindPhone;

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

        public long getCouponId() {
            return couponId;
        }

        public void setCouponId(long couponId) {
            this.couponId = couponId;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public int getResource() {
            return resource;
        }

        public void setResource(int resource) {
            this.resource = resource;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getAddTime() {
            return addTime;
        }

        public void setAddTime(int addTime) {
            this.addTime = addTime;
        }

        public int getNeedBindPhone() {
            return needBindPhone;
        }

        public void setNeedBindPhone(int needBindPhone) {
            this.needBindPhone = needBindPhone;
        }
    }
}
