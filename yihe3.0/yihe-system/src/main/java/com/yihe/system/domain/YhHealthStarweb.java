package com.yihe.system.domain;

import lombok.Data;

import java.util.Date;
@Data
public class YhHealthStarweb {
    private Integer healthstarId;

    private String userCode;
    private String usernmae;
    private String healthlevelstars;
    private String name;
    private Integer income;
    private Integer expenditure;
    private Integer userType;

    private Integer healthstars;

    private String healthstarsEvents;

    private Date eventsTime;

    private Integer roletype;

    public Integer getRoletype() {
        return roletype;
    }

    public void setRoletype(Integer roletype) {
        this.roletype = roletype;
    }

    public Integer getHealthstarId() {
        return healthstarId;
    }

    public void setHealthstarId(Integer healthstarId) {
        this.healthstarId = healthstarId;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode == null ? null : userCode.trim();
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Integer getHealthstars() {
        return healthstars;
    }

    public void setHealthstars(Integer healthstars) {
        this.healthstars = healthstars;
    }

    public String getHealthstarsEvents() {
        return healthstarsEvents;
    }

    public void setHealthstarsEvents(String healthstarsEvents) {
        this.healthstarsEvents = healthstarsEvents == null ? null : healthstarsEvents.trim();
    }

    public Date getEventsTime() {
        return eventsTime;
    }

    public void setEventsTime(Date eventsTime) {
        this.eventsTime = eventsTime;
    }
}