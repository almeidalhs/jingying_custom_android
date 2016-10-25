package com.atman.jixin.model.response;

import java.util.List;

/**
 * Created by tangbingliang on 16/10/25.
 */

public class GoodsDetailModel {
    /**
     * result : 1
     * body : {"id":407,"goodsName":"哦吼","gcName":"","brandId":0,"typeId":0,"storeId":24,"specOpen":0,"specName":"","goodsImage":"Goods/d5/c1/d5c1658a2c8611e6abe874d02ba07f83.png","fullGoodsImage":"http://192.168.1.141:8000/by/Goods/d5/c1/d5c1658a2c8611e6abe874d02ba07f83.png","goodsImageMore":"Goods/d5/c1/d5c1658a2c8611e6abe874d02ba07f83.png,Goods/d5/c4/d5c424ab2c8611e6abe874d02ba07f83.png,Goods/d5/c6/d5c6bcbc2c8611e6abe874d02ba07f83.jpg,Goods/d5/c7/d5c7f53d2c8611e6abe874d02ba07f83.png,Goods/d5/ca/d5cab45e2c8611e6abe874d02ba07f83.jpg","goodsStorePrice":1,"goodsStorePriceInterval":"","goodsSerial":"","goodsShow":1,"goodsClick":1,"goodsState":0,"goodsCommend":0,"goodsAddTime":0,"goodsKeywords":"","goodsDescription":"","goodsBody":"","goodsSpec":"也","goodsStartTime":0,"goodsEndTime":0,"goodsForm":1,"transportId":0,"pyPrice":0,"kdPrice":0,"esPrice":0,"cityId":0,"provinceId":0,"goodsStoreState":0,"commentnum":0,"salenum":0,"goodsCollect":0,"goodsGoldNum":0,"goodsIsztc":0,"goodsZtcState":1,"goodsZtcStartDate":0,"groupFlag":0,"groupPrice":0,"xianshiFlag":0,"xianshiDiscount":0,"goodsTransfeeCharge":0,"state":0,"memberMobile":"15676645555","stcId":286,"stcName":"女鞋","price":5,"storage":0,"goodsWebUrl":"http://www.atman.com:8000/cp/407","goodsImageMoreList":["http://192.168.1.141:8000/by/Goods/d5/c1/d5c1658a2c8611e6abe874d02ba07f83.png","http://192.168.1.141:8000/by/Goods/d5/c4/d5c424ab2c8611e6abe874d02ba07f83.png","http://192.168.1.141:8000/by/Goods/d5/c6/d5c6bcbc2c8611e6abe874d02ba07f83.jpg","http://192.168.1.141:8000/by/Goods/d5/c7/d5c7f53d2c8611e6abe874d02ba07f83.png","http://192.168.1.141:8000/by/Goods/d5/ca/d5cab45e2c8611e6abe874d02ba07f83.jpg"],"goodsSpecBeanList":[{"id":467,"goodsId":407,"specGoodsPrice":5,"specGoodsStorage":0,"specSalenum":0,"state":0}],"goodsImageUrlPre":""}
     */

