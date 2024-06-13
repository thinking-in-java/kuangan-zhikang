package com.yihe.system.domain;

import java.io.Serializable;

public class YhExprotBMI implements Serializable{
	private static final long serialVersionUID = 1L;
	private String Org_Name;
	private String name;
	private Integer gender;
	private String User_Code;
	private Double U_Height;
	private Double U_Weight;
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

	public Double getU_Height() {
		return U_Height;
	}

	public void setU_Height(Double u_Height) {
		U_Height = u_Height;
	}

	public Double getU_Weight() {
		return U_Weight;
	}

	public void setU_Weight(Double u_Weight) {
		U_Weight = u_Weight;
	}

	public String getEntry_Time() {
		return Entry_Time;
	}
	public void setEntry_Time(String entry_Time) {
		Entry_Time = entry_Time;
	}
	@Override
	public String toString() {
		return "YhExprotBMI [Org_Name=" + Org_Name + ", name=" + name + ", gender=" + gender + ", User_Code="
				+ User_Code + ", U_Height=" + U_Height + ", U_Weight=" + U_Weight + ", Entry_Time=" + Entry_Time + "]";
	}
	
}
