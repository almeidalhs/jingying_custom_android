package com.atman.jixin.model.response;

import java.util.List;

/**
 * Created by tangbingliang on 16/10/28.
 */

public class GetMessageModel {
    /**
     * messageType : 2
     * content : {"audio_duration":0,"chatId":8689,"eventAction":{"actionType":4,"couponId":0,"enterpriseId":0,"goodId":407,"storeId":0},"imageT_back":"Goods/d5/c1/d5c1658a2c8611e6abe874d02ba07f83.png","imageT_icon":"adchat_imagetext_icon_good","imageT_title":"哦吼","sendTime":1477641298586,"targetAvatar":"Avatar/ed/db/eddb771f133b11e6975374d02ba07f83.jpg","targetId":24,"targetName":"店铺1","targetType":1,"type":5}
     */

    private int messageType;
    /**
     * audio_duration : 0
     * chatId : 8689
     * eventAction : {"actionType":4,"couponId":0,"enterpriseId":0,"goodId":407,"storeId":0}
     * imageT_back : Goods/d5/c1/d5c1658a2c8611e6abe874d02ba07f83.png
     * imageT_icon : adchat_imagetext_icon_good
     * imageT_title : 哦吼
     * sendTime : 1477641298586
     * targetAvatar : Avatar/ed/db/eddb771f133b11e6975374d02ba07f83.jpg
     * targetId : 24
     * targetName : 店铺1
     * targetType : 1
     * type : 5
     */

    private ContentBean content;

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public ContentBean getContent() {
        return content;
    }

    public void setContent(ContentBean content) {
        this.content = content;
    }

    public static class ContentBean {
        private int audio_duration;
        private long chatId;
        /**
         * actionType : 4
         * couponId : 0
         * enterpriseId : 0
         * goodId : 407
         * storeId : 0
         */

        private EventActionBean eventAction;
        private String imageT_back;
        private String imageT_icon;
        private String imageT_title;
        private String content;
        private long sendTime;
        private String targetAvatar;
        private long targetId;
        private String targetName;
        private int targetType;
        private int type;
        private List<GetChatServiceModel.BodyBean.MessageBeanBean.OperaterListBean> operaterList;

        public List<GetChatServiceModel.BodyBean.MessageBeanBean.OperaterListBean> getOperaterList() {
            return operaterList;
        }

        public void setOperaterList(List<GetChatServiceModel.BodyBean.MessageBeanBean.OperaterListBean> operaterList) {
            this.operaterList = operaterList;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            content = content;
        }

        public int getAudio_duration() {
            return audio_duration;
        }

        public void setAudio_duration(int audio_duration) {
            this.audio_duration = audio_duration;
        }

        public long getChatId() {
            return chatId;
        }

        public void setChatId(long chatId) {
            this.chatId = chatId;
        }

        public EventActionBean getEventAction() {
            return eventAction;
        }

        public void setEventAction(EventActionBean eventAction) {
            this.eventAction = eventAction;
        }

        public String getImageT_back() {
            return imageT_back;
        }

        public void setImageT_back(String imageT_back) {
            this.imageT_back = imageT_back;
        }

        public String getImageT_icon() {
            return imageT_icon;
        }

        public void setImageT_icon(String imageT_icon) {
            this.imageT_icon = imageT_icon;
        }

        public String getImageT_title() {
            return imageT_title;
        }

        public void setImageT_title(String imageT_title) {
            this.imageT_title = imageT_title;
        }

        public long getSendTime() {
            return sendTime;
        }

        public void setSendTime(long sendTime) {
            this.sendTime = sendTime;
        }

        public String getTargetAvatar() {
            return targetAvatar;
        }

        public void setTargetAvatar(String targetAvatar) {
            this.targetAvatar = targetAvatar;
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

        public int getTargetType() {
            return targetType;
        }

        public void setTargetType(int targetType) {
            this.targetType = targetType;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public static class EventActionBean {
            private int actionType;
            private long couponId;
            private int enterpriseId;
            private long goodId;
            private long storeId;

            public int getActionType() {
                return actionType;
            }

            public void setActionType(int actionType) {
                this.actionType = actionType;
            }

            public long getCouponId() {
                return couponId;
            }

            public void setCouponId(long couponId) {
                this.couponId = couponId;
            }

            public int getEnterpriseId() {
                return enterpriseId;
            }

            public void setEnterpriseId(int enterpriseId) {
                this.enterpriseId = enterpriseId;
            }

            public long getGoodId() {
                return goodId;
            }

            public void setGoodId(long goodId) {
                this.goodId = goodId;
            }

            public long getStoreId() {
                return storeId;
            }

            public void setStoreId(long storeId) {
                this.storeId = storeId;
            }
        }
    }
}
