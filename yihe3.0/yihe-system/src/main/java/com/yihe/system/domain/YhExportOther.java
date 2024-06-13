package com.yihe.system.domain;

import java.io.Serializable;

public class YhExportOther implements Serializable{
	private static final long serialVersionUID = 1L;
	private String Org_Name;
	private String name;
	private Integer gender;
	private String User_Code;
	private String urine_microprotein;
	private String glycosylated_hemoglobin;
	private String hcy;
	private String insertTime;
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
	public String getUrine_microprotein() {
		return urine_microprotein;
	}
	public void setUrine_microprotein(String urine_microprotein) {
		this.urine_microprotein = urine_microprotein;
	}
	public String getGlycosylated_hemoglobin() {
		return glycosylated_hemoglobin;
	}
	public void setGlycosylated_hemoglobin(String glycosylated_hemoglobin) {
		this.glycosylated_hemoglobin = glycosylated_hemoglobin;
	}
	public String getHcy() {
		return hcy;
	}
	public void setHcy(String hcy) {
		this.hcy = hcy;
	}
	public String getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}
	@Override
	public String toString() {
		return "YhExportOther [Org_Name=" + Org_Name + ", name=" + name + ", gender=" + gender + ", User_Code="
				+ User_Code + ", urine_microprotein=" + urine_microprotein + ", glycosylated_hemoglobin="
				+ glycosylated_hemoglobin + ", hcy=" + hcy + ", insertTime=" + insertTime + "]";
	}
	
}
