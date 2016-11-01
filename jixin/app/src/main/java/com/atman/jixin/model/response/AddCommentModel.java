package com.atman.jixin.model.response;

/**
 * Created by tangbingliang on 16/11/1.
 */

public class AddCommentModel {
    /**
     * result : 1
     * body : {"id":727,"objId":222,"userId":100004517,"content":"Ghh","type":2,"source":1}
     */

    private String result;
    /**
     * id : 727
     * objId : 222
     * userId : 100004517
     * content : Ghh
     * type : 2
     * source : 1
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
        private long objId;
        private long userId;
        private String content;
        private int type;
        private int source;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public long getObjId() {
            return objId;
        }

        public void setObjId(long objId) {
            this.objId = objId;
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

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getSource() {
            return source;
        }

        public void setSource(int source) {
            this.source = source;
        }
    }
}
