package com.yihe.system.domain;

import java.io.Serializable;

public class YhExprotBloodFat implements Serializable{
	private static final long serialVersionUID = 1L;
	private String Org_Name;
	private String name;
	private Integer gender;
	private String User_Code;
	private Float Cholesterin;
	private Float Tring;
	private Float Hdl;
	private Float Ldl;
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
	public Float getCholesterin() {
		return Cholesterin;
	}
	public void setCholesterin(Float cholesterin) {
		Cholesterin = cholesterin;
	}
	public Float getTring() {
		return Tring;
	}
	public void setTring(Float tring) {
		Tring = tring;
	}
	public Float getHdl() {
		return Hdl;
	}
	public void setHdl(Float hdl) {
		Hdl = hdl;
	}
	public Float getLdl() {
		return Ldl;
	}
	public void setLdl(Float ldl) {
		Ldl = ldl;
	}
	public String getEntry_Time() {
		return Entry_Time;
	}
	public void setEntry_Time(String entry_Time) {
		Entry_Time = entry_Time;
	}
	@Override
	public String toString() {
		return "YhExprotBloodFat [Org_Name=" + Org_Name + ", name=" + name + ", gender=" + gender + ", User_Code="
				+ User_Code + ", Cholesterin=" + Cholesterin + ", Tring=" + Tring + ", Hdl=" + Hdl + ", Ldl=" + Ldl
				+ ", Entry_Time=" + Entry_Time + "]";
	}
	
}
