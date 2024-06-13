package com.yihe.applet.api.domain;

import java.io.Serializable;

public class SportDiaryDetails implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4845899168030669140L;
	private Integer id;
	private String sportId;
	private String sportName;
	private String kcalTotal;
	private String time;
	private String sportImg;
	private String kcalTotal1;
	private String timeData;
	private String kcalTotal2;
	
	
	
	public String getKcalTotal2() {
		return kcalTotal2;
	}
	public void setKcalTotal2(String kcalTotal2) {
		this.kcalTotal2 = kcalTotal2;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTimeData() {
		return timeData;
	}
	public void setTimeData(String timeData) {
		this.timeData = timeData;
	}
	public String getKcalTotal1() {
		return kcalTotal1;
	}
	public void setKcalTotal1(String kcalTotal1) {
		this.kcalTotal1 = kcalTotal1;
	}
	public String getSportId() {
		return sportId;
	}
	public void setSportId(String sportId) {
		this.sportId = sportId;
	}
	public String getSportImg() {
		return sportImg;
	}
	public void setSportImg(String sportImg) {
		this.sportImg = sportImg;
	}
	public String getSportName() {
		return sportName;
	}
	public void setSportName(String sportName) {
		this.sportName = sportName;
	}
	public String getKcalTotal() {
		return kcalTotal;
	}
	public void setKcalTotal(String kcalTotal) {
		this.kcalTotal = kcalTotal;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
	

}
