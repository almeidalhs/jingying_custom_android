package com.atman.jixin.model.response;

import java.util.List;

/**
 * Created by tangbingliang on 16/11/21.
 */

public class MyCardModel {
    /**
     * result : 1
     * body : [{"id":385,"couponStartDate":1479706543,"couponEndDate":1480055743,"couponPrice":1,"couponLimit":30,"storeId":139,"couponState":2,"couponStorage":30,"couponUsage":3,"couponUsedage":0,"couponLock":"1","couponClick":1,"couponPrintStyle":"4STYLE","couponRecommend":false,"couponAllowstate":true,"shareUrl":"http://www.atman.com:8000/cp/385","userReceiveLimit":29,"storeName":"95"},{"id":385,"couponStartDate":1479706543,"couponEndDate":1480055743,"couponPrice":1,"couponLimit":30,"storeId":139,"couponState":2,"couponStorage":30,"couponUsage":3,"couponUsedage":0,"couponLock":"1","couponClick":1,"couponPrintStyle":"4STYLE","couponRecommend":false,"couponAllowstate":true,"shareUrl":"http://www.atman.com:8000/cp/385","userReceiveLimit":29,"storeName":"95"},{"id":385,"couponStartDate":1479706543,"couponEndDate":1480055743,"couponPrice":1,"couponLimit":30,"storeId":139,"couponState":2,"couponStorage":30,"couponUsage":3,"couponUsedage":0,"couponLock":"1","couponClick":1,"couponPrintStyle":"4STYLE","couponRecommend":false,"couponAllowstate":true,"shareUrl":"http://www.atman.com:8000/cp/385","userReceiveLimit":29,"storeName":"95"}]
     */

    private String result;
    /**
     * id : 385
     * couponStartDate : 1479706543
     * couponEndDate : 1480055743
     * couponPrice : 1
     * couponLimit : 30
     * storeId : 139
     * couponState : 2
     * couponStorage : 30
     * couponUsage : 3
     * couponUsedage : 0
     * couponLock : 1
     * couponClick : 1
     * couponPrintStyle : 4STYLE
     * couponRecommend : false
     * couponAllowstate : true
     * shareUrl : http://www.atman.com:8000/cp/385
     * userReceiveLimit : 29
     * storeName : 95
     */

    private List<BodyBean> body;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<BodyBean> getBody() {
        return body;
    }

    public void setBody(List<BodyBean> body) {
        this.body = body;
    }

    public static class BodyBean {
        private long id;
        private long couponStartDate;
        private long couponEndDate;
        private double couponPrice;
        private int couponLimit;
        private long storeId;
        private int couponState;
        private int couponStorage;
        private int couponUsage;
        private int couponUsedage;
        private String couponLock;
        private int couponClick;
        private String couponPrintStyle;
        private boolean couponRecommend;
        private boolean couponAllowstate;
        private String shareUrl;
        private int userReceiveLimit;
        private String storeName;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public long getCouponStartDate() {
            return couponStartDate;
        }

        public void setCouponStartDate(long couponStartDate) {
            this.couponStartDate = couponStartDate;
        }

        public long getCouponEndDate() {
            return couponEndDate;
        }

        public void setCouponEndDate(long couponEndDate) {
            this.couponEndDate = couponEndDate;
        }

        public double getCouponPrice() {
            return couponPrice;
        }

        public void setCouponPrice(double couponPrice) {
            this.couponPrice = couponPrice;
        }

        public int getCouponLimit() {
            return couponLimit;
        }

        public void setCouponLimit(int couponLimit) {
            this.couponLimit = couponLimit;
        }

        public long getStoreId() {
            return storeId;
        }

        public void setStoreId(long storeId) {
            this.storeId = storeId;
        }

        public int getCouponState() {
            return couponState;
        }

        public void setCouponState(int couponState) {
            this.couponState = couponState;
        }

        public int getCouponStorage() {
            return couponStorage;
        }

        public void setCouponStorage(int couponStorage) {
            this.couponStorage = couponStorage;
        }

        public int getCouponUsage() {
            return couponUsage;
        }

        public void setCouponUsage(int couponUsage) {
            this.couponUsage = couponUsage;
        }

        public int getCouponUsedage() {
            return couponUsedage;
        }

        public void setCouponUsedage(int couponUsedage) {
            this.couponUsedage = couponUsedage;
        }

        public String getCouponLock() {
            return couponLock;
        }

        public void setCouponLock(String couponLock) {
            this.couponLock = couponLock;
        }

        public int getCouponClick() {
            return couponClick;
        }

        public void setCouponClick(int couponClick) {
            this.couponClick = couponClick;
        }

        public String getCouponPrintStyle() {
            return couponPrintStyle;
        }

        public void setCouponPrintStyle(String couponPrintStyle) {
            this.couponPrintStyle = couponPrintStyle;
        }

        public boolean isCouponRecommend() {
            return couponRecommend;
        }

        public void setCouponRecommend(boolean couponRecommend) {
            this.couponRecommend = couponRecommend;
        }

        public boolean isCouponAllowstate() {
            return couponAllowstate;
        }

        public void setCouponAllowstate(boolean couponAllowstate) {
            this.couponAllowstate = couponAllowstate;
        }

        public String getShareUrl() {
            return shareUrl;
        }

        public void setShareUrl(String shareUrl) {
            this.shareUrl = shareUrl;
        }

        public int getUserReceiveLimit() {
            return userReceiveLimit;
        }

        public void setUserReceiveLimit(int userReceiveLimit) {
            this.userReceiveLimit = userReceiveLimit;
        }

        public String getStoreName() {
            return storeName;
        }

        public void setStoreName(String storeName) {
            this.storeName = storeName;
        }
    }
}
