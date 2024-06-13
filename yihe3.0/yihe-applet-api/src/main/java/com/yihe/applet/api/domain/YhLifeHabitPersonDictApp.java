package com.yihe.applet.api.domain;

import java.util.Date;
import java.util.List;

public class YhLifeHabitPersonDictApp {
    private Integer id;

    private String userCode;

    private Integer habitId;

    private Integer continueDay;

    private Integer missingDay;

    private Integer totalDay;

    private String beginTime;

    private String finishTim;

    private String period;

    private String beginDate;

    private String finishDate;

    private String accomplishType;

    private Date increaseDate;

    private String standby;

    private String standby1;

    private String standby2;
    
    private String habitName;
    
    private String habitdesc;
    
    private String lifeImg;
    
    /**
     * 1，待打卡
     * 2，未到时间
     * 3，打卡成功
     * 4，补卡
     */
    private int type;
    
    private String sum;
    
    private int join;
    
    private int clock;
    
    private int rowno;
    
    private String name;
    
    private String headImg;
    
    private String habitType;
    
    private List<WeekDateVojo> weekDate;
    
    
    private boolean switch1;
    
    
    private boolean switch2;
    
    private String remindTime;
    
    private String postCode;
    
    private int gender;
    
    

	public boolean isSwitch1() {
		return switch1;
	}

	public void setSwitch1(boolean switch1) {
		this.switch1 = switch1;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public String getRemindTime() {
		return remindTime;
	}

	public void setRemindTime(String remindTime) {
		this.remindTime = remindTime;
	}

	public boolean isSwitch2() {
		return switch2;
	}

	public void setSwitch2(boolean switch2) {
		this.switch2 = switch2;
	}

	public List<WeekDateVojo> getWeekDate() {
		return weekDate;
	}

	public void setWeekDate(List<WeekDateVojo> weekDate) {
		this.weekDate = weekDate;
	}

	public String getHabitType() {
		return habitType;
	}

	public void setHabitType(String habitType) {
		this.habitType = habitType;
	}

	public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}

	public int getRowno() {
		return rowno;
	}

	public void setRowno(int rowno) {
		this.rowno = rowno;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getJoin() {
		return join;
	}

	public void setJoin(int join) {
		this.join = join;
	}

	public int getClock() {
		return clock;
	}

	public void setClock(int clock) {
		this.clock = clock;
	}

	public String getSum() {
		return sum;
	}

	public void setSum(String sum) {
		this.sum = sum;
	}

	public String getHabitName() {
		return habitName;
	}

	public void setHabitName(String habitName) {
		this.habitName = habitName;
	}

	public String getHabitdesc() {
		return habitdesc;
	}

	public void setHabitdesc(String habitdesc) {
		this.habitdesc = habitdesc;
	}

	public String getLifeImg() {
		return lifeImg;
	}

	public void setLifeImg(String lifeImg) {
		this.lifeImg = lifeImg;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
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

    public Integer getHabitId() {
        return habitId;
    }

    public void setHabitId(Integer habitId) {
        this.habitId = habitId;
    }

    public Integer getContinueDay() {
        return continueDay;
    }

    public void setContinueDay(Integer continueDay) {
        this.continueDay = continueDay;
    }

    public Integer getMissingDay() {
        return missingDay;
    }

    public void setMissingDay(Integer missingDay) {
        this.missingDay = missingDay;
    }

    public Integer getTotalDay() {
        return totalDay;
    }

    public void setTotalDay(Integer totalDay) {
        this.totalDay = totalDay;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime == null ? null : beginTime.trim();
    }

    public String getFinishTim() {
        return finishTim;
    }

    public void setFinishTim(String finishTim) {
        this.finishTim = finishTim == null ? null : finishTim.trim();
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period == null ? null : period.trim();
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate == null ? null : beginDate.trim();
    }

    public String getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate == null ? null : finishDate.trim();
    }

    public String getAccomplishType() {
        return accomplishType;
    }

    public void setAccomplishType(String accomplishType) {
        this.accomplishType = accomplishType == null ? null : accomplishType.trim();
    }

    public Date getIncreaseDate() {
        return increaseDate;
    }

    public void setIncreaseDate(Date increaseDate) {
        this.increaseDate = increaseDate;
    }

    public String getStandby() {
        return standby;
    }

    public void setStandby(String standby) {
        this.standby = standby == null ? null : standby.trim();
    }

    public String getStandby1() {
        return standby1;
    }

    public void setStandby1(String standby1) {
        this.standby1 = standby1 == null ? null : standby1.trim();
    }

    public String getStandby2() {
        return standby2;
    }

    public void setStandby2(String standby2) {
        this.standby2 = standby2 == null ? null : standby2.trim();
    }
}