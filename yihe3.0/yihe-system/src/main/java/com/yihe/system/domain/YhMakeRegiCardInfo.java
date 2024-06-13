package com.yihe.system.domain;

import java.io.Serializable;
import java.util.Date;

public class YhMakeRegiCardInfo implements Serializable{
	 private static final long serialVersionUID = 1L;
	 private String enrolCardId;
	 private String userName;
	 private String doctor;
	 private String orgName;
	 private Date beginTime;
	 private Date endTime;
	 private String Org_Code;
	 private String superOrgCode;
	 private Integer id;
	 private String name;
	 private String username;
	 private String key;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getSuperOrgCode() {
		return superOrgCode;
	}
	public void setSuperOrgCode(String superOrgCode) {
		this.superOrgCode = superOrgCode;
	}
	public String getOrg_Code() {
		return Org_Code;
	}
	public void setOrg_Code(String org_Code) {
		Org_Code = org_Code;
	}
	public String getEnrolCardId() {
		return enrolCardId;
	}
	public void setEnrolCardId(String enrolCardId) {
		this.enrolCardId = enrolCardId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getDoctor() {
		return doctor;
	}
	public void setDoctor(String doctor) {
		this.doctor = doctor;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
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
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	@Override
	public String toString() {
		return "YhMakeRegiCardInfo [enrolCardId=" + enrolCardId + ", userName=" + userName + ", doctor=" + doctor
				+ ", orgName=" + orgName + ", beginTime=" + beginTime + ", endTime=" + endTime + "]";
	}
}
