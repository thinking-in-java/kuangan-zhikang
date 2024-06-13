package com.yihe.applet.api.domain;

import java.util.Date;

public class YhHabitRemind {
    private Integer id;

    private String haId;

    private Date haTime;

    private String userCode;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHaId() {
        return haId;
    }

    public void setHaId(String haId) {
        this.haId = haId == null ? null : haId.trim();
    }

    public Date getHaTime() {
        return haTime;
    }

    public void setHaTime(Date haTime) {
        this.haTime = haTime;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode == null ? null : userCode.trim();
    }
}