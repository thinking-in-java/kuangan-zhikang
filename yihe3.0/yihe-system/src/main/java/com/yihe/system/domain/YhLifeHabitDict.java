package com.yihe.system.domain;

import java.util.Date;

public class YhLifeHabitDict {
    private Integer id;

    private String habitName;

    private Integer habitType;

    private String habitDesc;

    private Integer periodWhether;

    private String period;

    private Integer timeWhether;

    private String beginTime;

    private String finishTime;

    private String lifeImg;

    private Date lifeTime;

    private Integer oneRank;

    private Integer twoRank;

    private Integer threeRank;

    private Integer fourRank;

    private Integer fiveRank;

    private String remindTime;

    private String userCode;
    
    private String categoryName;
    
    private String standby;
    
    private String expect;
    
    private int oneself;
    
    private int quanxian;
    
    private String orgCode;
    
    

	public int getQuanxian() {
		return quanxian;
	}

	public void setQuanxian(int quanxian) {
		this.quanxian = quanxian;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public int getOneself() {
		return oneself;
	}

	public void setOneself(int oneself) {
		this.oneself = oneself;
	}

	public String getExpect() {
		return expect;
	}

	public void setExpect(String expect) {
		this.expect = expect;
	}

	public String getStandby() {
		return standby;
	}

	public void setStandby(String standby) {
		this.standby = standby;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHabitName() {
        return habitName;
    }

    public void setHabitName(String habitName) {
        this.habitName = habitName == null ? null : habitName.trim();
    }

    public Integer getHabitType() {
        return habitType;
    }

    public void setHabitType(Integer habitType) {
        this.habitType = habitType;
    }

    public String gethabitDesc() {
        return habitDesc;
    }

    public void sethabitDesc(String habitDesc) {
        this.habitDesc = habitDesc == null ? null : habitDesc.trim();
    }

    public Integer getPeriodWhether() {
        return periodWhether;
    }

    public void setPeriodWhether(Integer periodWhether) {
        this.periodWhether = periodWhether;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period == null ? null : period.trim();
    }

    public Integer getTimeWhether() {
        return timeWhether;
    }

    public void setTimeWhether(Integer timeWhether) {
        this.timeWhether = timeWhether;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime == null ? null : beginTime.trim();
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime == null ? null : finishTime.trim();
    }

    public String getLifeImg() {
        return lifeImg;
    }

    public void setLifeImg(String lifeImg) {
        this.lifeImg = lifeImg == null ? null : lifeImg.trim();
    }

    public Date getLifeTime() {
        return lifeTime;
    }

    public void setLifeTime(Date lifeTime) {
        this.lifeTime = lifeTime;
    }

    public Integer getOneRank() {
        return oneRank;
    }

    public void setOneRank(Integer oneRank) {
        this.oneRank = oneRank;
    }

    public Integer getTwoRank() {
        return twoRank;
    }

    public void setTwoRank(Integer twoRank) {
        this.twoRank = twoRank;
    }

    public Integer getThreeRank() {
        return threeRank;
    }

    public void setThreeRank(Integer threeRank) {
        this.threeRank = threeRank;
    }

    public Integer getFourRank() {
        return fourRank;
    }

    public void setFourRank(Integer fourRank) {
        this.fourRank = fourRank;
    }

    public Integer getFiveRank() {
        return fiveRank;
    }

    public void setFiveRank(Integer fiveRank) {
        this.fiveRank = fiveRank;
    }

    public String getRemindTime() {
        return remindTime;
    }

    public void setRemindTime(String remindTime) {
        this.remindTime = remindTime == null ? null : remindTime.trim();
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode == null ? null : userCode.trim();
    }
}