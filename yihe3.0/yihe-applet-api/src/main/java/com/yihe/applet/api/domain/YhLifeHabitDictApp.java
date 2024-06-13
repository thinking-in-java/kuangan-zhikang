package com.yihe.applet.api.domain;

import java.util.Date;
import java.util.List;

public class YhLifeHabitDictApp {
    private Integer id;

    private String habitName;

    private Integer habitType;

    private String habitdesc;

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

    private String standby;
    
    private boolean iftrue;
    
    private List<WeekDateVojo> weekDate;
    
    

    public List<WeekDateVojo> getWeekDate() {
		return weekDate;
	}

	public void setWeekDate(List<WeekDateVojo> weekDate) {
		this.weekDate = weekDate;
	}

	public boolean isIftrue() {
		return iftrue;
	}

	public void setIftrue(boolean iftrue) {
		this.iftrue = iftrue;
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

    public String getHabitdesc() {
        return habitdesc;
    }

    public void setHabitdesc(String habitdesc) {
        this.habitdesc = habitdesc == null ? null : habitdesc.trim();
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

    public String getStandby() {
        return standby;
    }

    public void setStandby(String standby) {
        this.standby = standby == null ? null : standby.trim();
    }
}