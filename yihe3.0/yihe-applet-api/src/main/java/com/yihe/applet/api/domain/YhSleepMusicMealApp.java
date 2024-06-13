package com.yihe.applet.api.domain;

import java.io.Serializable;

/**
 * @version: shayu3.0
 * @Description: 助眠套餐实体
 * @author: lhh
 * @date: 2020年5月10日 上午10:29:49
 */
public class YhSleepMusicMealApp implements Serializable{
	private static final long serialVersionUID = 1L;
	private int mealId;
	private String mealTitle;
	private String musicTds;
	private String mealImg;
	private int mealListennum;
	public int getMealId() {
		return mealId;
	}
	public void setMealId(int mealId) {
		this.mealId = mealId;
	}
	public String getMealTitle() {
		return mealTitle;
	}
	public void setMealTitle(String mealTitle) {
		this.mealTitle = mealTitle;
	}
	public String getMusicTds() {
		return musicTds;
	}
	public void setMusicTds(String musicTds) {
		this.musicTds = musicTds;
	}
	public String getMealImg() {
		return mealImg;
	}
	public void setMealImg(String mealImg) {
		this.mealImg = mealImg;
	}
	public int getMealListennum() {
		return mealListennum;
	}
	public void setMealListennum(int mealListennum) {
		this.mealListennum = mealListennum;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
