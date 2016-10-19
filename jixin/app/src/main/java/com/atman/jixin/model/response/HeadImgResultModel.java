package com.atman.jixin.model.response;

import java.util.List;

/**
 * Created by tangbingliang on 16/10/19.
 */

public class HeadImgResultModel {
    /**
     * body : [{"name":"small.jpg","type":"image/jpeg","size":5101,"successful":true,"url":"Avatar/09/9d/099da97d95dc11e6a3ab74d02ba07f83.jpg"}]
     * result : 1
     */

    private String result;
    /**
     * name : small.jpg
     * type : image/jpeg
     * size : 5101
     * successful : true
     * url : Avatar/09/9d/099da97d95dc11e6a3ab74d02ba07f83.jpg
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
        private String name;
        private String type;
        private int size;
        private boolean successful;
        private String url;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public boolean isSuccessful() {
            return successful;
        }

        public void setSuccessful(boolean successful) {
            this.successful = successful;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
