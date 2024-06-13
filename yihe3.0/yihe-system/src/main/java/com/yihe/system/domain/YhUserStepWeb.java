package com.yihe.system.domain;

import java.util.Date;

public class YhUserStepWeb {
    private Integer stepId; 

    private String userCode;

    private Integer userType;

    private Integer healthstars;

    private Integer healthlevelstars;

    private Integer goldCoins;

    private Integer silverStar;

    private Integer goldStar;

    private Integer goldCrown;

    private Date stepTime;

    private Integer longTime;

    private Date stayTime;

    public Integer getStepId() {
        return stepId;
    }

    public void setStepId(Integer stepId) {
        this.stepId = stepId;
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

    public Integer getHealthlevelstars() {
        return healthlevelstars;
    }

    public void setHealthlevelstars(Integer healthlevelstars) {
        this.healthlevelstars = healthlevelstars;
    }

    public Integer getGoldCoins() {
        return goldCoins;
    }

    public void setGoldCoins(Integer goldCoins) {
        this.goldCoins = goldCoins;
    }

    public Integer getSilverStar() {
        return silverStar;
    }

    public void setSilverStar(Integer silverStar) {
        this.silverStar = silverStar;
    }

    public Integer getGoldStar() {
        return goldStar;
    }

    public void setGoldStar(Integer goldStar) {
        this.goldStar = goldStar;
    }

    public Integer getGoldCrown() {
        return goldCrown;
    }

    public void setGoldCrown(Integer goldCrown) {
        this.goldCrown = goldCrown;
    }

    public Date getStepTime() {
        return stepTime;
    }

    public void setStepTime(Date stepTime) {
        this.stepTime = stepTime;
    }

    public Integer getLongTime() {
        return longTime;
    }

    public void setLongTime(Integer longTime) {
        this.longTime = longTime;
    }

    public Date getStayTime() {
        return stayTime;
    }

    public void setStayTime(Date stayTime) {
        this.stayTime = stayTime;
    }
}