package com.atman.jixin.model.response;

import java.io.Serializable;
import java.util.List;

/**
 * Created by tangbingliang on 16/10/21.
 */

public class QRScanCodeModel implements Serializable {
    /**
     * result : 1
     * body : {"type":1,"storeBean":{"id":86,"storeName":"在家窝","storeAddress":"fhj","storeTel":"5555556","fullStoreBanner":"http://192.168.1.141:8000/by/Store/c9/d3/c9d35c498d2411e68b7f74d02ba07f83.jpg","description":"fhh","messageBean":{"targetType":1,"targetId":86,"targetName":"在家窝","targetAvatar":"http://192.168.1.141:8000/by/Store/c9/d3/c9d35c498d2411e68b7f74d02ba07f83.jpg","sendTime":1477031969004,"content":"您好，请问需要什么服务吗？","type":0,"operaterList":[{"operaterId":1685,"operaterName":"wifi账号密码","operaterType":1}]}},"messageBean":{"targetType":1,"targetId":86,"chatId":2917,"targetName":"在家窝","targetAvatar":"http://192.168.1.141:8000/by/Store/c9/d3/c9d35c498d2411e68b7f74d02ba07f83.jpg","sendTime":1477031968889,"content":"请选择下方wifi账号进行连接！","type":0,"operaterList":[{"operaterId":1685,"operaterName":"返回上级","operaterType":7},{"operaterId":141,"operaterName":"12345","operaterType":3,"operaterExtra":"qqqqq"}]}}
     */

    private String result;
    /**
     * type : 1
     * storeBean : {"id":86,"storeName":"在家窝","storeAddress":"fhj","storeTel":"5555556","fullStoreBanner":"http://192.168.1.141:8000/by/Store/c9/d3/c9d35c498d2411e68b7f74d02ba07f83.jpg","description":"fhh","messageBean":{"targetType":1,"targetId":86,"targetName":"在家窝","targetAvatar":"http://192.168.1.141:8000/by/Store/c9/d3/c9d35c498d2411e68b7f74d02ba07f83.jpg","sendTime":1477031969004,"content":"您好，请问需要什么服务吗？","type":0,"operaterList":[{"operaterId":1685,"operaterName":"wifi账号密码","operaterType":1}]}}
     * messageBean : {"targetType":1,"targetId":86,"chatId":2917,"targetName":"在家窝","targetAvatar":"http://192.168.1.141:8000/by/Store/c9/d3/c9d35c498d2411e68b7f74d02ba07f83.jpg","sendTime":1477031968889,"content":"请选择下方wifi账号进行连接！","type":0,"operaterList":[{"operaterId":1685,"operaterName":"返回上级","operaterType":7},{"operaterId":141,"operaterName":"12345","operaterType":3,"operaterExtra":"qqqqq"}]}
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

    public static class BodyBean implements Serializable {
        private int type;
        /**
         * id : 86
         * storeName : 在家窝
         * storeAddress : fhj
         * storeTel : 5555556
         * fullStoreBanner : http://192.168.1.141:8000/by/Store/c9/d3/c9d35c498d2411e68b7f74d02ba07f83.jpg
         * description : fhh
         * messageBean : {"targetType":1,"targetId":86,"targetName":"在家窝","targetAvatar":"http://192.168.1.141:8000/by/Store/c9/d3/c9d35c498d2411e68b7f74d02ba07f83.jpg","sendTime":1477031969004,"content":"您好，请问需要什么服务吗？","type":0,"operaterList":[{"operaterId":1685,"operaterName":"wifi账号密码","operaterType":1}]}
         */

        private StoreBeanBean storeBean;
        /**
         * targetType : 1
         * targetId : 86
         * chatId : 2917
         * targetName : 在家窝
         * targetAvatar : http://192.168.1.141:8000/by/Store/c9/d3/c9d35c498d2411e68b7f74d02ba07f83.jpg
         * sendTime : 1477031968889
         * content : 请选择下方wifi账号进行连接！
         * type : 0
         * operaterList : [{"operaterId":1685,"operaterName":"返回上级","operaterType":7},{"operaterId":141,"operaterName":"12345","operaterType":3,"operaterExtra":"qqqqq"}]
         */

        private MessageBeanBean messageBean;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public StoreBeanBean getStoreBean() {
            return storeBean;
        }

        public void setStoreBean(StoreBeanBean storeBean) {
            this.storeBean = storeBean;
        }

        public MessageBeanBean getMessageBean() {
            return messageBean;
        }

        public void setMessageBean(MessageBeanBean messageBean) {
            this.messageBean = messageBean;
        }

        public static class StoreBeanBean implements Serializable {
            private long id;
            private String storeName;
            private String storeAddress;
            private String storeTel;
            private String fullStoreBanner;
            private String description;
            /**
             * targetType : 1
             * targetId : 86
             * targetName : 在家窝
             * targetAvatar : http://192.168.1.141:8000/by/Store/c9/d3/c9d35c498d2411e68b7f74d02ba07f83.jpg
             * sendTime : 1477031969004
             * content : 您好，请问需要什么服务吗？
             * type : 0
             * operaterList : [{"operaterId":1685,"operaterName":"wifi账号密码","operaterType":1}]
             */

