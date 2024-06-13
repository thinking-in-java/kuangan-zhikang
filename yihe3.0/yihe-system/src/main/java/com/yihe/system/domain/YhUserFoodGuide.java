package com.yihe.system.domain;

import java.util.Date;

public class YhUserFoodGuide {
    private Integer foodguideId;

    private Integer gtype;

    private Date startTime;

    private Date endTime;

    private Integer energyLevel;

    private Integer userCode;

    private Date newlyTime;
    
    private String source;
    
 private String userName;
    
    private int gender;
    
    private Date birthdate;
    
    
    
    

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

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Integer getFoodguideId() {
        return foodguideId;
    }

    public void setFoodguideId(Integer foodguideId) {
        this.foodguideId = foodguideId;
    }

    public Integer getGtype() {
        return gtype;
    }

    public void setGtype(Integer gtype) {
        this.gtype = gtype;
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

    public Integer getEnergyLevel() {
        return energyLevel;
    }

    public void setEnergyLevel(Integer energyLevel) {
        this.energyLevel = energyLevel;
    }

    public Integer getUserCode() {
        return userCode;
    }

    public void setUserCode(Integer userCode) {
        this.userCode = userCode;
    }

    public Date getNewlyTime() {
        return newlyTime;
    }

    public void setNewlyTime(Date newlyTime) {
        this.newlyTime = newlyTime;
    }
}