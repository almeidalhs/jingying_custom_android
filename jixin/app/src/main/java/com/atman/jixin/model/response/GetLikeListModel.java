package com.atman.jixin.model.response;

import java.util.List;

/**
 * Created by tangbingliang on 16/10/24.
 */

public class GetLikeListModel {
    /**
     * result : 1
     * body : [{"id":24,"favId":229,"favTime":1477034633,"storeName":"店铺1","storeBanner":"Store/ed/90/ed90a295126311e6b6e674d02ba07f83.jpg","integral":0}]
     */

    private String result;
    /**
     * id : 24
     * favId : 229
     * favTime : 1477034633
     * storeName : 店铺1
     * storeBanner : Store/ed/90/ed90a295126311e6b6e674d02ba07f83.jpg
     * integral : 0
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
        private String sortLetters;  //显示数据拼音的首字母
        private long id;
        private int favId;
        private int favTime;
        private String storeName;
        private String storeBanner;
        private int integral;
        private boolean isSelect;

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        public String getSortLetters() {
            return sortLetters;
        }

        public void setSortLetters(String sortLetters) {
            this.sortLetters = sortLetters;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public int getFavId() {
            return favId;
        }

        public void setFavId(int favId) {
            this.favId = favId;
        }

        public int getFavTime() {
            return favTime;
        }

        public void setFavTime(int favTime) {
            this.favTime = favTime;
        }

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

        public int getIntegral() {
            return integral;
        }

        public void setIntegral(int integral) {
            this.integral = integral;
        }
    }
}