            private MessageBeanBean messageBean;

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
            }

            public String getStoreName() {
                return storeName;
            }

            public void setStoreName(String storeName) {
                this.storeName = storeName;
            }

            public String getStoreAddress() {
                return storeAddress;
            }

            public void setStoreAddress(String storeAddress) {
                this.storeAddress = storeAddress;
            }

            public String getStoreTel() {
                return storeTel;
            }

            public void setStoreTel(String storeTel) {
                this.storeTel = storeTel;
            }

            public String getFullStoreBanner() {
                return fullStoreBanner;
            }

            public void setFullStoreBanner(String fullStoreBanner) {
                this.fullStoreBanner = fullStoreBanner;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public MessageBeanBean getMessageBean() {
                return messageBean;
            }

            public void setMessageBean(MessageBeanBean messageBean) {
                this.messageBean = messageBean;
            }

            public static class MessageBeanBean implements Serializable {
                private int targetType;
                private long targetId;
                private String targetName;
                private String targetAvatar;
                private long sendTime;
                private String content;
                private int type;
                /**
                 * operaterId : 1685
                 * operaterName : wifi账号密码
                 * operaterType : 1
                 */

                private List<OperaterListBean> operaterList;

                public int getTargetType() {
                    return targetType;
                }

                public void setTargetType(int targetType) {
                    this.targetType = targetType;
                }

                public long getTargetId() {
                    return targetId;
                }

                public void setTargetId(long targetId) {
                    this.targetId = targetId;
                }

                public String getTargetName() {
                    return targetName;
                }

                public void setTargetName(String targetName) {
                    this.targetName = targetName;
                }

                public String getTargetAvatar() {
                    return targetAvatar;
                }

                public void setTargetAvatar(String targetAvatar) {
                    this.targetAvatar = targetAvatar;
                }

                public long getSendTime() {
                    return sendTime;
                }

                public void setSendTime(long sendTime) {
                    this.sendTime = sendTime;
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

                public List<OperaterListBean> getOperaterList() {
                    return operaterList;
                }

                public void setOperaterList(List<OperaterListBean> operaterList) {
                    this.operaterList = operaterList;
                }

                public static class OperaterListBean implements Serializable {
                    private long operaterId;
                    private String operaterName;
                    private int operaterType;

                    public long getOperaterId() {
                        return operaterId;
                    }

                    public void setOperaterId(long operaterId) {
                        this.operaterId = operaterId;
                    }

                    public String getOperaterName() {
                        return operaterName;
                    }

                    public void setOperaterName(String operaterName) {
                        this.operaterName = operaterName;
                    }

                    public int getOperaterType() {
                        return operaterType;
                    }

                    public void setOperaterType(int operaterType) {
                        this.operaterType = operaterType;
                    }
                }
            }
        }

        public static class MessageBeanBean implements Serializable {
            private int targetType;
            private long targetId;
            private long chatId;
            private String targetName;
            private String targetAvatar;
            private long sendTime;
            private String content;
            private int type;
            /**
             * operaterId : 1685
             * operaterName : 返回上级
             * operaterType : 7
             */

            private List<OperaterListBean> operaterList;

            public int getTargetType() {
                return targetType;
            }

            public void setTargetType(int targetType) {
                this.targetType = targetType;
            }

            public long getTargetId() {
                return targetId;
            }

            public void setTargetId(long targetId) {
                this.targetId = targetId;
            }

            public long getChatId() {
                return chatId;
            }

            public void setChatId(long chatId) {
                this.chatId = chatId;
            }

            public String getTargetName() {
                return targetName;
            }

            public void setTargetName(String targetName) {
                this.targetName = targetName;
            }

            public String getTargetAvatar() {
                return targetAvatar;
            }

            public void setTargetAvatar(String targetAvatar) {
                this.targetAvatar = targetAvatar;
            }

            public long getSendTime() {
                return sendTime;
            }

            public void setSendTime(long sendTime) {
                this.sendTime = sendTime;
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

            public List<OperaterListBean> getOperaterList() {
                return operaterList;
            }

            public void setOperaterList(List<OperaterListBean> operaterList) {
                this.operaterList = operaterList;
            }

            public static class OperaterListBean implements Serializable {
                private long operaterId;
                private String operaterName;
                private int operaterType;

                public long getOperaterId() {
                    return operaterId;
                }

                public void setOperaterId(long operaterId) {
                    this.operaterId = operaterId;
                }

                public String getOperaterName() {
                    return operaterName;
                }

                public void setOperaterName(String operaterName) {
                    this.operaterName = operaterName;
                }

                public int getOperaterType() {
                    return operaterType;
                }

                public void setOperaterType(int operaterType) {
                    this.operaterType = operaterType;
                }
            }
        }
    }
}