    private String result;
    /**
     * id : 407
     * goodsName : 哦吼
     * gcName :
     * brandId : 0
     * typeId : 0
     * storeId : 24
     * specOpen : 0
     * specName :
     * goodsImage : Goods/d5/c1/d5c1658a2c8611e6abe874d02ba07f83.png
     * fullGoodsImage : http://192.168.1.141:8000/by/Goods/d5/c1/d5c1658a2c8611e6abe874d02ba07f83.png
     * goodsImageMore : Goods/d5/c1/d5c1658a2c8611e6abe874d02ba07f83.png,Goods/d5/c4/d5c424ab2c8611e6abe874d02ba07f83.png,Goods/d5/c6/d5c6bcbc2c8611e6abe874d02ba07f83.jpg,Goods/d5/c7/d5c7f53d2c8611e6abe874d02ba07f83.png,Goods/d5/ca/d5cab45e2c8611e6abe874d02ba07f83.jpg
     * goodsStorePrice : 1
     * goodsStorePriceInterval :
     * goodsSerial :
     * goodsShow : 1
     * goodsClick : 1
     * goodsState : 0
     * goodsCommend : 0
     * goodsAddTime : 0
     * goodsKeywords :
     * goodsDescription :
     * goodsBody :
     * goodsSpec : 也
     * goodsStartTime : 0
     * goodsEndTime : 0
     * goodsForm : 1
     * transportId : 0
     * pyPrice : 0
     * kdPrice : 0
     * esPrice : 0
     * cityId : 0
     * provinceId : 0
     * goodsStoreState : 0
     * commentnum : 0
     * salenum : 0
     * goodsCollect : 0
     * goodsGoldNum : 0
     * goodsIsztc : 0
     * goodsZtcState : 1
     * goodsZtcStartDate : 0
     * groupFlag : 0
     * groupPrice : 0
     * xianshiFlag : 0
     * xianshiDiscount : 0
     * goodsTransfeeCharge : 0
     * state : 0
     * memberMobile : 15676645555
     * stcId : 286
     * stcName : 女鞋
     * price : 5
     * storage : 0
     * goodsWebUrl : http://www.atman.com:8000/cp/407
     * goodsImageMoreList : ["http://192.168.1.141:8000/by/Goods/d5/c1/d5c1658a2c8611e6abe874d02ba07f83.png","http://192.168.1.141:8000/by/Goods/d5/c4/d5c424ab2c8611e6abe874d02ba07f83.png","http://192.168.1.141:8000/by/Goods/d5/c6/d5c6bcbc2c8611e6abe874d02ba07f83.jpg","http://192.168.1.141:8000/by/Goods/d5/c7/d5c7f53d2c8611e6abe874d02ba07f83.png","http://192.168.1.141:8000/by/Goods/d5/ca/d5cab45e2c8611e6abe874d02ba07f83.jpg"]
     * goodsSpecBeanList : [{"id":467,"goodsId":407,"specGoodsPrice":5,"specGoodsStorage":0,"specSalenum":0,"state":0}]
     * goodsImageUrlPre :
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
        private String goodsName;
        private String gcName;
        private int brandId;
        private int typeId;
        private long storeId;
        private int specOpen;
        private String specName;
        private String goodsImage;
        private String fullGoodsImage;
        private String goodsImageMore;
        private String goodsStorePrice;
        private String goodsStorePriceInterval;
        private String goodsSerial;
        private int goodsShow;
        private int goodsClick;
        private int goodsState;
        private int goodsCommend;
        private long goodsAddTime;
        private String goodsKeywords;
        private String goodsDescription;
        private String goodsBody;
        private String goodsSpec;
        private long goodsStartTime;
        private long goodsEndTime;
        private int goodsForm;
        private int transportId;
        private String pyPrice;
        private String kdPrice;
        private String esPrice;
        private int cityId;
        private int provinceId;
        private int goodsStoreState;
        private int commentnum;
        private int salenum;
        private int goodsCollect;
        private int goodsGoldNum;
        private int goodsIsztc;
        private int goodsZtcState;
        private int goodsZtcStartDate;
        private int groupFlag;
        private String groupPrice;
        private int xianshiFlag;
        private int xianshiDiscount;
        private int goodsTransfeeCharge;
        private int state;
        private String memberMobile;
        private int stcId;
        private String stcName;
        private String price;
        private int storage;
        private String goodsWebUrl;
        private String goodsImageUrlPre;
        private List<String> goodsImageMoreList;
        /**
         * id : 467
         * goodsId : 407
         * specGoodsPrice : 5
         * specGoodsStorage : 0
         * specSalenum : 0
         * state : 0
         */

        private List<GoodsSpecBeanListBean> goodsSpecBeanList;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public String getGcName() {
            return gcName;
        }

        public void setGcName(String gcName) {
            this.gcName = gcName;
        }

        public int getBrandId() {
            return brandId;
        }

        public void setBrandId(int brandId) {
            this.brandId = brandId;
        }

        public int getTypeId() {
            return typeId;
        }

        public void setTypeId(int typeId) {
            this.typeId = typeId;
        }

        public long getStoreId() {
            return storeId;
        }

        public void setStoreId(long storeId) {
            this.storeId = storeId;
        }

        public int getSpecOpen() {
            return specOpen;
        }

        public void setSpecOpen(int specOpen) {
            this.specOpen = specOpen;
        }

        public String getSpecName() {
            return specName;
        }

        public void setSpecName(String specName) {
            this.specName = specName;
        }

        public String getGoodsImage() {
            return goodsImage;
        }

        public void setGoodsImage(String goodsImage) {
            this.goodsImage = goodsImage;
        }

        public String getFullGoodsImage() {
            return fullGoodsImage;
        }

