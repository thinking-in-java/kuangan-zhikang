package com.yihe.system.domain;

import java.util.Date;

public class YhFoodSrecipeRecord {
    private Integer recordId;

    private Date recordData;
    private String recordDataStr;

    private String recordName;

    private Integer recordKay;

    private Integer recordType;

    private String recordMode;

    private String userCode;

    private String mealId;

    private String illnessId;

    private String standby;
    
    private String name;
    
    private int oneself;
    
    private String age;
	private String height;
	private String weight;
	private String strength;
	private String sex;
	
	
	

	public String getRecordDataStr() {
		return recordDataStr;
	}

	public void setRecordDataStr(String recordDataStr) {
		this.recordDataStr = recordDataStr;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getStrength() {
		return strength;
	}

	public void setStrength(String strength) {
		this.strength = strength;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public int getOneself() {
		return oneself;
	}

	public void setOneself(int oneself) {
		this.oneself = oneself;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    public Date getRecordData() {
        return recordData;
    }

    public void setRecordData(Date recordData) {
        this.recordData = recordData;
    }

    public String getRecordName() {
        return recordName;
    }

    public void setRecordName(String recordName) {
        this.recordName = recordName == null ? null : recordName.trim();
    }

    public Integer getRecordKay() {
        return recordKay;
    }

    public void setRecordKay(Integer recordKay) {
        this.recordKay = recordKay;
    }

    public Integer getRecordType() {
        return recordType;
    }

    public void setRecordType(Integer recordType) {
        this.recordType = recordType;
    }

    public String getRecordMode() {
        return recordMode;
    }

    public void setRecordMode(String recordMode) {
        this.recordMode = recordMode == null ? null : recordMode.trim();
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode == null ? null : userCode.trim();
    }

    public String getMealId() {
        return mealId;
    }

    public void setMealId(String mealId) {
        this.mealId = mealId == null ? null : mealId.trim();
    }

    public String getIllnessId() {
        return illnessId;
    }

    public void setIllnessId(String illnessId) {
        this.illnessId = illnessId == null ? null : illnessId.trim();
    }

    public String getStandby() {
        return standby;
    }

    public void setStandby(String standby) {
        this.standby = standby == null ? null : standby.trim();
    }
}