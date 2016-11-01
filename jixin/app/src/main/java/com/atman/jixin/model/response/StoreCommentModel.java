package com.atman.jixin.model.response;

import java.util.List;

/**
 * Created by tangbingliang on 16/11/1.
 */

public class StoreCommentModel {
    /**
     * result : 1
     * body : [{"id":725,"storeId":0,"userId":100004517,"content":"[得意]","addTime":1477970320,"likeNum":1,"imgList":[],"userName":"88只是c端用户","userAvatar":"Avatar/51/33/5133ba5d9b1d11e681df74d02ba07f83.jpg","isLike":1,"isMember":1,"smId":1143}]
     */

    private String result;
    /**
     * id : 725
     * storeId : 0
     * userId : 100004517
     * content : [得意]
     * addTime : 1477970320
     * likeNum : 1
     * imgList : []
     * userName : 88只是c端用户
     * userAvatar : Avatar/51/33/5133ba5d9b1d11e681df74d02ba07f83.jpg
     * isLike : 1
     * isMember : 1
     * smId : 1143
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
        private long userId;
        private String content;
        private long addTime;
        private int likeNum;
        private String userName;
        private String userAvatar;
        private int isLike;
        private int isMember;
        private long smId;
        private List<?> imgList;

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

        public long getUserId() {
            return userId;
        }

        public void setUserId(long userId) {
            this.userId = userId;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public long getAddTime() {
            return addTime;
        }

        public void setAddTime(long addTime) {
            this.addTime = addTime;
        }

        public int getLikeNum() {
            return likeNum;
        }

        public void setLikeNum(int likeNum) {
            this.likeNum = likeNum;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserAvatar() {
            return userAvatar;
        }

        public void setUserAvatar(String userAvatar) {
            this.userAvatar = userAvatar;
        }

        public int getIsLike() {
            return isLike;
        }

        public void setIsLike(int isLike) {
            this.isLike = isLike;
        }

        public int getIsMember() {
            return isMember;
        }

        public void setIsMember(int isMember) {
            this.isMember = isMember;
        }

        public long getSmId() {
            return smId;
        }

        public void setSmId(long smId) {
            this.smId = smId;
        }

        public List<?> getImgList() {
            return imgList;
        }

        public void setImgList(List<?> imgList) {
            this.imgList = imgList;
        }
    }
}
