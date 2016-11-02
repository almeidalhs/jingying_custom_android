package com.atman.jixin.model.response;

import java.util.List;

/**
 * Created by tangbingliang on 16/10/25.
 */

public class ExchangeRecordModel {
    /**
     * result : 1
     * body : [{"id":253,"addTime":1477308712,"state":0,"amount":1,"goodsImage":"Goods/d5/c1/d5c1658a2c8611e6abe874d02ba07f83.png","goodsPrice":5,"goodsName":"哦吼","userLimit":20,"goodsId":407,"integral":1,"orderNo":2016102400000057},{"id":252,"addTime":1477308610,"state":0,"amount":1,"goodsImage":"Goods/d5/c1/d5c1658a2c8611e6abe874d02ba07f83.png","goodsPrice":5,"goodsName":"哦吼","userLimit":20,"goodsId":407,"integral":1,"orderNo":2016102400000056},{"id":251,"addTime":1477308373,"state":0,"amount":1,"goodsImage":"Goods/d5/c1/d5c1658a2c8611e6abe874d02ba07f83.png","goodsPrice":5,"goodsName":"哦吼","userLimit":20,"goodsId":407,"integral":1,"orderNo":2016102400000055},{"id":250,"addTime":1477308169,"state":0,"amount":1,"goodsImage":"Goods/76/e5/76e5ffde182511e6836574d02ba07f83.gif","goodsPrice":7492476,"goodsName":"呀show","userLimit":3,"goodsId":340,"integral":1,"orderNo":2016102400000054},{"id":249,"addTime":1477307973,"state":0,"amount":1,"goodsImage":"Goods/76/e5/76e5ffde182511e6836574d02ba07f83.gif","goodsPrice":7492476,"goodsName":"呀show","userLimit":3,"goodsId":340,"integral":1,"orderNo":2016102400000053},{"id":248,"addTime":1477307858,"state":0,"amount":1,"goodsImage":"Goods/76/e5/76e5ffde182511e6836574d02ba07f83.gif","goodsPrice":7492476,"goodsName":"呀show","userLimit":3,"goodsId":340,"integral":1,"orderNo":2016102400000052}]
     */

    private String result;
    /**
     * id : 253
     * addTime : 1477308712
     * state : 0
     * amount : 1
     * goodsImage : Goods/d5/c1/d5c1658a2c8611e6abe874d02ba07f83.png
     * goodsPrice : 5
     * goodsName : 哦吼
     * userLimit : 20
     * goodsId : 407
     * integral : 1
     * orderNo : 2016102400000057
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
        private long addTime;
        private int state;
        private int amount;
        private String goodsImage;
        private int goodsPrice;
        private String goodsName;
        private int userLimit;
        private long goodsId;
        private int integral;
        private long orderNo;
        private long storeId;
        private String storeName;
        private String storeBanner;

        public String getStoreName() {
            return storeName;
        }

        public void setStoreName(String storeName) {
            this.storeName = storeName;
        }

        public String getStoreBanner() {
            return storeBanner;
        }

        public void setStoreBanner(String storeBanner) {
            this.storeBanner = storeBanner;
        }

        public long getStoreId() {
            return storeId;
        }

        public void setStoreId(long storeId) {
            this.storeId = storeId;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public long getAddTime() {
            return addTime;
        }

        public void setAddTime(long addTime) {
            this.addTime = addTime;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public String getGoodsImage() {
            return goodsImage;
        }

        public void setGoodsImage(String goodsImage) {
            this.goodsImage = goodsImage;
        }

        public int getGoodsPrice() {
            return goodsPrice;
        }

        public void setGoodsPrice(int goodsPrice) {
            this.goodsPrice = goodsPrice;
        }

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public int getUserLimit() {
            return userLimit;
        }

        public void setUserLimit(int userLimit) {
            this.userLimit = userLimit;
        }

        public long getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(long goodsId) {
            this.goodsId = goodsId;
        }

        public int getIntegral() {
            return integral;
        }

        public void setIntegral(int integral) {
            this.integral = integral;
        }

        public long getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(long orderNo) {
            this.orderNo = orderNo;
        }
    }
}