        public void setFullGoodsImage(String fullGoodsImage) {
            this.fullGoodsImage = fullGoodsImage;
        }

        public String getGoodsImageMore() {
            return goodsImageMore;
        }

        public void setGoodsImageMore(String goodsImageMore) {
            this.goodsImageMore = goodsImageMore;
        }

        public String getGoodsStorePrice() {
            return goodsStorePrice;
        }

        public void setGoodsStorePrice(String goodsStorePrice) {
            this.goodsStorePrice = goodsStorePrice;
        }

        public String getGoodsStorePriceInterval() {
            return goodsStorePriceInterval;
        }

        public void setGoodsStorePriceInterval(String goodsStorePriceInterval) {
            this.goodsStorePriceInterval = goodsStorePriceInterval;
        }

        public String getGoodsSerial() {
            return goodsSerial;
        }

        public void setGoodsSerial(String goodsSerial) {
            this.goodsSerial = goodsSerial;
        }

        public int getGoodsShow() {
            return goodsShow;
        }

        public void setGoodsShow(int goodsShow) {
            this.goodsShow = goodsShow;
        }

        public int getGoodsClick() {
            return goodsClick;
        }

        public void setGoodsClick(int goodsClick) {
            this.goodsClick = goodsClick;
        }

        public int getGoodsState() {
            return goodsState;
        }

        public void setGoodsState(int goodsState) {
            this.goodsState = goodsState;
        }

        public int getGoodsCommend() {
            return goodsCommend;
        }

        public void setGoodsCommend(int goodsCommend) {
            this.goodsCommend = goodsCommend;
        }

        public long getGoodsAddTime() {
            return goodsAddTime;
        }

        public void setGoodsAddTime(long goodsAddTime) {
            this.goodsAddTime = goodsAddTime;
        }

        public String getGoodsKeywords() {
            return goodsKeywords;
        }

        public void setGoodsKeywords(String goodsKeywords) {
            this.goodsKeywords = goodsKeywords;
        }

        public String getGoodsDescription() {
            return goodsDescription;
        }

        public void setGoodsDescription(String goodsDescription) {
            this.goodsDescription = goodsDescription;
        }

        public String getGoodsBody() {
            return goodsBody;
        }

        public void setGoodsBody(String goodsBody) {
            this.goodsBody = goodsBody;
        }

        public String getGoodsSpec() {
            return goodsSpec;
        }

        public void setGoodsSpec(String goodsSpec) {
            this.goodsSpec = goodsSpec;
        }

        public long getGoodsStartTime() {
            return goodsStartTime;
        }

        public void setGoodsStartTime(long goodsStartTime) {
            this.goodsStartTime = goodsStartTime;
        }

        public long getGoodsEndTime() {
            return goodsEndTime;
        }

        public void setGoodsEndTime(long goodsEndTime) {
            this.goodsEndTime = goodsEndTime;
        }

        public int getGoodsForm() {
            return goodsForm;
        }

        public void setGoodsForm(int goodsForm) {
            this.goodsForm = goodsForm;
        }

        public int getTransportId() {
            return transportId;
        }

        public void setTransportId(int transportId) {
            this.transportId = transportId;
        }

        public String getPyPrice() {
            return pyPrice;
        }

        public void setPyPrice(String pyPrice) {
            this.pyPrice = pyPrice;
        }

        public String getKdPrice() {
            return kdPrice;
        }

        public void setKdPrice(String kdPrice) {
            this.kdPrice = kdPrice;
        }

        public String getEsPrice() {
            return esPrice;
        }

        public void setEsPrice(String esPrice) {
            this.esPrice = esPrice;
        }

        public int getCityId() {
            return cityId;
        }

        public void setCityId(int cityId) {
            this.cityId = cityId;
        }

        public int getProvinceId() {
            return provinceId;
        }

        public void setProvinceId(int provinceId) {
            this.provinceId = provinceId;
        }

        public int getGoodsStoreState() {
            return goodsStoreState;
        }

        public void setGoodsStoreState(int goodsStoreState) {
            this.goodsStoreState = goodsStoreState;
        }

        public int getCommentnum() {
            return commentnum;
        }

        public void setCommentnum(int commentnum) {
            this.commentnum = commentnum;
        }

        public int getSalenum() {
            return salenum;
        }

        public void setSalenum(int salenum) {
            this.salenum = salenum;
        }

