package com.yihe.system.domain;

import java.util.Date;



public class YhUserReginfoVOJO implements java.io.Serializable{
	private String user_code;
	private String user_name;
	private Integer gender;
	private String birth_date;
	private String mobile;
	private String Org_Name;
	private Date reg_time;
	public String getUser_code() {
		return user_code;
	}
	public void setUser_code(String user_code) {
		this.user_code = user_code;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public Integer getGender() {
		return gender;
	}
	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public String getBirth_date() {
		return birth_date;
	}

	public void setBirth_date(String birth_date) {
		this.birth_date = birth_date;
	}

	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getOrg_Name() {
		return Org_Name;
	}
	public void setOrg_Name(String org_Name) {
		Org_Name = org_Name;
	}
	public Date getReg_time() {
		return reg_time;
	}
	public void setReg_time(Date reg_time) {
		this.reg_time = reg_time;
	}
	@Override
	public String toString() {
		return "YhUserReginfoVOJO [user_code=" + user_code + ", user_name=" + user_name + ", gender=" + gender
				+ ", birth_date=" + birth_date + ", mobile=" + mobile + ", Org_Name=" + Org_Name + ", reg_time="
				+ reg_time + "]";
	}
	
}
