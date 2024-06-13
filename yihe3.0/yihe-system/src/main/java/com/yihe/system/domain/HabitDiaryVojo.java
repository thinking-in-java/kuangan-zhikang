/**
 * 
 */
package com.yihe.system.domain;

import java.util.Date;

/**
 * @author dong
 *
 */
public class HabitDiaryVojo {
	private String usercode;
	private String username;
	private String name;
	private String sex;
	private Date diaryTime;
	private String habitId;
	private String schedule;
	private int complete;
	private int sum;
	private String finish;
	
	
	public String getFinish() {
		return finish;
	}
	public void setFinish(String finish) {
		this.finish = finish;
	}
	public String getSchedule() {
		return schedule;
	}
	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}
	public int getComplete() {
		return complete;
	}
	public void setComplete(int complete) {
		this.complete = complete;
	}
	public int getSum() {
		return sum;
	}
	public void setSum(int sum) {
		this.sum = sum;
	}
	public String getUsercode() {
		return usercode;
	}
	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public Date getDiaryTime() {
		return diaryTime;
	}
	public void setDiaryTime(Date diaryTime) {
		this.diaryTime = diaryTime;
	}
	public String getHabitId() {
		return habitId;
	}
	public void setHabitId(String habitId) {
		this.habitId = habitId;
	}
	
	
	
	
}
