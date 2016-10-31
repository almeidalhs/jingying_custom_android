package com.atman.jixin.model.response;

import java.util.List;

/**
 * Created by tangbingliang on 16/10/25.
 */

public class GetChatServiceModel {
    /**
     * result : 1
     * body : {"id":68,"storeName":"happy lemon","storeAddress":"hkvcb哦工具","storeTel":"13500000011","fullStoreBanner":"http://192.168.1.141:8000/by/Store/91/54/9154cc8a848111e69a4274d02ba07f83.jpg","description":"哈哈哈哈个广告","openTime":"11000300","messageBean":{"targetType":1,"targetId":68,"targetName":"happy lemon","targetAvatar":"http://192.168.1.141:8000/by/Store/91/54/9154cc8a848111e69a4274d02ba07f83.jpg","sendTime":1477385485556,"content":"您好，请问需要什么服务吗？","type":0,"operaterList":[{"operaterId":1366,"operaterName":"wifi账号密码","operaterType":1},{"operaterId":1367,"operaterName":"呼叫服务","operaterType":1},{"operaterId":1368,"operaterName":"影音展示","operaterType":1},{"operaterId":1369,"operaterName":"菜品预览","operaterType":1}]}}
     */

    private String result;
    /**
     * id : 68
     * storeName : happy lemon
     * storeAddress : hkvcb哦工具
     * storeTel : 13500000011
     * fullStoreBanner : http://192.168.1.141:8000/by/Store/91/54/9154cc8a848111e69a4274d02ba07f83.jpg
     * description : 哈哈哈哈个广告
     * openTime : 11000300
     * messageBean : {"targetType":1,"targetId":68,"targetName":"happy lemon","targetAvatar":"http://192.168.1.141:8000/by/Store/91/54/9154cc8a848111e69a4274d02ba07f83.jpg","sendTime":1477385485556,"content":"您好，请问需要什么服务吗？","type":0,"operaterList":[{"operaterId":1366,"operaterName":"wifi账号密码","operaterType":1},{"operaterId":1367,"operaterName":"呼叫服务","operaterType":1},{"operaterId":1368,"operaterName":"影音展示","operaterType":1},{"operaterId":1369,"operaterName":"菜品预览","operaterType":1}]}
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
        private String storeAddress;
        private String storeTel;
        private String fullStoreBanner;
        private String description;
        private String openTime;
        /**
         * targetType : 1
         * targetId : 68
         * targetName : happy lemon
         * targetAvatar : http://192.168.1.141:8000/by/Store/91/54/9154cc8a848111e69a4274d02ba07f83.jpg
         * sendTime : 1477385485556
         * content : 您好，请问需要什么服务吗？
         * type : 0
         * operaterList : [{"operaterId":1366,"operaterName":"wifi账号密码","operaterType":1},{"operaterId":1367,"operaterName":"呼叫服务","operaterType":1},{"operaterId":1368,"operaterName":"影音展示","operaterType":1},{"operaterId":1369,"operaterName":"菜品预览","operaterType":1}]
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
             * operaterId : 1366
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
                private long operaterId;
                private String operaterName;
                private String operaterExtra;
                private String identifyChangeNotice;
                private int operaterType;
                private int identifyChange;
                private int identifyNeed;//操作此服务需要识别编号  1:需要 非1:不需要？
                private boolean structLanguage;

                public boolean isStructLanguage() {
                    return structLanguage;
                }

                public void setStructLanguage(boolean structLanguage) {
                    this.structLanguage = structLanguage;
                }

                public String getIdentifyChangeNotice() {
                    return identifyChangeNotice;
                }

                public void setIdentifyChangeNotice(String identifyChangeNotice) {
                    this.identifyChangeNotice = identifyChangeNotice;
                }

                public int getIdentifyChange() {
                    return identifyChange;
                }

                public void setIdentifyChange(int identifyChange) {
                    this.identifyChange = identifyChange;
                }

                public int getIdentifyNeed() {
                    return identifyNeed;
                }

                public void setIdentifyNeed(int identifyNeed) {
                    this.identifyNeed = identifyNeed;
                }

                public String getOperaterExtra() {
                    return operaterExtra;
                }

                public void setOperaterExtra(String operaterExtra) {
                    this.operaterExtra = operaterExtra;
                }

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
