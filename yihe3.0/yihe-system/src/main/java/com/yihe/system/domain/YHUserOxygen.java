package com.yihe.system.domain;

import java.io.Serializable;

public class YHUserOxygen implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer uOxygenId;

    private Float oxygen;

    private Integer heartRate;

    private String entryTime;

    private Integer isDel;

    private String eUserId;

    private String testHj;

    private String type;
    
    public YHUserOxygen() {
		super();
		// TODO Auto-generated constructor stub
	}
    
	public  YHUserOxygen(Float oxygen, Integer heartRate, String entryTime, Integer isDel, String eUserId,
			String testHj, String type) {
		super();
		this.oxygen = oxygen;
		this.heartRate = heartRate;
		this.entryTime = entryTime;
		this.isDel = isDel;
		this.eUserId = eUserId;
		this.testHj = testHj;
		this.type = type;
	}
	
	

    public Integer getuOxygenId() {
        return uOxygenId;
    }

    public void setuOxygenId(Integer uOxygenId) {
        this.uOxygenId = uOxygenId;
    }

    public Float getOxygen() {
        return oxygen;
    }

    public void setOxygen(Float oxygen) {
        this.oxygen = oxygen;
    }

    public Integer getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(Integer heartRate) {
        this.heartRate = heartRate;
    }

    public String getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(String entryTime) {
        this.entryTime = entryTime == null ? null : entryTime.trim();
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public String geteUserId() {
        return eUserId;
    }

    public void seteUserId(String eUserId) {
        this.eUserId = eUserId;
    }

    public String getTestHj() {
        return testHj;
    }

    public void setTestHj(String testHj) {
        this.testHj = testHj == null ? null : testHj.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }
}