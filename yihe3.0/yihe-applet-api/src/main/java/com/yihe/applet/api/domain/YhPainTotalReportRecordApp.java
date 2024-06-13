package com.yihe.applet.api.domain;

import java.util.Date;

public class YhPainTotalReportRecordApp {
    private Integer id;

    private String userCode;

    private Integer reportId;

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

    public Integer getReportId() {
        return reportId;
    }

    public void setReportId(Integer reportId) {
        this.reportId = reportId;
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