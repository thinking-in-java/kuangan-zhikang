package com.yihe.system.domain;

import java.util.Date;

public class YhSleepUserDiary {
    private Integer id;

    private String userCode;

    private Date sleepTime;

    private Date wakeTime;

    private String diaryDesc;

    private Date sleepDiarytime;
    
    private String duration;
    
    private String result;
    

    
    public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
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
        this.userCode = userCode == null ? null : userCode.trim();
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
        this.diaryDesc = diaryDesc == null ? null : diaryDesc.trim();
    }

    public Date getSleepDiarytime() {
        return sleepDiarytime;
    }

    public void setSleepDiarytime(Date sleepDiarytime) {
        this.sleepDiarytime = sleepDiarytime;
    }
}