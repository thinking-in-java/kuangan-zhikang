package com.yihe.system.domain;

import java.io.Serializable;
//yh_disease_assess_report
public class YhExportAssessReport implements Serializable{
	 private static final long serialVersionUID = 1L;
	 private Integer aid;
	 private String user_code;
	 private String result_serialNum;
	 private String request_serialNum;
	 private String diseasId;
	 private String risk_level;
	 private String risk_desc;
	 private String now_risk_value;
	 private String ideal_risk_level;
	 private String ideal_risk_desc;
	 private String age_forecast_r20;
	 private String age_forecast_r30;
	 private String age_forecast_r40;
	 private String age_forecast_r50;
	 private String age_forecast_r60;
	 private String age_forecast_r70;
	 private String age_forecast_r80;
	 private String age_op_forecast_r20_op;
	 private String	age_op_forecast_r30_op;
	 private String age_op_forecast_r40_op;
	 private String age_op_forecast_r50_op;
	 private String age_op_forecast_r60_op;
	 private String age_op_forecast_r70_op;
	 private String age_op_forecast_r80_op;
	
	
	
	public Integer getAid() {
		return aid;
	}
	public void setAid(Integer aid) {
		this.aid = aid;
	}
	public String getUser_code() {
		return user_code;
	}
	public void setUser_code(String user_code) {
		this.user_code = user_code;
	}
	public String getResult_serialNum() {
		return result_serialNum;
	}
	public void setResult_serialNum(String result_serialNum) {
		this.result_serialNum = result_serialNum;
	}
	public String getRequest_serialNum() {
		return request_serialNum;
	}
	public void setRequest_serialNum(String request_serialNum) {
		this.request_serialNum = request_serialNum;
	}


	public String getDiseasId() {
		return diseasId;
	}
	public void setDiseasId(String diseasId) {
		this.diseasId = diseasId;
	}
	public String getRisk_level() {
		return risk_level;
	}
	public void setRisk_level(String risk_level) {
		this.risk_level = risk_level;
	}
	public String getRisk_desc() {
		return risk_desc;
	}
	public void setRisk_desc(String risk_desc) {
		this.risk_desc = risk_desc;
	}
	public String getNow_risk_value() {
		return now_risk_value;
	}
	public void setNow_risk_value(String now_risk_value) {
		this.now_risk_value = now_risk_value;
	}
	public String getIdeal_risk_level() {
		return ideal_risk_level;
	}
	public void setIdeal_risk_level(String ideal_risk_level) {
		this.ideal_risk_level = ideal_risk_level;
	}
	public String getIdeal_risk_desc() {
		return ideal_risk_desc;
	}
	public void setIdeal_risk_desc(String ideal_risk_desc) {
		this.ideal_risk_desc = ideal_risk_desc;
	}
	public String getAge_forecast_r20() {
		return age_forecast_r20;
	}
	public void setAge_forecast_r20(String age_forecast_r20) {
		this.age_forecast_r20 = age_forecast_r20;
	}
	public String getAge_forecast_r30() {
		return age_forecast_r30;
	}
	public void setAge_forecast_r30(String age_forecast_r30) {
		this.age_forecast_r30 = age_forecast_r30;
	}
	public String getAge_forecast_r40() {
		return age_forecast_r40;
	}
	public void setAge_forecast_r40(String age_forecast_r40) {
		this.age_forecast_r40 = age_forecast_r40;
	}
	public String getAge_forecast_r50() {
		return age_forecast_r50;
	}
	public void setAge_forecast_r50(String age_forecast_r50) {
		this.age_forecast_r50 = age_forecast_r50;
	}
	public String getAge_forecast_r60() {
		return age_forecast_r60;
	}
	public void setAge_forecast_r60(String age_forecast_r60) {
		this.age_forecast_r60 = age_forecast_r60;
	}
	public String getAge_forecast_r70() {
		return age_forecast_r70;
	}
	public void setAge_forecast_r70(String age_forecast_r70) {
		this.age_forecast_r70 = age_forecast_r70;
	}
	public String getAge_forecast_r80() {
		return age_forecast_r80;
	}
	public void setAge_forecast_r80(String age_forecast_r80) {
		this.age_forecast_r80 = age_forecast_r80;
	}
	public String getAge_op_forecast_r20_op() {
		return age_op_forecast_r20_op;
	}
	public void setAge_op_forecast_r20_op(String age_op_forecast_r20_op) {
		this.age_op_forecast_r20_op = age_op_forecast_r20_op;
	}
	public String getAge_op_forecast_r30_op() {
		return age_op_forecast_r30_op;
	}
	public void setAge_op_forecast_r30_op(String age_op_forecast_r30_op) {
		this.age_op_forecast_r30_op = age_op_forecast_r30_op;
	}
	public String getAge_op_forecast_r40_op() {
		return age_op_forecast_r40_op;
	}
	public void setAge_op_forecast_r40_op(String age_op_forecast_r40_op) {
		this.age_op_forecast_r40_op = age_op_forecast_r40_op;
	}
	public String getAge_op_forecast_r50_op() {
		return age_op_forecast_r50_op;
	}
	public void setAge_op_forecast_r50_op(String age_op_forecast_r50_op) {
		this.age_op_forecast_r50_op = age_op_forecast_r50_op;
	}
	public String getAge_op_forecast_r60_op() {
		return age_op_forecast_r60_op;
	}
	public void setAge_op_forecast_r60_op(String age_op_forecast_r60_op) {
		this.age_op_forecast_r60_op = age_op_forecast_r60_op;
	}
	public String getAge_op_forecast_r70_op() {
		return age_op_forecast_r70_op;
	}
	public void setAge_op_forecast_r70_op(String age_op_forecast_r70_op) {
		this.age_op_forecast_r70_op = age_op_forecast_r70_op;
	}
	public String getAge_op_forecast_r80_op() {
		return age_op_forecast_r80_op;
	}
	public void setAge_op_forecast_r80_op(String age_op_forecast_r80_op) {
		this.age_op_forecast_r80_op = age_op_forecast_r80_op;
	}
	@Override
	public String toString() {
		return "YhExportAssessReport [aid=" + aid + ", user_code=" + user_code + ", result_serialNum=" + result_serialNum
				+ ", request_serialNum=" + request_serialNum + ", diseasId=" + diseasId + ", risk_level=" + risk_level
				+ ", risk_desc=" + risk_desc + ", now_risk_value=" + now_risk_value + ", ideal_risk_level="
				+ ideal_risk_level + ", ideal_risk_desc=" + ideal_risk_desc + ", age_forecast_r20=" + age_forecast_r20
				+ ", age_forecast_r30=" + age_forecast_r30 + ", age_forecast_r40=" + age_forecast_r40
				+ ", age_forecast_r50=" + age_forecast_r50 + ", age_forecast_r60=" + age_forecast_r60
				+ ", age_forecast_r70=" + age_forecast_r70 + ", age_forecast_r80=" + age_forecast_r80
				+ ", age_op_forecast_r20_op=" + age_op_forecast_r20_op + ", age_op_forecast_r30_op="
				+ age_op_forecast_r30_op + ", age_op_forecast_r40_op=" + age_op_forecast_r40_op
				+ ", age_op_forecast_r50_op=" + age_op_forecast_r50_op + ", age_op_forecast_r60_op="
				+ age_op_forecast_r60_op + ", age_op_forecast_r70_op=" + age_op_forecast_r70_op
				+ ", age_op_forecast_r80_op=" + age_op_forecast_r80_op + "]";
	}
	 
}
