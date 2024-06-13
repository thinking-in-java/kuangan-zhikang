package com.yihe.system.domain;

import java.util.Date;
import java.util.List;

public class YhSleepReportVojo {
    private Integer reportId;

    private String userCode;

    private String reportDesc;

    private Integer reportScore;

    private String reportLevel;

    private String reportQueselect;

    private Date qtime;
    
    private String userName;
    
    private String sex;
    
    private String name;
    
    private String result;
    
    private int frequency;
    
    
	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Integer getReportId() {
		return reportId;
	}

	public void setReportId(Integer reportId) {
		this.reportId = reportId;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getReportDesc() {
		return reportDesc;
	}

	public void setReportDesc(String reportDesc) {
		this.reportDesc = reportDesc;
	}

	public Integer getReportScore() {
		return reportScore;
	}

	public void setReportScore(Integer reportScore) {
		this.reportScore = reportScore;
	}

	public String getReportLevel() {
		return reportLevel;
	}

	public void setReportLevel(String reportLevel) {
		this.reportLevel = reportLevel;
	}

	public String getReportQueselect() {
		return reportQueselect;
	}

	public void setReportQueselect(String reportQueselect) {
		this.reportQueselect = reportQueselect;
	}

	public Date getQtime() {
		return qtime;
	}

	public void setQtime(Date qtime) {
		this.qtime = qtime;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
    
    
    
}