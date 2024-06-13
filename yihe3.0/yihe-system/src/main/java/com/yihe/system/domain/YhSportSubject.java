package com.yihe.system.domain;

public class YhSportSubject {
    private Integer id;

    private Integer subjectType;

    private String subjectImg;

    private String subjectMotion;

    private String subjectDetails;

    private Integer subjectNum;

    private String remark;

    private String subjectTime;

    private Integer isDel;

    private Integer subjectDays;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSubjectType() {
        return subjectType;
    }

    public void setSubjectType(Integer subjectType) {
        this.subjectType = subjectType;
    }

    public String getSubjectImg() {
        return subjectImg;
    }

    public void setSubjectImg(String subjectImg) {
        this.subjectImg = subjectImg == null ? null : subjectImg.trim();
    }

    public String getSubjectMotion() {
        return subjectMotion;
    }

    public void setSubjectMotion(String subjectMotion) {
        this.subjectMotion = subjectMotion == null ? null : subjectMotion.trim();
    }

    public String getSubjectDetails() {
        return subjectDetails;
    }

    public void setSubjectDetails(String subjectDetails) {
        this.subjectDetails = subjectDetails == null ? null : subjectDetails.trim();
    }

    public Integer getSubjectNum() {
        return subjectNum;
    }

    public void setSubjectNum(Integer subjectNum) {
        this.subjectNum = subjectNum;
    }

    public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSubjectTime() {
        return subjectTime;
    }

    public void setSubjectTime(String subjectTime) {
        this.subjectTime = subjectTime == null ? null : subjectTime.trim();
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public Integer getSubjectDays() {
        return subjectDays;
    }

    public void setSubjectDays(Integer subjectDays) {
        this.subjectDays = subjectDays;
    }
}