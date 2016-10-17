package com.base.baselibs.net;

/**
 * 描述 访问错误的解析基础类
 * 作者 tangbingliang
 * 时间 16/7/6 10:50
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class BaseErrorTwoModel {
    /**
     * result : 0
     * body : {"message":"账号或密码错误"}
     */

    private String result;
    /**
     * message : 账号或密码错误
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
        private String message;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
