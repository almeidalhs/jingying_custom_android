package com.atman.jixin.model.response;

/**
 * Created by tangbingliang on 16/12/16.
 */

public class GetSignInforModel {
    /**
     * result : 1
     * body : {"id":23,"name":"唐炳良","respectCall":"尊敬的《唐炳良》领导\r\n您的座位号为","avatar":"Avatar/b5/f1/b5f10087b60911e6ae7774d02ba07f83.jpg","company":"阿特门","dept":"移动创新实验室","mobile":"18578909061","position":"资深Android","atmanUserId":100003790,"signType":1,"getOver":0,"seatNum":134,"tableNum":29,"machineType":1,"winningType":0}
     */

    private String result;
    /**
     * id : 23
     * name : 唐炳良
     * respectCall : 尊敬的《唐炳良》领导
     您的座位号为
     * avatar : Avatar/b5/f1/b5f10087b60911e6ae7774d02ba07f83.jpg
     * company : 阿特门
     * dept : 移动创新实验室
     * mobile : 18578909061
     * position : 资深Android
     * atmanUserId : 100003790
     * signType : 1
     * getOver : 0
     * seatNum : 134
     * tableNum : 29
     * machineType : 1
     * winningType : 0
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
        private String name;
        private String respectCall;
        private String avatar;
        private String company;
        private String dept;
        private String mobile;
        private String position;
        private long atmanUserId;
        private int signType;
        private int getOver;
        private String seatNum;
        private String tableNum;
        private int machineType;
        private int winningType;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRespectCall() {
            return respectCall;
        }

        public void setRespectCall(String respectCall) {
            this.respectCall = respectCall;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getDept() {
            return dept;
        }

        public void setDept(String dept) {
            this.dept = dept;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public long getAtmanUserId() {
            return atmanUserId;
        }

        public void setAtmanUserId(long atmanUserId) {
            this.atmanUserId = atmanUserId;
        }

        public int getSignType() {
            return signType;
        }

        public void setSignType(int signType) {
            this.signType = signType;
        }

        public int getGetOver() {
            return getOver;
        }

        public void setGetOver(int getOver) {
            this.getOver = getOver;
        }

        public String getSeatNum() {
            return seatNum;
        }

        public void setSeatNum(String seatNum) {
            this.seatNum = seatNum;
        }

        public String getTableNum() {
            return tableNum;
        }

        public void setTableNum(String tableNum) {
            this.tableNum = tableNum;
        }

        public int getMachineType() {
            return machineType;
        }

        public void setMachineType(int machineType) {
            this.machineType = machineType;
        }

        public int getWinningType() {
            return winningType;
        }

        public void setWinningType(int winningType) {
            this.winningType = winningType;
        }
    }
}
