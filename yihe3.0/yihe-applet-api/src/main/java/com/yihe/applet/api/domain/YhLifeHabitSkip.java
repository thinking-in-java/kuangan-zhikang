package com.yihe.applet.api.domain;

import java.util.Date;

public class YhLifeHabitSkip {
    private Integer id;

    private String userCode;

    private Date skipTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode == null ? null : userCode.trim();
    }

    public Date getSkipTime() {
        return skipTime;
    }

    public void setSkipTime(Date skipTime) {
        this.skipTime = skipTime;
    }
}