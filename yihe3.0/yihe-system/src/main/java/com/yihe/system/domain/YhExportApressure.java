package com.yihe.system.domain;

import java.io.Serializable;

public class YhExportApressure implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer Id;
	private String Evaluate_Data;
	private Integer Evaluate_Type;
	private String Evaluate_Score;
	private String User_Code;
	private String Evaluate_Date;
	private String Do_Date;
	private String Org_Name;
	private String name;
	private Integer gender;
	public Integer getId() {
		return Id;
	}
	public void setId(Integer id) {
		Id = id;
	}
	public String getEvaluate_Data() {
		return Evaluate_Data;
	}
	public void setEvaluate_Data(String evaluate_Data) {
		Evaluate_Data = evaluate_Data;
	}
	public Integer getEvaluate_Type() {
		return Evaluate_Type;
	}
	public void setEvaluate_Type(Integer evaluate_Type) {
		Evaluate_Type = evaluate_Type;
	}
	public String getEvaluate_Score() {
		return Evaluate_Score;
	}
	public void setEvaluate_Score(String evaluate_Score) {
		Evaluate_Score = evaluate_Score;
	}
	public String getUser_Code() {
		return User_Code;
	}
	public void setUser_Code(String user_Code) {
		User_Code = user_Code;
	}
	public String getEvaluate_Date() {
		return Evaluate_Date;
	}
	public void setEvaluate_Date(String evaluate_Date) {
		Evaluate_Date = evaluate_Date;
	}
	public String getDo_Date() {
		return Do_Date;
	}
	public void setDo_Date(String do_Date) {
		Do_Date = do_Date;
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
	@Override
	public String toString() {
		return "YhExportApressure [Id=" + Id + ", Evaluate_Data=" + Evaluate_Data + ", Evaluate_Type=" + Evaluate_Type
				+ ", Evaluate_Score=" + Evaluate_Score + ", User_Code=" + User_Code + ", Evaluate_Date=" + Evaluate_Date
				+ ", Do_Date=" + Do_Date + ", Org_Name=" + Org_Name + ", name=" + name + ", gender=" + gender + "]";
	}
	
}