        public int getGoodsCollect() {
            return goodsCollect;
        }

        public void setGoodsCollect(int goodsCollect) {
            this.goodsCollect = goodsCollect;
        }

        public int getGoodsGoldNum() {
            return goodsGoldNum;
        }

        public void setGoodsGoldNum(int goodsGoldNum) {
            this.goodsGoldNum = goodsGoldNum;
        }

        public int getGoodsIsztc() {
            return goodsIsztc;
        }

        public void setGoodsIsztc(int goodsIsztc) {
            this.goodsIsztc = goodsIsztc;
        }

        public int getGoodsZtcState() {
            return goodsZtcState;
        }

        public void setGoodsZtcState(int goodsZtcState) {
            this.goodsZtcState = goodsZtcState;
        }

        public int getGoodsZtcStartDate() {
            return goodsZtcStartDate;
        }

        public void setGoodsZtcStartDate(int goodsZtcStartDate) {
            this.goodsZtcStartDate = goodsZtcStartDate;
        }

        public int getGroupFlag() {
            return groupFlag;
        }

        public void setGroupFlag(int groupFlag) {
            this.groupFlag = groupFlag;
        }

        public String getGroupPrice() {
            return groupPrice;
        }

        public void setGroupPrice(String groupPrice) {
            this.groupPrice = groupPrice;
        }

        public int getXianshiFlag() {
            return xianshiFlag;
        }

        public void setXianshiFlag(int xianshiFlag) {
            this.xianshiFlag = xianshiFlag;
        }

        public int getXianshiDiscount() {
            return xianshiDiscount;
        }

        public void setXianshiDiscount(int xianshiDiscount) {
            this.xianshiDiscount = xianshiDiscount;
        }

        public int getGoodsTransfeeCharge() {
            return goodsTransfeeCharge;
        }

        public void setGoodsTransfeeCharge(int goodsTransfeeCharge) {
            this.goodsTransfeeCharge = goodsTransfeeCharge;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getMemberMobile() {
            return memberMobile;
        }

        public void setMemberMobile(String memberMobile) {
            this.memberMobile = memberMobile;
        }

        public int getStcId() {
            return stcId;
        }

        public void setStcId(int stcId) {
            this.stcId = stcId;
        }

        public String getStcName() {
            return stcName;
        }

        public void setStcName(String stcName) {
            this.stcName = stcName;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public int getStorage() {
            return storage;
        }

        public void setStorage(int storage) {
            this.storage = storage;
        }

        public String getGoodsWebUrl() {
            return goodsWebUrl;
        }

        public void setGoodsWebUrl(String goodsWebUrl) {
            this.goodsWebUrl = goodsWebUrl;
        }

        public String getGoodsImageUrlPre() {
            return goodsImageUrlPre;
        }

        public void setGoodsImageUrlPre(String goodsImageUrlPre) {
            this.goodsImageUrlPre = goodsImageUrlPre;
        }

        public List<String> getGoodsImageMoreList() {
            return goodsImageMoreList;
        }

        public void setGoodsImageMoreList(List<String> goodsImageMoreList) {
            this.goodsImageMoreList = goodsImageMoreList;
        }

        public List<GoodsSpecBeanListBean> getGoodsSpecBeanList() {
            return goodsSpecBeanList;
        }

        public void setGoodsSpecBeanList(List<GoodsSpecBeanListBean> goodsSpecBeanList) {
            this.goodsSpecBeanList = goodsSpecBeanList;
        }

        public static class GoodsSpecBeanListBean {
            private long id;
            private long goodsId;
            private String specGoodsPrice;
            private int specGoodsStorage;
            private int specSalenum;
            private int state;

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
            }

            public long getGoodsId() {
                return goodsId;
            }

            public void setGoodsId(long goodsId) {
                this.goodsId = goodsId;
            }

            public String getSpecGoodsPrice() {
                return specGoodsPrice;
            }

            public void setSpecGoodsPrice(String specGoodsPrice) {
                this.specGoodsPrice = specGoodsPrice;
            }

            public int getSpecGoodsStorage() {
                return specGoodsStorage;
            }

            public void setSpecGoodsStorage(int specGoodsStorage) {
                this.specGoodsStorage = specGoodsStorage;
            }

            public int getSpecSalenum() {
                return specSalenum;
            }

            public void setSpecSalenum(int specSalenum) {
                this.specSalenum = specSalenum;
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
