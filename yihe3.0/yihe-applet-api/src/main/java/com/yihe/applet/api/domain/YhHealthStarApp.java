package com.yihe.applet.api.domain;

import java.util.Date;

public class YhHealthStarApp {
    private Integer healthstarId;

    private String userCode;

    private Integer userType;

    private Integer healthstars;

    private String healthstarsEvents;
    
    private String writeTime;

    private Date eventsTime;

    
    
    public String getWriteTime() {
		return writeTime;
	}

	public void setWriteTime(String writeTime) {
		this.writeTime = writeTime;
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