package com.atman.jixin.model.response;

/**
 * Created by tangbingliang on 16/10/19.
 */

public class ResetPassWordResultModel {

    private String result;
    /**
     * message : 该手机号已注册“阿特门通行证”，请直接登录。如您忘记密码，请到登录页下点击“忘记密码？”找回。
     */

    private BodyEntity body;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public BodyEntity getBody() {
        return body;
    }

    public void setBody(BodyEntity body) {
        this.body = body;
    }

    public static class BodyEntity {
        private String message;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
