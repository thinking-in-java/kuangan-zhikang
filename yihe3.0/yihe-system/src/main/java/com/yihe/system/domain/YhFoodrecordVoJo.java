/**
 * 
 */
package com.yihe.system.domain;

import java.util.List;

/**
 * 食物记录
 * @author dong
 *
 */
public class YhFoodrecordVoJo {
	/**
	 * 早
	 */
	private List<YhFoodCookBook> breakfast;
	/**
	 * 中
	 */
	private List<YhFoodCookBook> lunch;
	/**
	 * 晚
	 */
	private List<YhFoodCookBook> supper;
	/**
	 * 加
	 */
	private List<YhFoodCookBook> extra;
	/**
	 * 早千卡
	 */
	private String breakfastkilocalorie;
	
	/**
	 * 中千卡
	 */
	private String lunchkilocalorie;
	/**
	 * 晚千卡
	 */
	private String bsupperkilocalorie;
	/**
	 * 加千卡
	 */
	private String extrakilocalorie;
	
	/**
	 * 总千卡
	 */
	private String sumilocalorie;
	
	public String getBreakfastkilocalorie() {
		return breakfastkilocalorie;
	}
	public void setBreakfastkilocalorie(String breakfastkilocalorie) {
		this.breakfastkilocalorie = breakfastkilocalorie;
	}
	public String getLunchkilocalorie() {
		return lunchkilocalorie;
	}
	public void setLunchkilocalorie(String lunchkilocalorie) {
		this.lunchkilocalorie = lunchkilocalorie;
	}
	public String getBsupperkilocalorie() {
		return bsupperkilocalorie;
	}
	public void setBsupperkilocalorie(String bsupperkilocalorie) {
		this.bsupperkilocalorie = bsupperkilocalorie;
	}
	public String getExtrakilocalorie() {
		return extrakilocalorie;
	}
	public void setExtrakilocalorie(String extrakilocalorie) {
		this.extrakilocalorie = extrakilocalorie;
	}
	public String getSumilocalorie() {
		return sumilocalorie;
	}
	public void setSumilocalorie(String sumilocalorie) {
		this.sumilocalorie = sumilocalorie;
	}
	public List<YhFoodCookBook> getBreakfast() {
		return breakfast;
	}
	public void setBreakfast(List<YhFoodCookBook> breakfast) {
		this.breakfast = breakfast;
	}
	public List<YhFoodCookBook> getLunch() {
		return lunch;
	}
	public void setLunch(List<YhFoodCookBook> lunch) {
		this.lunch = lunch;
	}
	public List<YhFoodCookBook> getSupper() {
		return supper;
	}
	public void setSupper(List<YhFoodCookBook> supper) {
		this.supper = supper;
	}
	public List<YhFoodCookBook> getExtra() {
		return extra;
	}
	public void setExtra(List<YhFoodCookBook> extra) {
		this.extra = extra;
	}
	
	
	
}
