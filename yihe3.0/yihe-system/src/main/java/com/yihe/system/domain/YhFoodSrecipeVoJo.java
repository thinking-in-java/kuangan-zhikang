package com.yihe.system.domain;

import java.util.List;

public class YhFoodSrecipeVoJo {
	/**
	 * 套餐名
	 */
	private String Packagename;
	
	/**
	 * 试用人群
	 */
	private String tryout;
	
	/**
	 * 千卡
	 */
	private String kilocalorie;
	
	/**
	 * 描述
	 */
	private String describe;
	
	/**
	 * 天数
	 */
	private int number;
	
	private List<YhFoodrecordVoJo> vojo;

	public String getPackagename() {
		return Packagename;
	}

	public void setPackagename(String packagename) {
		Packagename = packagename;
	}

	public String getTryout() {
		return tryout;
	}

	public void setTryout(String tryout) {
		this.tryout = tryout;
	}

	public String getKilocalorie() {
		return kilocalorie;
	}

	public void setKilocalorie(String kilocalorie) {
		this.kilocalorie = kilocalorie;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public List<YhFoodrecordVoJo> getVojo() {
		return vojo;
	}

	public void setVojo(List<YhFoodrecordVoJo> vojo) {
		this.vojo = vojo;
	}
	
	
	
}
