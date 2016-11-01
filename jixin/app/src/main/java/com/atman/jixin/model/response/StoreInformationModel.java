package com.atman.jixin.model.response;

import java.util.List;

/**
 * Created by tangbingliang on 16/11/1.
 */

public class StoreInformationModel {
    /**
     * result : 1
     * body : {"id":34,"storeName":"美女集中营","storeAvatar":"Avatar/e3/d9/e3d968bb9fe211e6863974d02ba07f83.jpg","storeAddress":"客户反馈地给我改个名哦呵呵你秘密哦哦你哦破你 Mr 你规模哦婆婆婆婆","storeTel":"13860000028","fullStoreBanner":"http://192.168.1.141:8000/by/Store/10/76/107661b97fc411e6849f74d02ba07f83.jpg","description":"这是美女集合的夏令营","storeWorkingTime":"8:00 ~ 21:00","openTime":"23482021","messageBean":{"targetType":1,"targetId":34,"targetName":"美女集中营","targetAvatar":"Avatar/e3/d9/e3d968bb9fe211e6863974d02ba07f83.jpg","sendTime":1477982804558,"content":"您好，请问需要什么服务吗？","type":0,"operaterList":[{"operaterId":1271,"operaterName":"wifi账号密码","operaterType":1},{"operaterId":1272,"operaterName":"呼叫服务","operaterType":1},{"operaterId":1273,"operaterName":"影音展示","operaterType":1},{"operaterId":1274,"operaterName":"商品预览","operaterType":1}]}}
     */

    private String result;
    /**
     * id : 34
     * storeName : 美女集中营
     * storeAvatar : Avatar/e3/d9/e3d968bb9fe211e6863974d02ba07f83.jpg
     * storeAddress : 客户反馈地给我改个名哦呵呵你秘密哦哦你哦破你 Mr 你规模哦婆婆婆婆
     * storeTel : 13860000028
     * fullStoreBanner : http://192.168.1.141:8000/by/Store/10/76/107661b97fc411e6849f74d02ba07f83.jpg
     * description : 这是美女集合的夏令营
     * storeWorkingTime : 8:00 ~ 21:00
     * openTime : 23482021
     * messageBean : {"targetType":1,"targetId":34,"targetName":"美女集中营","targetAvatar":"Avatar/e3/d9/e3d968bb9fe211e6863974d02ba07f83.jpg","sendTime":1477982804558,"content":"您好，请问需要什么服务吗？","type":0,"operaterList":[{"operaterId":1271,"operaterName":"wifi账号密码","operaterType":1},{"operaterId":1272,"operaterName":"呼叫服务","operaterType":1},{"operaterId":1273,"operaterName":"影音展示","operaterType":1},{"operaterId":1274,"operaterName":"商品预览","operaterType":1}]}
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
        private String storeName;
        private String storeAvatar;
        private String storeAddress;
        private String storeTel;
        private String fullStoreBanner;
        private String description;
        private String storeWorkingTime;
        private String openTime;
        /**
         * targetType : 1
         * targetId : 34
         * targetName : 美女集中营
         * targetAvatar : Avatar/e3/d9/e3d968bb9fe211e6863974d02ba07f83.jpg
         * sendTime : 1477982804558
         * content : 您好，请问需要什么服务吗？
         * type : 0
         * operaterList : [{"operaterId":1271,"operaterName":"wifi账号密码","operaterType":1},{"operaterId":1272,"operaterName":"呼叫服务","operaterType":1},{"operaterId":1273,"operaterName":"影音展示","operaterType":1},{"operaterId":1274,"operaterName":"商品预览","operaterType":1}]
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

        public String getStoreAvatar() {
            return storeAvatar;
        }

        public void setStoreAvatar(String storeAvatar) {
            this.storeAvatar = storeAvatar;
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

        public String getStoreWorkingTime() {
            return storeWorkingTime;
        }

        public void setStoreWorkingTime(String storeWorkingTime) {
            this.storeWorkingTime = storeWorkingTime;
        }

        public String getOpenTime() {
            return openTime;
        }

        public void setOpenTime(String openTime) {
            this.openTime = openTime;
        }

        public MessageBeanBean getMessageBean() {
            return messageBean;
        }

        public void setMessageBean(MessageBeanBean messageBean) {
            this.messageBean = messageBean;
        }

        public static class MessageBeanBean {
            private int targetType;
            private long targetId;
            private String targetName;
            private String targetAvatar;
            private long sendTime;
            private String content;
            private int type;
            /**
             * operaterId : 1271
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

            public static class OperaterListBean {
                private int operaterId;
                private String operaterName;
                private int operaterType;

                public int getOperaterId() {
                    return operaterId;
                }

                public void setOperaterId(int operaterId) {
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
