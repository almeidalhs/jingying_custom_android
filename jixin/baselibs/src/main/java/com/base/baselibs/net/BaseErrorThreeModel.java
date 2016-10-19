package com.base.baselibs.net;

/**
 * 描述 访问错误的解析基础类
 * 作者 tangbingliang
 * 时间 16/7/6 10:50
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class BaseErrorThreeModel {
    /**
     * code : 1
     * message : 异常：javax.ws.rs.WebApplicationException
     */

    private RequestResultBean requestResult;

    public RequestResultBean getRequestResult() {
        return requestResult;
    }

    public void setRequestResult(RequestResultBean requestResult) {
        this.requestResult = requestResult;
    }

    public static class RequestResultBean {
        private int code;
        private String message;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
