package com.yihe.system.domain;

import java.util.List;

import lombok.Data;

@Data
public class InterventionAdjustment {
	private String usercode;
	private String user_name;
	private String age;
	private String height;
	private String weight;
	private String strength;
	private String sex;
	private String record_kay;
	private String record_data;
	private String record_name;
	private String Flag_Dgroup;
	private String record_mode;
	
	public Integer srecipeId;
	public Integer ftype=1;
	public Integer eLevel;
	public Integer diseaseid;
	
	private List<String> usercodeList;
	
	
}
