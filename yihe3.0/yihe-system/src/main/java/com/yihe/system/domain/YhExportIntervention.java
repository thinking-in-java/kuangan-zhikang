package com.yihe.system.domain;

import java.io.Serializable;

public class YhExportIntervention implements Serializable{
	private static final long serialVersionUID = 1L;
	//评估人数
	private	Integer assesspeople;
	//可疑糖尿病
	private Integer maydiabetes;
	//可疑高血压
	private Integer mayhypertension;
	//有一个或多个危险因素的人
	private Integer haveriskpeople;
	//现在换病人数介绍
	private String havedis;
	//吸烟人数
	private Integer smokepeople;
	//一个或多个因素的百分比
	private Double persent;
	private Integer waistcount;
	//被动吸烟
	private Integer smoke_sencod;
	//高血脂人数
	private Integer bfcount;
	
	public Integer getBfcount() {
		return bfcount;
	}
	public void setBfcount(Integer bfcount) {
		this.bfcount = bfcount;
	}
	public Integer getSmoke_sencod() {
		return smoke_sencod;
	}
	public void setSmoke_sencod(Integer smoke_sencod) {
		this.smoke_sencod = smoke_sencod;
	}
	public Integer getWaistcount() {
		return waistcount;
	}
	public void setWaistcount(Integer waistcount) {
		this.waistcount = waistcount;
	}
	public Double getPersent() {
		return persent;
	}
	public void setPersent(Double persent) {
		this.persent = persent;
	}
	public Integer getAssesspeople() {
		return assesspeople;
	}
	public void setAssesspeople(Integer assesspeople) {
		this.assesspeople = assesspeople;
	}
	public Integer getMaydiabetes() {
		return maydiabetes;
	}
	public void setMaydiabetes(Integer maydiabetes) {
		this.maydiabetes = maydiabetes;
	}
	public Integer getMayhypertension() {
		return mayhypertension;
	}
	public void setMayhypertension(Integer mayhypertension) {
		this.mayhypertension = mayhypertension;
	}
	public Integer getHaveriskpeople() {
		return haveriskpeople;
	}
	public void setHaveriskpeople(Integer haveriskpeople) {
		this.haveriskpeople = haveriskpeople;
	}
	public String getHavedis() {
		return havedis;
	}
	public void setHavedis(String havedis) {
		this.havedis = havedis;
	}
	public Integer getSmokepeople() {
		return smokepeople;
	}
	public void setSmokepeople(Integer smokepeople) {
		this.smokepeople = smokepeople;
	}
	
}
