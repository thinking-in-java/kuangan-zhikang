package com.yihe.system.domain;

public class YHUserBmi {
    private Integer uBmiId;

    private Float uHeight;

    private Float uWeight;

    private String entryTime;

    private Integer isDel;

    private Integer eUserId;

    private String env;

    private String dataSource;
    
    public YHUserBmi( Float height, Float weight, String entryTime, Integer is_Del, Integer eUserId,
			String env, String dataSource) {
		super();
		this.uHeight = height;
		this.uWeight = weight;
		this.entryTime = entryTime;
		this.isDel = is_Del;
		this.eUserId = eUserId;
		this.env = env;
		this.dataSource = dataSource;
	}
    
	public YHUserBmi() {
		super();
	}
	

    public Integer getuBmiId() {
        return uBmiId;
    }

    public void setuBmiId(Integer uBmiId) {
        this.uBmiId = uBmiId;
    }

    public Float getuHeight() {
        return uHeight;
    }

    public void setuHeight(Float uHeight) {
        this.uHeight = uHeight;
    }

    public Float getuWeight() {
        return uWeight;
    }

    public void setuWeight(Float uWeight) {
        this.uWeight = uWeight;
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

    public Integer geteUserId() {
        return eUserId;
    }

    public void seteUserId(Integer eUserId) {
        this.eUserId = eUserId;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env == null ? null : env.trim();
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource == null ? null : dataSource.trim();
    }
}