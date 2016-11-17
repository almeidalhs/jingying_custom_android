package com.atman.jixin.model.response;

/**
 * Created by tangbingliang on 16/11/17.
 */

public class GetVisitorsModel {
    /**
     * result : 1
     * body : {"userName":"je76d224cd537020","password":"343b1c4a3ea721b2d640fc8700db0f36"}
     */

    private String result;
    /**
     * userName : je76d224cd537020
     * password : 343b1c4a3ea721b2d640fc8700db0f36
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
        private String userName;
        private String password;

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
