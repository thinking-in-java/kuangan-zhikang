/**
 * 
 */
package com.yihe.system.domain;

import java.util.Date;

/**
 * @author dong
 *
 */
public class YHUserSleepDiaryVOJO {
	private String usercode;
	private String username;
	private String name;
	private String sex;

    private Date sleepTime;

    private Date wakeTime;

    private String diaryDesc;

    private Date sleepDiarytime;
    
    private String duration;
    
    private String result;

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

	public String getDiaryDesc() {
		return diaryDesc;
	}

	public void setDiaryDesc(String diaryDesc) {
		this.diaryDesc = diaryDesc;
	}

	public Date getSleepDiarytime() {
		return sleepDiarytime;
	}

	public void setSleepDiarytime(Date sleepDiarytime) {
		this.sleepDiarytime = sleepDiarytime;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
    
    
    
    
    
}
