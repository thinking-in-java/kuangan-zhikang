package com.yihe.system.domain;

import java.util.Date;

public class SleepRemindWeb {
    private Integer id;

    private String userCode;

    private String remindTime;

    private Date qtime;

    private Integer timerId;

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

    public String getRemindTime() {
        return remindTime;
    }

    public void setRemindTime(String remindTime) {
        this.remindTime = remindTime == null ? null : remindTime.trim();
    }

    public Date getQtime() {
        return qtime;
    }

    public void setQtime(Date qtime) {
        this.qtime = qtime;
    }

    public Integer getTimerId() {
        return timerId;
    }

    public void setTimerId(Integer timerId) {
        this.timerId = timerId;
    }
}