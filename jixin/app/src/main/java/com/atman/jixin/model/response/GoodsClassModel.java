package com.atman.jixin.model.response;

import java.util.List;

/**
 * Created by tangbingliang on 16/10/31.
 */

public class GoodsClassModel {
    /**
     * result : 1
     * body : [{"id":385,"stcName":"素炒","stcParentId":0,"stcState":1,"storeId":68,"stcSort":1,"goodsCount":2,"goodsShowCount":2},{"id":386,"stcName":"荤菜","stcParentId":0,"stcState":1,"storeId":68,"stcSort":2,"goodsCount":1,"goodsShowCount":1},{"id":389,"stcName":"凉菜","stcParentId":0,"stcState":1,"storeId":68,"stcSort":3,"goodsCount":0,"goodsShowCount":0},{"id":390,"stcName":"甜点","stcParentId":0,"stcState":1,"storeId":68,"stcSort":4,"goodsCount":0,"goodsShowCount":0},{"id":392,"stcName":"酒水","stcParentId":0,"stcState":1,"storeId":68,"stcSort":6,"goodsCount":1,"goodsShowCount":1},{"id":393,"stcName":"招牌","stcParentId":0,"stcState":1,"storeId":68,"stcSort":7,"goodsCount":0,"goodsShowCount":0},{"id":394,"stcName":"大菜","stcParentId":0,"stcState":1,"storeId":68,"stcSort":8,"goodsCount":2,"goodsShowCount":2},{"id":395,"stcName":"汤品","stcParentId":0,"stcState":1,"storeId":68,"stcSort":9,"goodsCount":0,"goodsShowCount":0},{"id":396,"stcName":"小吃","stcParentId":0,"stcState":1,"storeId":68,"stcSort":10,"goodsCount":0,"goodsShowCount":0},{"id":397,"stcName":"主食","stcParentId":0,"stcState":1,"storeId":68,"stcSort":11,"goodsCount":0,"goodsShowCount":0}]
     */

    private String result;
    /**
     * id : 385
     * stcName : 素炒
     * stcParentId : 0
     * stcState : 1
     * storeId : 68
     * stcSort : 1
     * goodsCount : 2
     * goodsShowCount : 2
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
        private String stcName;
        private int stcParentId;
        private int stcState;
        private long storeId;
        private int stcSort;
        private int goodsCount;
        private int goodsShowCount;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getStcName() {
            return stcName;
        }

        public void setStcName(String stcName) {
            this.stcName = stcName;
        }

        public int getStcParentId() {
            return stcParentId;
        }

        public void setStcParentId(int stcParentId) {
            this.stcParentId = stcParentId;
        }

        public int getStcState() {
            return stcState;
        }

        public void setStcState(int stcState) {
            this.stcState = stcState;
        }

        public long getStoreId() {
            return storeId;
        }

        public void setStoreId(long storeId) {
            this.storeId = storeId;
        }

        public int getStcSort() {
            return stcSort;
        }

        public void setStcSort(int stcSort) {
            this.stcSort = stcSort;
        }

        public int getGoodsCount() {
            return goodsCount;
        }

        public void setGoodsCount(int goodsCount) {
            this.goodsCount = goodsCount;
        }

        public int getGoodsShowCount() {
            return goodsShowCount;
        }

        public void setGoodsShowCount(int goodsShowCount) {
            this.goodsShowCount = goodsShowCount;
        }
    }
}
