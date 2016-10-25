package com.base.baselibs.net;

/**
 * 描述 访问错误的解析基础类
 * 作者 tangbingliang
 * 时间 16/7/6 10:50
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class BaseErrorModel {
    /**
     * result : 0
     * body : {"error":"User offline","error_code":"20030","error_description":"您的帐号已在别处登录。\n如非您本人操作，请尽快修改您的密码!"}
     */

    private String result;
    /**
     * error : User offline
     * error_code : 20030
     * error_description : 您的帐号已在别处登录。
     如非您本人操作，请尽快修改您的密码!
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
        private String error;
        private String error_code;
        private String error_description;

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }

        public String getError_code() {
            return error_code;
        }

        public void setError_code(String error_code) {
            this.error_code = error_code;
        }

        public String getError_description() {
            return error_description;
        }

        public void setError_description(String error_description) {
            this.error_description = error_description;
        }
    }
}
