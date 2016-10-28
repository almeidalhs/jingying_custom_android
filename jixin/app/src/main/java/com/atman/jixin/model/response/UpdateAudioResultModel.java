package com.atman.jixin.model.response;

import java.util.List;

/**
 * Created by tangbingliang on 16/10/28.
 */

public class UpdateAudioResultModel {
    /**
     * body : [{"name":"1477623720623.aac","type":"audio/aac","size":4806,"successful":true,"url":"ChatA/cc/d9/ccd993b39cba11e69f0574d02ba07f83.aac"}]
     * result : 1
     */

    private String result;
    /**
     * name : 1477623720623.aac
     * type : audio/aac
     * size : 4806
     * successful : true
     * url : ChatA/cc/d9/ccd993b39cba11e69f0574d02ba07f83.aac
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
