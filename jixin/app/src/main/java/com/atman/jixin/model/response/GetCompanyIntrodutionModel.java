package com.atman.jixin.model.response;

import java.util.List;

/**
 * Created by tangbingliang on 16/11/1.
 */

public class GetCompanyIntrodutionModel {
    /**
     * result : 1
     * body : {"id":222,"storeId":34,"title":"自己娃在何方？","state":1,"sort":1,"fileList":[{"id":883,"businessInfoId":222,"fileUrl":"introduction/1f/f8/1ff8509b96ab11e6beb374d02ba07f83.mp4","remark":"tududududud不出那么你只能像顶部的组合支持他总想打破了这别张口容许别人星空人像你不懂不怎地怎么东西呢星空下肚某些人了徐健您的你对你任性女性如何徐健哦恶心了新的科学你让消灭悉尼贼呢哦何止热销楼盘则别致\n   恶心的戒指季节性姐姐喜欢邪恶鸡贼好你贼好邪恶戒指恶心戒指则会恶化\n\n 小复古个性别的好处是筹备处恶心人的内心戒指恶心别致哦不行诶奔走表现而你只恶心恶心不别想别指责你必须信息恶不恶心\n ","type":3,"length":"0","showImg":"introduction/1f/89/1f893b1a96ab11e6beb374d02ba07f83.jpg","state":1}]}
     */

    private String result;
    /**
     * id : 222
     * storeId : 34
     * title : 自己娃在何方？
     * state : 1
     * sort : 1
     * fileList : [{"id":883,"businessInfoId":222,"fileUrl":"introduction/1f/f8/1ff8509b96ab11e6beb374d02ba07f83.mp4","remark":"tududududud不出那么你只能像顶部的组合支持他总想打破了这别张口容许别人星空人像你不懂不怎地怎么东西呢星空下肚某些人了徐健您的你对你任性女性如何徐健哦恶心了新的科学你让消灭悉尼贼呢哦何止热销楼盘则别致\n   恶心的戒指季节性姐姐喜欢邪恶鸡贼好你贼好邪恶戒指恶心戒指则会恶化\n\n 小复古个性别的好处是筹备处恶心人的内心戒指恶心别致哦不行诶奔走表现而你只恶心恶心不别想别指责你必须信息恶不恶心\n ","type":3,"length":"0","showImg":"introduction/1f/89/1f893b1a96ab11e6beb374d02ba07f83.jpg","state":1}]
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
        private long storeId;
        private String title;
        private int state;
        private int sort;
        /**
         * id : 883
         * businessInfoId : 222
         * fileUrl : introduction/1f/f8/1ff8509b96ab11e6beb374d02ba07f83.mp4
         * remark : tududududud不出那么你只能像顶部的组合支持他总想打破了这别张口容许别人星空人像你不懂不怎地怎么东西呢星空下肚某些人了徐健您的你对你任性女性如何徐健哦恶心了新的科学你让消灭悉尼贼呢哦何止热销楼盘则别致
         恶心的戒指季节性姐姐喜欢邪恶鸡贼好你贼好邪恶戒指恶心戒指则会恶化

         小复古个性别的好处是筹备处恶心人的内心戒指恶心别致哦不行诶奔走表现而你只恶心恶心不别想别指责你必须信息恶不恶心

         * type : 3
         * length : 0
         * showImg : introduction/1f/89/1f893b1a96ab11e6beb374d02ba07f83.jpg
         * state : 1
         */

        private List<FileListBean> fileList;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public long getStoreId() {
            return storeId;
        }

        public void setStoreId(long storeId) {
            this.storeId = storeId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public List<FileListBean> getFileList() {
            return fileList;
        }

        public void setFileList(List<FileListBean> fileList) {
            this.fileList = fileList;
        }

        public static class FileListBean {
            private long id;
            private long businessInfoId;
            private String fileUrl;
            private String remark;
            private int type;
            private String length;
            private String showImg;
            private int state;

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
            }

            public long getBusinessInfoId() {
                return businessInfoId;
            }

            public void setBusinessInfoId(long businessInfoId) {
                this.businessInfoId = businessInfoId;
            }

            public String getFileUrl() {
                return fileUrl;
            }

            public void setFileUrl(String fileUrl) {
                this.fileUrl = fileUrl;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getLength() {
                return length;
            }

            public void setLength(String length) {
                this.length = length;
            }

            public String getShowImg() {
                return showImg;
            }

            public void setShowImg(String showImg) {
                this.showImg = showImg;
            }

            public int getState() {
                return state;
            }

            public void setState(int state) {
                this.state = state;
            }
        }
    }
}
