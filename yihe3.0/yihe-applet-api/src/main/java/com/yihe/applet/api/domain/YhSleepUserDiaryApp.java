package com.yihe.applet.api.domain;
/**
 * @version: shayu3.0
 * @Description: 用户睡眠记录
 * @author: lhh
 * @date: 2020年4月15日 上午11:52:33
 */

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class YhSleepUserDiaryApp implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String userCode;
	//@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date sleepTime;
	//@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date wakeTime;
	private String diaryDesc;
	//  @DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date sleepDiarytime;
	
	private String sleeptime;
	
	private int day;
	private int daynum;
	
	
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public int getDaynum() {
		return daynum;
	}
	public void setDaynum(int daynum) {
		this.daynum = daynum;
	}
	public String getSleeptime() {
		return sleeptime;
	}
	public void setSleeptime(String sleeptime) {
		this.sleeptime = sleeptime;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public Date getSleepTime() {
		return sleepTime;
	}
	public void setSleepTime(Date sleepTime) {
		this.sleepTime = sleepTime;
	}
	public Date getWakeTime() {
		return wakeTime;
	}
	public void setWakeTime(Date wakeTime) {
		this.wakeTime = wakeTime;
	}
	public Date getSleepDiarytime() {
		return sleepDiarytime;
	}
	public void setSleepDiarytime(Date sleepDiarytime) {
		this.sleepDiarytime = sleepDiarytime;
	}
	public String getDiaryDesc() {
		return diaryDesc;
	}
	public void setDiaryDesc(String diaryDesc) {
		this.diaryDesc = diaryDesc;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
