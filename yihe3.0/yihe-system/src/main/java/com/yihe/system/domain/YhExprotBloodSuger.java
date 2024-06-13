package com.yihe.system.domain;

import java.io.Serializable;

public class YhExprotBloodSuger implements Serializable{
	private static final long serialVersionUID = 1L;
	private String Org_Name;
	private String name;
	private Integer gender;
	private String User_Code;
	private String Bs;
	private String Entry_Time;
	private String Env;

	public String getEnv() {
		return Env;
	}

	public void setEnv(String env) {
		Env = env;
	}

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
	public String getBs() {
		return Bs;
	}
	public void setBs(String bs) {
		Bs = bs;
	}
	public String getEntry_Time() {
		return Entry_Time;
	}
	public void setEntry_Time(String entry_Time) {
		Entry_Time = entry_Time;
	}
	@Override
	public String toString() {
		return "YhExprotBloodSuger [Org_Name=" + Org_Name + ", name=" + name + ", gender=" + gender + ", User_Code="
				+ User_Code + ", Bs=" + Bs + ", Entry_Time=" + Entry_Time + "]";
	}
	
}
