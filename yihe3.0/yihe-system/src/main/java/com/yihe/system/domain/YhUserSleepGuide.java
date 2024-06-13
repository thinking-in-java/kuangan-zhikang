package com.yihe.system.domain;

import java.util.Date;

public class YhUserSleepGuide {
    private Integer sleepGuideId;

    private Date startTime;

    private Date endTime;

    private Integer stype;

    private String userCode;

    private Date newlyTime;
    
private String userName;
    
    private int gender;
    
    private Date birthdate;
    
    private String source;
    
    

    public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public Integer getSleepGuideId() {
        return sleepGuideId;
    }

    public void setSleepGuideId(Integer sleepGuideId) {
        this.sleepGuideId = sleepGuideId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getStype() {
        return stype;
    }

    public void setStype(Integer stype) {
        this.stype = stype;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode == null ? null : userCode.trim();
    }

    public Date getNewlyTime() {
        return newlyTime;
    }

    public void setNewlyTime(Date newlyTime) {
        this.newlyTime = newlyTime;
    }
}