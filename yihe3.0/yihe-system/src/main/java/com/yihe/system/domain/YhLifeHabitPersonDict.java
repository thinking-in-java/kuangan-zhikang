package com.yihe.system.domain;

import java.util.Date;

public class YhLifeHabitPersonDict {
    private Integer id;

    private String userCode;

    private Integer habitId;

    private Integer continueDay;

    private Integer missingDay;

    private Integer totalDay;

    private String beginTime;

    private String finishTim;

    private String period;

    private String beginDate;

    private String finishDate;

    private String accomplishType;

    private Date increaseDate;

    private String standby;

    private String standby1;

    private String standby2;
    
    private String habitName;
    
    

    public String getHabitName() {
		return habitName;
	}

	public void setHabitName(String habitName) {
		this.habitName = habitName;
	}

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

    public Integer getHabitId() {
        return habitId;
    }

    public void setHabitId(Integer habitId) {
        this.habitId = habitId;
    }

    public Integer getContinueDay() {
        return continueDay;
    }

    public void setContinueDay(Integer continueDay) {
        this.continueDay = continueDay;
    }

    public Integer getMissingDay() {
        return missingDay;
    }

    public void setMissingDay(Integer missingDay) {
        this.missingDay = missingDay;
    }

    public Integer getTotalDay() {
        return totalDay;
    }

    public void setTotalDay(Integer totalDay) {
        this.totalDay = totalDay;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime == null ? null : beginTime.trim();
    }

    public String getFinishTim() {
        return finishTim;
    }

    public void setFinishTim(String finishTim) {
        this.finishTim = finishTim == null ? null : finishTim.trim();
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period == null ? null : period.trim();
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate == null ? null : beginDate.trim();
    }

    public String getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate == null ? null : finishDate.trim();
    }

    public String getAccomplishType() {
        return accomplishType;
    }

    public void setAccomplishType(String accomplishType) {
        this.accomplishType = accomplishType == null ? null : accomplishType.trim();
    }

    public Date getIncreaseDate() {
        return increaseDate;
    }

    public void setIncreaseDate(Date increaseDate) {
        this.increaseDate = increaseDate;
    }

    public String getStandby() {
        return standby;
    }

    public void setStandby(String standby) {
        this.standby = standby == null ? null : standby.trim();
    }

    public String getStandby1() {
        return standby1;
    }

    public void setStandby1(String standby1) {
        this.standby1 = standby1 == null ? null : standby1.trim();
    }

    public String getStandby2() {
        return standby2;
    }

    public void setStandby2(String standby2) {
        this.standby2 = standby2 == null ? null : standby2.trim();
    }
}