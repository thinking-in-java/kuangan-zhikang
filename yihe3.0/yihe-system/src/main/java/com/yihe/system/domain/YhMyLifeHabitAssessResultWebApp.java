package com.yihe.system.domain;

import java.util.Date;

public class YhMyLifeHabitAssessResultWebApp {
    private Integer id;

    private String userCode;

    private Integer dictId;

    private Integer assessScores;

    private String assessImproved;

    private Date resultTime;

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

    public Integer getDictId() {
        return dictId;
    }

    public void setDictId(Integer dictId) {
        this.dictId = dictId;
    }

    public Integer getAssessScores() {
        return assessScores;
    }

    public void setAssessScores(Integer assessScores) {
        this.assessScores = assessScores;
    }

    public String getAssessImproved() {
        return assessImproved;
    }

    public void setAssessImproved(String assessImproved) {
        this.assessImproved = assessImproved == null ? null : assessImproved.trim();
    }

    public Date getResultTime() {
        return resultTime;
    }

    public void setResultTime(Date resultTime) {
        this.resultTime = resultTime;
    }
}