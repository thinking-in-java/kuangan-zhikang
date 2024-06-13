package com.yihe.system.domain;

import java.io.Serializable;

public class YhExportLiver implements Serializable{
	private static final long serialVersionUID = 1L;
	private String Org_Name;
	private String name;
	private Integer gender;
	private String User_Code;
	private String gu_Bing;
	private String millet_straw;
	private String serum_albumin;
	private String serum_globulin;
	private String insertTime;
	
	public String getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
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
	public String getGu_Bing() {
		return gu_Bing;
	}
	public void setGu_Bing(String gu_Bing) {
		this.gu_Bing = gu_Bing;
	}
	public String getMillet_straw() {
		return millet_straw;
	}
	public void setMillet_straw(String millet_straw) {
		this.millet_straw = millet_straw;
	}
	public String getSerum_albumin() {
		return serum_albumin;
	}
	public void setSerum_albumin(String serum_albumin) {
		this.serum_albumin = serum_albumin;
	}
	public String getSerum_globulin() {
		return serum_globulin;
	}
	public void setSerum_globulin(String serum_globulin) {
		this.serum_globulin = serum_globulin;
	}
	@Override
	public String toString() {
		return "YhExportLiver [Org_Name=" + Org_Name + ", name=" + name + ", gender=" + gender + ", User_Code="
				+ User_Code + ", gu_Bing=" + gu_Bing + ", millet_straw=" + millet_straw + ", serum_albumin="
				+ serum_albumin + ", serum_globulin=" + serum_globulin + ", insertTime=" + insertTime + "]";
	}
	
}
