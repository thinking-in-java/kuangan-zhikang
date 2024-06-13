package com.yihe.applet.api.domain;

import java.util.Date;

public class YhSportFrequencyApp {
    private Integer frId;

    private String userCode;

    private Integer frType;

    private String frFrequency;

    private Date frDete;

    public Integer getFrId() {
        return frId;
    }

    public void setFrId(Integer frId) {
        this.frId = frId;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode == null ? null : userCode.trim();
    }

    public Integer getFrType() {
        return frType;
    }

    public void setFrType(Integer frType) {
        this.frType = frType;
    }

    public String getFrFrequency() {
        return frFrequency;
    }

    public void setFrFrequency(String frFrequency) {
        this.frFrequency = frFrequency == null ? null : frFrequency.trim();
    }

    public Date getFrDete() {
        return frDete;
    }

    public void setFrDete(Date frDete) {
        this.frDete = frDete;
    }
}