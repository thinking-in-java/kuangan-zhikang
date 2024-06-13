package com.yihe.applet.api.domain;

import java.util.Date;

public class YhEventEnrollApp {
    private Integer id;

    private String eventName;

    private String eventContent;

    private String provider;

    private Date publicDate;

    private Date registerEndDate;

    private Date startDate;

    private Integer total;

    private Date endDate;

    private String eventRule;

    private String eventAddr;

    private String eventImg;

    private Integer eventType;
    
    private int status;//1不显示按钮2,还没到报名时间3,时间以过4,已报名
    
    

    public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName == null ? null : eventName.trim();
    }

    public String getEventContent() {
        return eventContent;
    }

    public void setEventContent(String eventContent) {
        this.eventContent = eventContent == null ? null : eventContent.trim();
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider == null ? null : provider.trim();
    }

    public Date getPublicDate() {
        return publicDate;
    }

    public void setPublicDate(Date publicDate) {
        this.publicDate = publicDate;
    }

    public Date getRegisterEndDate() {
        return registerEndDate;
    }

    public void setRegisterEndDate(Date registerEndDate) {
        this.registerEndDate = registerEndDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getEventRule() {
        return eventRule;
    }

    public void setEventRule(String eventRule) {
        this.eventRule = eventRule == null ? null : eventRule.trim();
    }

    public String getEventAddr() {
        return eventAddr;
    }

    public void setEventAddr(String eventAddr) {
        this.eventAddr = eventAddr == null ? null : eventAddr.trim();
    }

    public String getEventImg() {
        return eventImg;
    }

    public void setEventImg(String eventImg) {
        this.eventImg = eventImg == null ? null : eventImg.trim();
    }

    public Integer getEventType() {
        return eventType;
    }

    public void setEventType(Integer eventType) {
        this.eventType = eventType;
    }
}