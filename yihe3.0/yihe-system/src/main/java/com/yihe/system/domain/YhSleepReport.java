package com.yihe.system.domain;

import java.util.Date;
import java.util.List;

public class YhSleepReport {
    private Integer reportId;

    private String userCode;

    private String reportDesc;

    private Integer reportScore;

    private String reportLevel;

    private String reportQueselect;

    private Date qtime;
    
    private String result;
    
    private String userName;
    
    private int gender;
    
    private Date birthdate;
    
    List<SleepVoJo>  vojo;
    
    private int age;
    
    
    
    
    
    
    public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public List<SleepVoJo> getVojo() {
		return vojo;
	}

	public void setVojo(List<SleepVoJo> vojo) {
		this.vojo = vojo;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
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
        this.userCode = userCode == null ? null : userCode.trim();
    }

    public String getReportDesc() {
        return reportDesc;
    }

    public void setReportDesc(String reportDesc) {
        this.reportDesc = reportDesc == null ? null : reportDesc.trim();
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
        this.reportLevel = reportLevel == null ? null : reportLevel.trim();
    }

    public String getReportQueselect() {
        return reportQueselect;
    }

    public void setReportQueselect(String reportQueselect) {
        this.reportQueselect = reportQueselect == null ? null : reportQueselect.trim();
    }

    public Date getQtime() {
        return qtime;
    }

    public void setQtime(Date qtime) {
        this.qtime = qtime;
    }
}