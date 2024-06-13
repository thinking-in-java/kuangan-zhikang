package com.yihe.system.domain;

import java.util.Date;

public class YhUserSportsGuide {
    private Integer sportsGuideId;

    private Date startTime;

    private Date endTime;

    private Float totalEnergy;

    private Integer stype;

    private String userCode;

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

	public Integer getSportsGuideId() {
        return sportsGuideId;
    }

    public void setSportsGuideId(Integer sportsGuideId) {
        this.sportsGuideId = sportsGuideId;
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

    public Float getTotalEnergy() {
        return totalEnergy;
    }

    public void setTotalEnergy(Float totalEnergy) {
        this.totalEnergy = totalEnergy;
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