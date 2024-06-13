package com.yihe.system.domain;

import java.util.Date;

public class YhFoodUserScrecipe {
    private Integer uFoodErecipeId;

    private Date cTime;

    private Date startTime;

    private Integer stateFlag;

    private Integer srecipeId;

    private Integer isDel;

    private String fElements;

    public Integer getuFoodErecipeId() {
        return uFoodErecipeId;
    }

    public void setuFoodErecipeId(Integer uFoodErecipeId) {
        this.uFoodErecipeId = uFoodErecipeId;
    }

    public Date getcTime() {
        return cTime;
    }

    public void setcTime(Date cTime) {
        this.cTime = cTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Integer getStateFlag() {
        return stateFlag;
    }

    public void setStateFlag(Integer stateFlag) {
        this.stateFlag = stateFlag;
    }

    public Integer getSrecipeId() {
        return srecipeId;
    }

    public void setSrecipeId(Integer srecipeId) {
        this.srecipeId = srecipeId;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public String getfElements() {
        return fElements;
    }

    public void setfElements(String fElements) {
        this.fElements = fElements == null ? null : fElements.trim();
    }
}