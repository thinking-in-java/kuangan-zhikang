package com.yihe.applet.api.domain;

import java.util.Date;

public class YhSportEncourageApp {
    private Integer zwId;

    private Integer chId;

    private Date timeAdd;

    private String userCode;

    public Integer getZwId() {
        return zwId;
    }

    public void setZwId(Integer zwId) {
        this.zwId = zwId;
    }

    public Integer getChId() {
        return chId;
    }

    public void setChId(Integer chId) {
        this.chId = chId;
    }

    public Date getTimeAdd() {
        return timeAdd;
    }

    public void setTimeAdd(Date timeAdd) {
        this.timeAdd = timeAdd;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode == null ? null : userCode.trim();
    }
}