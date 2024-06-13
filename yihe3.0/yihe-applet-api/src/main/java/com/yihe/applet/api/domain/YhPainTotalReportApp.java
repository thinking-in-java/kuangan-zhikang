package com.yihe.applet.api.domain;

import java.util.Date;

public class YhPainTotalReportApp {
    private Integer reportId;

    private String userCode;

    private Date qtime;

    private Integer gender;

    private Integer age;

    private String userName;

    private Integer times;
   
    private Integer  num;
    private Integer painScore;

    private Integer cervicalResult;

    private Integer cervicalClassify;

    private String shoulderResult;

    private Integer shoulderClassify;

    private String backResult;

    private Integer backClassify;

    private String waistResult;

    private Integer waistClassify;

    private String wristResult;

    private Integer wristClassify;

    
    public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
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

    public Date getQtime() {
        return qtime;
    }

    public void setQtime(Date qtime) {
        this.qtime = qtime;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public Integer getTimes() {
        return times;
    }

    public void setTimes(Integer times) {
        this.times = times;
    }

    public Integer getPainScore() {
        return painScore;
    }

    public void setPainScore(Integer painScore) {
        this.painScore = painScore;
    }

    public Integer getCervicalResult() {
        return cervicalResult;
    }

    public void setCervicalResult(Integer cervicalResult) {
        this.cervicalResult = cervicalResult;
    }

    public Integer getCervicalClassify() {
        return cervicalClassify;
    }

    public void setCervicalClassify(Integer cervicalClassify) {
        this.cervicalClassify = cervicalClassify;
    }

    public String getShoulderResult() {
        return shoulderResult;
    }

    public void setShoulderResult(String shoulderResult) {
        this.shoulderResult = shoulderResult == null ? null : shoulderResult.trim();
    }

    public Integer getShoulderClassify() {
        return shoulderClassify;
    }

    public void setShoulderClassify(Integer shoulderClassify) {
        this.shoulderClassify = shoulderClassify;
    }

    public String getBackResult() {
        return backResult;
    }

    public void setBackResult(String backResult) {
        this.backResult = backResult == null ? null : backResult.trim();
    }

    public Integer getBackClassify() {
        return backClassify;
    }

    public void setBackClassify(Integer backClassify) {
        this.backClassify = backClassify;
    }

    public String getWaistResult() {
        return waistResult;
    }

    public void setWaistResult(String waistResult) {
        this.waistResult = waistResult == null ? null : waistResult.trim();
    }

    public Integer getWaistClassify() {
        return waistClassify;
    }

    public void setWaistClassify(Integer waistClassify) {
        this.waistClassify = waistClassify;
    }

    public String getWristResult() {
        return wristResult;
    }

    public void setWristResult(String wristResult) {
        this.wristResult = wristResult == null ? null : wristResult.trim();
    }

    public Integer getWristClassify() {
        return wristClassify;
    }

    public void setWristClassify(Integer wristClassify) {
        this.wristClassify = wristClassify;
    }
}