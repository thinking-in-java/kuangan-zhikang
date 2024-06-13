package com.yihe.applet.api.domain;

import java.util.Date;

public class YhUserMakeCardApp {
    private String enrolCardId;

    private String enrolCardCode;

    private Date enrolTime;

    private String enrolUserId;

    private Integer cardType;

    private Date beginTime;

    private Date endTime;

    private String orgId;

    private Integer isDel;

    private String userCode;

    private Integer userType;

    private Integer doctor;

    private Integer evAmount;

    private Integer number;

    public String getEnrolCardId() {
        return enrolCardId;
    }

    public void setEnrolCardId(String enrolCardId) {
        this.enrolCardId = enrolCardId == null ? null : enrolCardId.trim();
    }

    public String getEnrolCardCode() {
        return enrolCardCode;
    }

    public void setEnrolCardCode(String enrolCardCode) {
        this.enrolCardCode = enrolCardCode == null ? null : enrolCardCode.trim();
    }

    public Date getEnrolTime() {
        return enrolTime;
    }

    public void setEnrolTime(Date enrolTime) {
        this.enrolTime = enrolTime;
    }

    public String getEnrolUserId() {
        return enrolUserId;
    }

    public void setEnrolUserId(String enrolUserId) {
        this.enrolUserId = enrolUserId == null ? null : enrolUserId.trim();
    }

    public Integer getCardType() {
        return cardType;
    }

    public void setCardType(Integer cardType) {
        this.cardType = cardType;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId == null ? null : orgId.trim();
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
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

    public Integer getDoctor() {
        return doctor;
    }

    public void setDoctor(Integer doctor) {
        this.doctor = doctor;
    }

    public Integer getEvAmount() {
        return evAmount;
    }

    public void setEvAmount(Integer evAmount) {
        this.evAmount = evAmount;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}