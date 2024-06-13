package com.yihe.system.domain;

import java.io.Serializable;

public class YhExprotBloodPressure implements Serializable{
//"用户编码", "姓名", "性别","单位","舒张压","收缩压","心率","数据录入时间"
	private static final long serialVersionUID = 1L;
	private String Org_Name;
	private String name;
	private Integer gender;
	private String User_Code;
	private String Sbp;
	private String Dbp;
	private String xinlv;
	private String Entry_Time;
	public String getOrg_Name() {
		return Org_Name;
	}
	public void setOrg_Name(String org_Name) {
		Org_Name = org_Name;
	}
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
	public String getUser_Code() {
		return User_Code;
	}
	public void setUser_Code(String user_Code) {
		User_Code = user_Code;
	}
	public String getSbp() {
		return Sbp;
	}
	public void setSbp(String sbp) {
		Sbp = sbp;
	}
	public String getDbp() {
		return Dbp;
	}
	public void setDbp(String dbp) {
		Dbp = dbp;
	}
	public String getXinlv() {
		return xinlv;
	}
	public void setXinlv(String xinlv) {
		this.xinlv = xinlv;
	}
	public String getEntry_Time() {
		return Entry_Time;
	}
	public void setEntry_Time(String entry_Time) {
		Entry_Time = entry_Time;
	}
	@Override
	public String toString() {
		return "YhExprotBloodPressure [Org_Name=" + Org_Name + ", name=" + name + ", gender=" + gender + ", User_Code="
				+ User_Code + ", Sbp=" + Sbp + ", Dbp=" + Dbp + ", xinlv=" + xinlv + ", Entry_Time=" + Entry_Time + "]";
	}
	
}
