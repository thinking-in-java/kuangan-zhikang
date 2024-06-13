package com.yihe.system.domain;

import java.util.Date;

public class YhLifeHabitSign {
    private Integer id;

    private Integer habitId;

    private Date signidDate;

    private String mark;

    private String userCode;

    private Integer lifeAdd;

    private String signImg;
    
    private String schedule;
    
    private int complete;//完成数
    
    private int sum;//总数
    
    private String categoryName;
    
    private Date increaseTime;
    
    
    
    public Date getIncreaseTime() {
		return increaseTime;
	}

	public void setIncreaseTime(Date increaseTime) {
		this.increaseTime = increaseTime;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
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

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getHabitId() {
        return habitId;
    }

    public void setHabitId(Integer habitId) {
        this.habitId = habitId;
    }

    public Date getSignidDate() {
        return signidDate;
    }

    public void setSignidDate(Date signidDate) {
        this.signidDate = signidDate;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark == null ? null : mark.trim();
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode == null ? null : userCode.trim();
    }

    public Integer getLifeAdd() {
        return lifeAdd;
    }

    public void setLifeAdd(Integer lifeAdd) {
        this.lifeAdd = lifeAdd;
    }

    public String getSignImg() {
        return signImg;
    }

    public void setSignImg(String signImg) {
        this.signImg = signImg == null ? null : signImg.trim();
    }
}