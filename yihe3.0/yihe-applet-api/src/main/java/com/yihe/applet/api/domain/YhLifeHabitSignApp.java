package com.yihe.applet.api.domain;

import java.util.Date;

public class YhLifeHabitSignApp {
    private Integer id;

    private Integer habitId;

    private Date signidDate;

    private String mark;

    private String userCode;

    private Integer lifeAdd;

    private String signImg;

    private Date increaseTime;
    
    private String name;
    
    private String headImg;
    
    
    

    public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getHabitId() {
        return habitId;
    }

    public void setHabitId(Integer habitId) {
        this.habitId = habitId;
    }

    public Date getSignidDate() {
        return signidDate;
    }

    public void setSignidDate(Date signidDate) {
        this.signidDate = signidDate;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark == null ? null : mark.trim();
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode == null ? null : userCode.trim();
    }

    public Integer getLifeAdd() {
        return lifeAdd;
    }

    public void setLifeAdd(Integer lifeAdd) {
        this.lifeAdd = lifeAdd;
    }

    public String getSignImg() {
        return signImg;
    }

    public void setSignImg(String signImg) {
        this.signImg = signImg == null ? null : signImg.trim();
    }

    public Date getIncreaseTime() {
        return increaseTime;
    }

    public void setIncreaseTime(Date increaseTime) {
        this.increaseTime = increaseTime;
    }
}