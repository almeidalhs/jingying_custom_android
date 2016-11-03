package com.atman.jixin.model.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by tangbingliang on 16/11/3.
 */
@Entity
public class LocationNumberModel {

    @Id(autoincrement = true)
    private Long id;
    private long targetId;
    private long loginId;
    private String location;
    public String getLocation() {
        return this.location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public long getLoginId() {
        return this.loginId;
    }
    public void setLoginId(long loginId) {
        this.loginId = loginId;
    }
    public long getTargetId() {
        return this.targetId;
    }
    public void setTargetId(long targetId) {
        this.targetId = targetId;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Generated(hash = 1080810457)
    public LocationNumberModel(Long id, long targetId, long loginId, String location) {
        this.id = id;
        this.targetId = targetId;
        this.loginId = loginId;
        this.location = location;
    }
    @Generated(hash = 1006845727)
    public LocationNumberModel() {
    }
}
