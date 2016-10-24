package com.atman.jixin.model.response;

import java.util.List;

/**
 * Created by tangbingliang on 16/10/24.
 */

public class IntegralGoodsModel {
    /**
     * result : 1
     * body : [{"id":104,"storeId":24,"goodsId":407,"integral":1,"state":1,"storeLimit":30,"userLimit":20,"goodsImage":"Goods/d5/c1/d5c1658a2c8611e6abe874d02ba07f83.png","goodsPrice":5,"goodsName":"哦吼","exchangeNum":0},{"id":105,"storeId":24,"goodsId":406,"integral":2,"state":1,"storeLimit":10,"userLimit":1,"goodsImage":"Goods/b5/44/b54470e42c8611e6abe874d02ba07f83.jpg","goodsPrice":469,"goodsName":"2773","exchangeNum":0},{"id":106,"storeId":24,"goodsId":369,"integral":3,"state":1,"storeLimit":4,"userLimit":4,"goodsImage":"Goods/5e/68/5e68d37d21c911e698c274d02ba07f83.jpg","goodsPrice":4548,"goodsName":"破哦亲移民","exchangeNum":0},{"id":107,"storeId":24,"goodsId":340,"integral":1,"state":1,"storeLimit":5,"userLimit":3,"goodsImage":"Goods/76/e5/76e5ffde182511e6836574d02ba07f83.gif","goodsPrice":7492476,"goodsName":"呀show","exchangeNum":0}]
     */

    private String result;
    /**
     * id : 104
     * storeId : 24
     * goodsId : 407
     * integral : 1
     * state : 1
     * storeLimit : 30
     * userLimit : 20
     * goodsImage : Goods/d5/c1/d5c1658a2c8611e6abe874d02ba07f83.png
     * goodsPrice : 5
     * goodsName : 哦吼
     * exchangeNum : 0
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
        private long storeId;
        private long goodsId;
        private int integral;
        private int state;
        private int storeLimit;
        private int userLimit;
        private String goodsImage;
        private String goodsPrice;
        private String goodsName;
        private int exchangeNum;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public long getStoreId() {
            return storeId;
        }

        public void setStoreId(long storeId) {
            this.storeId = storeId;
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

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public int getStoreLimit() {
            return storeLimit;
        }

        public void setStoreLimit(int storeLimit) {
            this.storeLimit = storeLimit;
        }

        public int getUserLimit() {
            return userLimit;
        }

        public void setUserLimit(int userLimit) {
            this.userLimit = userLimit;
        }

        public String getGoodsImage() {
            return goodsImage;
        }

        public void setGoodsImage(String goodsImage) {
            this.goodsImage = goodsImage;
        }

        public String getGoodsPrice() {
            return goodsPrice;
        }

        public void setGoodsPrice(String goodsPrice) {
            this.goodsPrice = goodsPrice;
        }

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public int getExchangeNum() {
            return exchangeNum;
        }

        public void setExchangeNum(int exchangeNum) {
            this.exchangeNum = exchangeNum;
        }
    }
}
