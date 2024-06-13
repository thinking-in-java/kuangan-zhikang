package com.yihe.system.domain;

import java.io.Serializable;
import java.util.Date;

public class YhUserMakeCard implements Serializable {
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

    private String key;

    private static final long serialVersionUID = 1L;


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getEnrolCardId() {
        return enrolCardId;
    }

    public void setEnrolCardId(String enrolCardId) {
        this.enrolCardId = enrolCardId;
    }

    public String getEnrolCardCode() {
        return enrolCardCode;
    }

    public void setEnrolCardCode(String enrolCardCode) {
        this.enrolCardCode = enrolCardCode;
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
        this.enrolUserId = enrolUserId;
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
        this.orgId = orgId;
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
        this.userCode = userCode;
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

    @Override
	public String toString() {
		return "YhUserMakeCard [enrolCardId=" + enrolCardId + ", enrolCardCode=" + enrolCardCode + ", enrolTime="
				+ enrolTime + ", enrolUserId=" + enrolUserId + ", cardType=" + cardType + ", beginTime=" + beginTime
				+ ", endTime=" + endTime + ", orgId=" + orgId + ", isDel=" + isDel + ", userCode=" + userCode
				+ ", userType=" + userType + ", doctor=" + doctor + ", evAmount=" + evAmount + ", number=" + number
				+ "]";
	}

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        YhUserMakeCard other = (YhUserMakeCard) that;
        return (this.getEnrolCardId() == null ? other.getEnrolCardId() == null : this.getEnrolCardId().equals(other.getEnrolCardId()))
            && (this.getEnrolCardCode() == null ? other.getEnrolCardCode() == null : this.getEnrolCardCode().equals(other.getEnrolCardCode()))
            && (this.getEnrolTime() == null ? other.getEnrolTime() == null : this.getEnrolTime().equals(other.getEnrolTime()))
            && (this.getEnrolUserId() == null ? other.getEnrolUserId() == null : this.getEnrolUserId().equals(other.getEnrolUserId()))
            && (this.getCardType() == null ? other.getCardType() == null : this.getCardType().equals(other.getCardType()))
            && (this.getBeginTime() == null ? other.getBeginTime() == null : this.getBeginTime().equals(other.getBeginTime()))
            && (this.getEndTime() == null ? other.getEndTime() == null : this.getEndTime().equals(other.getEndTime()))
            && (this.getOrgId() == null ? other.getOrgId() == null : this.getOrgId().equals(other.getOrgId()))
            && (this.getIsDel() == null ? other.getIsDel() == null : this.getIsDel().equals(other.getIsDel()))
            && (this.getUserCode() == null ? other.getUserCode() == null : this.getUserCode().equals(other.getUserCode()))
            && (this.getUserType() == null ? other.getUserType() == null : this.getUserType().equals(other.getUserType()))
            && (this.getDoctor() == null ? other.getDoctor() == null : this.getDoctor().equals(other.getDoctor()))
            && (this.getEvAmount() == null ? other.getEvAmount() == null : this.getEvAmount().equals(other.getEvAmount()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getEnrolCardId() == null) ? 0 : getEnrolCardId().hashCode());
        result = prime * result + ((getEnrolCardCode() == null) ? 0 : getEnrolCardCode().hashCode());
        result = prime * result + ((getEnrolTime() == null) ? 0 : getEnrolTime().hashCode());
        result = prime * result + ((getEnrolUserId() == null) ? 0 : getEnrolUserId().hashCode());
        result = prime * result + ((getCardType() == null) ? 0 : getCardType().hashCode());
        result = prime * result + ((getBeginTime() == null) ? 0 : getBeginTime().hashCode());
        result = prime * result + ((getEndTime() == null) ? 0 : getEndTime().hashCode());
        result = prime * result + ((getOrgId() == null) ? 0 : getOrgId().hashCode());
        result = prime * result + ((getIsDel() == null) ? 0 : getIsDel().hashCode());
        result = prime * result + ((getUserCode() == null) ? 0 : getUserCode().hashCode());
        result = prime * result + ((getUserType() == null) ? 0 : getUserType().hashCode());
        result = prime * result + ((getDoctor() == null) ? 0 : getDoctor().hashCode());
        result = prime * result + ((getEvAmount() == null) ? 0 : getEvAmount().hashCode());
        return result;
    }
}