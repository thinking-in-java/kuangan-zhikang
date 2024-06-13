package com.yihe.system.domain;

import java.util.Date;

public class YhPainTotalReportRecord {
    private Integer id;

    private String userCode;
    
    private String name;
    private Integer gender;
    private Integer reportId;

    private String assessImproved;

    private Date resultTime;

    
    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
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