package com.atman.jixin.model.response;

/**
 * Created by tangbingliang on 16/12/20.
 */

public class QRTouristModel {
    /**
     * result : 1
     * body : {"type":201}
     */

    private String result;
    /**
     * type : 201
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
        private int type;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}
